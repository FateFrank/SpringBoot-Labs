package cn.iocoder.springboot.lab68.resourceserverdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CallbackController {

    @Autowired
    private OAuth2ClientProperties oauth2ClientProperties;

    @Value("${security.oauth2.access-token-uri}")
    private String accessTokenUri;

    /**
     * 使用OAuth2RestTemplate对象来消耗OAuth2安全的REST服务(即 /oauth/token 接口)
     *
     * 提供 /callback 回调地址，在获取到授权码时，请求授权服务器，通过授权码获取访问令牌。
     *
     * 设置的回调地址，会校验和授权码绑定的回调地址是否相同
     * @param code
     * @return
     */
    @GetMapping("/callback")
    public OAuth2AccessToken login(@RequestParam("code") String code) {
        // 创建 AuthorizationCodeResourceDetails 对象
        AuthorizationCodeResourceDetails resourceDetails = new AuthorizationCodeResourceDetails();
        resourceDetails.setAccessTokenUri(accessTokenUri);
        resourceDetails.setClientId(oauth2ClientProperties.getClientId());
        resourceDetails.setClientSecret(oauth2ClientProperties.getClientSecret());
        // 创建 OAuth2RestTemplate 对象
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails);
        restTemplate.getOAuth2ClientContext().getAccessTokenRequest().setAuthorizationCode(code); // 设置 code
        restTemplate.getOAuth2ClientContext().getAccessTokenRequest().setPreservedState("http://127.0.0.1:9090/callback"); // 通过这个方式，设置 redirect_uri 参数
        restTemplate.setAccessTokenProvider(new AuthorizationCodeAccessTokenProvider());
        // 获取访问令牌
        return restTemplate.getAccessToken();
    }

}
