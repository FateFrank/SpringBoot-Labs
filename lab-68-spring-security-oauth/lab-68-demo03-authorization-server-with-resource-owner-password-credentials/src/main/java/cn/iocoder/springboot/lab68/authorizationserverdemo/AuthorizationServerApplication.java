package cn.iocoder.springboot.lab68.authorizationserverdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * oauth2 刷新令牌 & 删除令牌 演示demo
 *
 * ---------------------------------------------------------------------------------------------------------------------
 *
 * 在 OAuth2.0 中，一共有两类令牌：
 *  - 访问令牌（Access Token）
 *  - 刷新令牌（Refresh Token）
 *
 * 在访问令牌过期时，我们可以使用刷新令牌向授权服务器获取一个新的访问令牌。
 *
 * 可能会胖友有疑惑，为什么会有刷新令牌呢？每次请求资源服务器时，都会在请求上带上访问令牌，这样它的泄露风险是相对高的。
 *
 * 因此，出于安全性的考虑，访问令牌的过期时间比较短，刷新令牌的过期时间比较长。
 *
 * 这样，如果访问令牌即使被盗用走，那么在一定的时间后，访问令牌也能在较短的时间吼过期。
 *
 * 当然，安全也是相对的，如果使用刷新令牌后，获取到新的访问令牌，访问令牌后续又可能被盗用。
 *
 *
 * 艿艿整理了下，大家常用开放平台的令牌过期时间，让大家更好的理解：
 *
 * 开放平台	    Access Token 有效期	    Refresh Token 有效期
 * 微信开放平台	    2 小时	                未知
 * 腾讯开放平台	    90 天	                未知
 * 小米开放平台	    90 天	                10 年
 *
 * ---------------------------------------------------------------------------------------------------------------------
 *
 * 在用户登出系统时，我们会有删除令牌的需求。虽然说，可以通过客户端本地删除令牌的方式实现。但是，考虑到真正的彻底的实现删除令牌，必然服务端自身需要删除令牌。
 *
 *      友情提示：客户端本地删除令牌的方式实现，指的是清楚本地 Cookie、localStorage 的令牌缓存。
 *
 * 在 Spring Security OAuth2 中，并没有提供内置的接口，所以需要自己去实现。
 *
 * 笔者参看 《Spring Security OAuth2 – Simple Token Revocation》 文档，实现删除令牌的 API 接口。
 *
 * 具体的实现，通过调用 ConsumerTokenServices 的 #revokeToken(String tokenValue) 方法，删除访问令牌和刷新令牌。
 */
@SpringBootApplication
public class AuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
    }

}
