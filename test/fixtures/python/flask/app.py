from flask import Flask, Blueprint

app = Flask(__name__)
bp  = Blueprint('api', __name__)

# Flask 2.0+ shorthand decorators — produce clean single method
@app.get('/health')
def health():
    return {'ok': True}

@app.post('/events')
def create_event():
    return {}, 201

@app.get('/users')
def list_users():
    return []

@app.get('/users/<int:user_id>')
def get_user(user_id):
    return {}

@app.post('/users')
def create_user():
    return {}, 201

@app.put('/users/<int:user_id>')
def update_user(user_id):
    return {}

@app.delete('/users/<int:user_id>')
def delete_user(user_id):
    return {}, 204

# Blueprint routes — also using shorthand
@bp.get('/products')
def list_products():
    return []

@bp.post('/products')
def create_product():
    return {}, 201

@bp.get('/products/<int:pid>')
def get_product(pid):
    return {}
