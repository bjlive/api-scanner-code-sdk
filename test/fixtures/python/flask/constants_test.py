from flask import Flask
from .config import API_PREFIX, VERSION, INTERNAL_SEC

app = Flask(__name__)

# 场景 1: 跨文件常量拼接
# 预期路径: /api/v1/login
@app.route(API_PREFIX + "/" + VERSION + "/login", methods=["POST"])
def login():
    return "ok"

# 场景 2: 单个跨文件变量引用
# 预期路径: /internal/admin/status
@app.route(INTERNAL_SEC + "/status")
def status():
    return "ok"