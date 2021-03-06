package cn.iocoder.springboot.lab68.authorizationserverdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Token 示例 Controller
 */
@RestController
@RequestMapping("/token/demo")
public class TokenDemoController {

    @Autowired
    private ConsumerTokenServices tokenServices;

    /**
     * 调用 ConsumerTokenServices 的 #revokeToken(String tokenValue) 方法，删除访问令牌和刷新令牌
     * @param token
     * @return
     */
    @PostMapping(value = "/revoke")
    public boolean revokeToken(@RequestParam("token") String token) {
        return tokenServices.revokeToken(token);
    }

}
