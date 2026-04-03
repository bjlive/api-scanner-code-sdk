package main

import "github.com/gin-gonic/gin"

const API = "/api"

func setupRoutes(r *gin.Engine) {
    // 场景 3: 多层 Group 嵌套
    v2 := r.Group(API + "/v2")
    {
        user := v2.Group("/user")
        {
            // 预期路径: /api/v2/user/profile
            user.GET("/profile", func(c *gin.Context) {})
            
            // 预期路径: /api/v2/user/update
            user.POST("/update", func(c *gin.Context) {})
        }
        
        // 场景 4: 同级 Group 切换
        admin := v2.Group("/admin")
        {
            // 预期路径: /api/v2/admin/config
            admin.PUT("/config", func(c *gin.Context) {})
        }
    }
}