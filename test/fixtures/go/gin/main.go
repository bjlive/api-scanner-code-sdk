package main

import (
    "github.com/gin-gonic/gin"
    "myproject/pkg/api"
)

func main() {
    r := gin.Default()
    
    // 场景 1: 直接引用跨包常量
    // 预期路径: /api/v1/login
    r.POST(api.Root + "/" + api.Version + "/login", func(c *gin.Context) {})

    // 场景 2: 引用跨包变量 (Variable Tracing)
    // 预期路径: /api/v1/admin/status
    r.GET(api.AdminPrefix + "/status", func(c *gin.Context) {})
}