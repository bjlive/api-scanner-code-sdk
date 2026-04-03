from flask import Flask

app = Flask(__name__)

# 路由映射表
ROUTES = {
    "auth": "/auth_system",
    "user": "/user_manager",
    "v3": "/v3"
}

# 场景 6: 字典 Key 引用
# 预期路径: /auth_system/login
@app.route(ROUTES["auth"] + "/login")
def auth_login():
    return "ok"

# 场景 7: 列表索引引用
PATH_LIST = ["/test", "/prod", "/dev"]
# 预期路径: /test/health
@app.route(PATH_LIST[0] + "/health")
def health():
    return "ok"