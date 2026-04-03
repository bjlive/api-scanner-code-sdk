from fastapi import FastAPI

app = FastAPI()

BASE = "/service"
SUB = "compute"
# 嵌套拼接
FULL_BASE = BASE + "/" + SUB # "/service/compute"

# 场景 3: F-string 还原 (Python 3.6+)
# 预期路径: /service/compute/run
@app.post(f"{FULL_BASE}/run")
async def run_task():
    return {}

# 场景 4: 带有表达式的 F-string
# 预期路径: /service/COMPUTE/info
@app.get(f"{BASE}/{SUB}/info")
async def get_info():
    return {}

# 场景 5: 多级变量链式传递
MOD = "core"
FINAL_PATH = f"{FULL_BASE}/{MOD}" # "/service/compute/core"
@app.get(FINAL_PATH)
def core_api():
    return "ok"