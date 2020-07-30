package cn.iocoder.springboot.lab68.authorizationserverdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 简化模式
 *
 * (A) 用户访问客户端，后者将前者跳转到到授权服务器。
 * (B) 用户选择是否给予客户端授权。
 * (C) 假设用户给予授权，授权服务器将用户导向客户端指定的”重定向URI”，并在 URI 的 Hash 部分包含了访问令牌。
 * (D) 浏览器向资源服务器发出请求，其中不包括上一步收到的 Hash 值。
 * (E) 资源服务器返回一个网页，其中包含的代码可以获取 Hash 值中的令牌。
 * (F) 浏览器执行上一步获得的脚本，提取出令牌。
 * (G) 浏览器将令牌发给客户端。
 */
@SpringBootApplication
public class AuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
    }

}
