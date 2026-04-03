package main

type AppConfig struct {
    Prefix string
}

const BasePath = "/internal"

func main() {
    cfg := AppConfig{Prefix: BasePath = "/prefix"}
    
    // 场景 5: 结构体字段引用
    // 预期路径: /internal/debug
    // 注意：这对静态分析有难度，需识别 cfg.Prefix 的初始化值
    r.GET(cfg.Prefix + "/debug", handler)
}