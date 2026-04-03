from fastapi import FastAPI
import os

app = FastAPI()

def get_env_path():
    # 模拟从环境变量读取，如果读取不到则返回默认值
    return os.getenv("API_ROOT", "/default_api")

# 场景 8: 函数调用作为路径
# 预期路径: /default_api/check (如果无法动态执行，应标记为 {get_env_path()}/check)
@app.get(get_env_path() + "/check")
def check():
    return "ok"

# 场景 9: 条件分支路径 (Conditional Assignment)
IS_DEBUG = True
if IS_DEBUG:
    DEBUG_PREFIX = "/debug"
else:
    DEBUG_PREFIX = "/prod"

# 预期路径: 应提取出两条可能路径 [/debug/dump, /prod/dump] 以覆盖攻击面
@app.get(DEBUG_PREFIX + "/dump")
def dump():
    return "secret"