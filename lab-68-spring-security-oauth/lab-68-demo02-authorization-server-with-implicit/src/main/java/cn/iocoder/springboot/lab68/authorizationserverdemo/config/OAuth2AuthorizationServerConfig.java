package cn.iocoder.springboot.lab68.authorizationserverdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * 授权服务器配置
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 用户认证 Manager
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 配置使用的 AuthenticationManager 实现用户认证的功能
     *
     * 其中，authenticationManager 是 Spring Security 的认证策略配置类(SecurityConfig)中创建的。
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }

    /**
     * 设置 /oauth/check_token 端点，通过认证后可访问。
     *
     * 友情提示：这里的认证，指的是使用 client-id + client-secret 进行的客户端认证，不要和用户认证混淆。
     *
     * 其中，/oauth/check_token 端点对应 CheckTokenEndpoint 类，用于校验访问令牌的有效性。
     *
     * - 在客户端访问资源服务器时，会在请求中带上访问令牌。
     * - 在资源服务器收到客户端的请求时，会使用请求中的访问令牌，找授权服务器确认该访问令牌的有效性。
     * @param oauthServer
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.checkTokenAccess("isAuthenticated()");
    }

    /**
     * 进行 Client 客户端的配置。
     *
     * <4.1> 处，设置使用基于内存的 Client 存储器。实际情况下，最好放入数据库中，方便管理。
     * <4.2> 处，创建一个 Client 配置。如果要继续添加另外的 Client 配置，可以在 <4.3> 处使用 #and() 方法继续拼接。
     *          注意，这里的 .withClient("clientapp").secret("112233") 代码段，就是 client-id 和 client-secret。
     *
     * 补充知识：可能会有胖友会问，为什么要创建 Client 的 client-id 和 client-secret 呢？
     *
     * - 通过 client-id 编号和 client-secret，授权服务器可以知道调用的来源以及正确性。
     * - 这样，即使“坏人”拿到 Access Token ，但是没有 client-id 编号和 client-secret，也不能和授权服务器发生有效的交互。
     *
     * 注意：
     * 1) 这里的回调地址，实际上是可以配置多个的，简化模式下至少配置一个
     * 2) 如果配置多个，授权服务器会将多个回调地址注册到指定的clientId
     * 3) 在使用 /oauth/authorize 访问授权服务器时
     *      如果指定的clientId只配置了一个回调地址，传参有回调地址，会匹配，匹配失败会报错；传参没有回调地址，使用唯一注册的
     *      如果指定的clientId配置了多个回调地址，传参有回调地址，会匹配，匹配失败会报错；传参没有回调地址，会报错
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("clientapp").secret("112233")       // Client 账号、密码。
                .authorizedGrantTypes("implicit")                      // 简化模式
                .redirectUris("http://127.0.0.1:9090/callback02")      // 配置回调地址，选填。
                .scopes("read_userinfo", "read_contacts")              // 可授权的 Scope
//                .and().withClient()                                  // 可以继续配置新的 Client
                ;
    }

}
