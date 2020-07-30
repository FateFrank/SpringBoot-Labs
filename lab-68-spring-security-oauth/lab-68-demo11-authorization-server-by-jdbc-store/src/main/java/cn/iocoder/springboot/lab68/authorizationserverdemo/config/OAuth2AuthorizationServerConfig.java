package cn.iocoder.springboot.lab68.authorizationserverdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * 授权服务器配置
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 注入 用户认证 Manager (SecurityConfig 中创建)
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 注入 数据源 DataSource (Spring 解析配置文件 application.yaml 创建)
     */
    @Autowired
    private DataSource dataSource;

    /**
     * 创建 令牌存储器(基于数据库实现)
     * @return
     */
    @Bean
    public TokenStore jdbcTokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    /**
     * 配置使用的 AuthenticationManager 实现用户认证的功能
     * 同时配置令牌存储器，使用 JdbcTokenStore 进行【令牌】的读写
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
            .tokenStore(jdbcTokenStore());
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
//        oauthServer.tokenKeyAccess("isAuthenticated()")
//                .checkTokenAccess("isAuthenticated()");
//        oauthServer.tokenKeyAccess("permitAll()")
//                .checkTokenAccess("permitAll()");
    }

    /**
     * 创建 JdbcClientDetailsService
     * @return
     */
    @Bean
    public ClientDetailsService jdbcClientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 进行 client 客户端的配置
     * 使用 JdbcClientDetailsService 进行【客户端】的读写
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(jdbcClientDetailsService());
    }

}
