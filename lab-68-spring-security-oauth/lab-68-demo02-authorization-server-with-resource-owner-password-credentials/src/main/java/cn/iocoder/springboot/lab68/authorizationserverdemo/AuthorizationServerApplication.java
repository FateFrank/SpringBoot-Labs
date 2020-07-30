package cn.iocoder.springboot.lab68.authorizationserverdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 密码模式
 *
 * (A) 用户向客户端提供用户名和密码。
 * (B) 客户端将用户名和密码发给授权服务器，向后者请求令牌。
 * (C) 授权服务器确认无误后，向客户端提供访问令牌。
 *
 * (D) 客户端携带令牌向资源服务器请求资源
 * (E) 资源服务器请求授权服务器，获取令牌，校验令牌有效性
 * (F) 校验通过，资源服务器返回资源
 */
@SpringBootApplication
public class AuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
    }

}
