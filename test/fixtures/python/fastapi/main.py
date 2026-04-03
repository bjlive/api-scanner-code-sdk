from fastapi import FastAPI, APIRouter

app    = FastAPI()
router = APIRouter()

# app-level routes
@app.get('/users')
async def list_users():
    return []

@app.get('/users/{user_id}')
async def get_user(user_id: int):
    return {}

@app.post('/users')
async def create_user():
    return {}

@app.put('/users/{user_id}')
async def update_user(user_id: int):
    return {}

@app.delete('/users/{user_id}')
async def delete_user(user_id: int):
    return {}

# Router-level routes (no prefix — prefix is added at include_router time,
# which the scanner cannot know; paths here are the declared ones)
@router.get('/items')
async def list_items():
    return []

@router.get('/items/{item_id}')
async def get_item(item_id: int):
    return {}

@router.post('/items')
async def create_item():
    return {}

@router.delete('/items/{item_id}')
async def delete_item(item_id: int):
    return {}
