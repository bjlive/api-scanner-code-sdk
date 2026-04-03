package api

const (
    Version = "v1"
    Root    = "/api"
)

var (
    // 某些动态定义的路径
    AdminPrefix = Root + "/" + Version + "/admin"
)