import express from 'express';
import { API_ROOT, VERSIONS } from '../constants';

const app    = express();
const router = express.Router();

// Basic CRUD — tests all 5 HTTP methods + path params
router.get('/users',        (req, res) => { res.json([]); });
router.get('/users/:id',    (req, res) => { res.json({}); });
router.post('/users',       (req, res) => { res.status(201).json({}); });
router.put('/users/:id',    (req, res) => { res.json({}); });
router.patch('/users/:id',  (req, res) => { res.json({}); });
router.delete('/users/:id', (req, res) => { res.status(204).send(); });

// app.METHOD() — tests that both app and router prefixes are matched
app.get('/health',  (req, res) => { res.json({ ok: true }); });
app.post('/events', (req, res) => { res.status(201).json({}); });

const config = {
  path: {
    auth: '/auth',
    user: '/user'
  }
};

// 场景 2: 对象解构
const { auth } = config.path;
// 预期路径: /auth/login
app.post(auth + '/login', handle);

// 场景 3: 解构并重命名 (Aliasing)
const { user: userPath } = config.path;
// 预期路径: /user/profile
app.get(user + '/profile', handle);
const BASE = '/services';
const VERSION = 'v3';

// 场景 4: 基础模板字符串还原
// 预期路径: /services/v3/data
app.get(`${BASE}/${VERSION}/data`, handle);

// 场景 5: 嵌套表达式与三元运算 (Conditional)
const IS_PROD = true;
// 预期路径: /services/v3/prod/upload (应支持分支枚举或还原真值)
app.post(`${BASE}/${VERSION}/${IS_PROD ? 'prod' : 'dev'}/upload`, handle);

const ROOT = '/api';
const buildPath = (sub) => ROOT + '/' + sub; // 场景 6: 简单函数调用还原

// 场景 7: 深度拼接链
const PART1 = '/internal';
const PART2 = '/debug';
const FULL_INTERNAL = ROOT + PART1 + PART2; // "/api/internal/debug"

// 预期路径: /api/internal/debug/dump
app.get(FULL_INTERNAL + '/dump', handle);

// 场景 8: 引用 process.env (环境变量)
// 预期路径: {process.env.API_SECRET_PATH}/leak
app.get(process.env.API_SECRET_PATH + '/leak', handle);

// Nested path params
router.get('/orgs/:orgId/members/:memberId', (req, res) => { res.json({}); });

// 场景 1: 对象属性拼接 (MemberExpression)
// 预期路径: /api/v1/status
router.get(API_ROOT + VERSIONS.V1 + '/status', (req, res) => {
  res.send('ok');
});

export default router;
