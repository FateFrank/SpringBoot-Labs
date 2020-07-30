package cn.iocoder.springboot.lab68.resourceserverdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 资源服务器配置
 *
 * 在类上添加 @EnableResourceServer 注解，声明开启 OAuth 资源服务器的功能。
 *
 * 同时，继承 ResourceServerConfigurerAdapter 类，进行 OAuth 资源服务器的配置。
 */
@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * 设置 HTTP 权限。这里，我们设置一些接口无需权限访问，这些接口时用来获取"访问令牌"的，其它接口认证后可访问。
     *
     * 这样，客户端在访问资源服务器时：
     *
     * - 如果是设置的特殊接口，会跳过认证，因为这些接口就是用来获取令牌的，当然不需要认证
     *
     * - 如果是其它接口，其请求中的"访问令牌"会被资源服务器调用授权服务器的 /oauth/check_token 接口，进行校验"访问令牌"的正确性。
     *   /oauth/check_token 这个URL已经被写到了配置文件中
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            // 设置 /login 无需权限访问，测试密码模式
            .antMatchers("/login").permitAll()
            // 设置 /client-login 无需权限访问，测试客户端模式
            .antMatchers("/client-login").permitAll()
            // 设置 /callback 无需权限访问，测试授权码模式
            .antMatchers("/callback").permitAll()
            // 设置 /callback02 无需权限访问，测试简化模式
            .antMatchers("/callback02").permitAll()
            // 设置其它请求，需要认证后访问
            .anyRequest().authenticated()
            ;
    }

}
