package main

import "github.com/gin-gonic/gin"

func main() {
	r := gin.Default()

	// Basic routes — tests all HTTP methods
	r.GET("/ping",         func(c *gin.Context) { c.JSON(200, gin.H{"pong": true}) })
	r.GET("/users",        func(c *gin.Context) { c.JSON(200, nil) })
	r.GET("/users/:id",    func(c *gin.Context) { c.JSON(200, nil) })
	r.POST("/users",       func(c *gin.Context) { c.JSON(201, nil) })
	r.PUT("/users/:id",    func(c *gin.Context) { c.JSON(200, nil) })
	r.PATCH("/users/:id",  func(c *gin.Context) { c.JSON(200, nil) })
	r.DELETE("/users/:id", func(c *gin.Context) { c.Status(204) })

	// Nested path params
	r.GET("/orgs/:orgId/repos/:repo", func(c *gin.Context) { c.JSON(200, nil) })

	r.Run(":8080")
}
