package main

import "net/http"

func main() {
    // 场景 6: 标准库 http.Handle
    // 预期路径: /static/resource
    http.Handle("/static/resource", handler)

    // 场景 7: 带正则或占位符的路径 (chi/mux 风格)
    // 预期路径: /articles/{category}/{id:[0-9]+}
    r.HandleFunc("/articles/{category}/{id:[0-9]+}", articleHandler)
}