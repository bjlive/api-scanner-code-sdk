package com.demo.api;

import io.javalin.Javalin;

public class JavalinTest {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);
        
        final String VERSION = "/v3";

        // 1. Lambda 表达式路由
        // 预期路径: /v3/hello (Method: GET)
        app.get(VERSION + "/hello", ctx -> ctx.result("Hello World"));

        // 2. 嵌套路径 (ApiBuilder)
        // 预期路径: /v3/users (Method: POST)
        app.routes(() -> {
            path(VERSION + "/users", () -> {
                post(ctx -> { /* save user */ });
                get(ctx -> { /* list users */ });
            });
        });
    }
}