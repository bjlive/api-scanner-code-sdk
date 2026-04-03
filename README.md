# 注：sdk 默认带过期失效功能，当前版本2026.04.30 过期，过期后使用会报错，如需完整功能请联系
<img src="wx.jpg" width="300" height="400">

# api-scanner-sdk

扫描任意语言 / 框架的源代码，自动提取 HTTP API 接口定义。

支持 Java、TypeScript、Python、Go 等主流语言，内置跨文件常量路径还原、多核并发扫描、置信度评分，结果可导出为 CSV / JSON / OpenAPI 3.0。

---

## 目录

- [安装](#安装)
- [快速开始](#快速开始)
- [构建与打包](#构建与打包)
- [ScanOptions 参数说明](#scanoptions-参数说明)
- [ScanResult 返回值](#scanresult-返回值)
- [ApiEndpoint 字段说明](#apiendpoint-字段说明)
- [路径字段说明](#路径字段说明)
- [置信度说明](#置信度说明)
- [常量路径解析](#常量路径解析)
- [进度显示](#进度显示)
- [输出格式](#输出格式)
- [自定义规则](#自定义规则)
- [自定义 Parser](#自定义-parser)
- [支持的框架](#支持的框架)

---

## 安装

```bash
# 从本地 tgz 安装（推荐用于内部分发）
npm install ./api-scanner-sdk-x.x.x.tgz

```

---

## 快速开始

### JavaScript（CommonJS）

```javascript
const { Scanner } = require('api-scanner-sdk');

async function main() {
  const result = await new Scanner().scan({
    rootDir: './my-project',
  });

  for (const ep of result.endpoints) {
    console.log(`${ep.method} ${ep.path}  →  ${ep.sourceFile}:${ep.sourceLine}`);
  }
  console.log(`共找到 ${result.stats.totalEndpoints} 个接口`);
}

main();
```

### TypeScript

```typescript
import { Scanner, ScanResult } from 'api-scanner-sdk';

const result: ScanResult = await new Scanner().scan({
  rootDir: './my-project',
  minConfidence: 'medium',
  verbose: true,
  output: { format: 'csv', filePath: './endpoints.csv' },
});
```

### 便捷函数（无需实例化）

```javascript
const { scan } = require('api-scanner-sdk');

const result = await scan({ rootDir: './my-project' });
```

---

## ScanOptions 参数说明

`scanner.scan(options)` 接受以下参数：

### 基础参数

| 参数 | 类型 | 必填 | 默认值 | 说明 |
|------|------|------|--------|------|
| `rootDir` | `string` | ✅ | — | 要扫描的项目根目录（绝对或相对路径） |
| `minConfidence` | `'high' \| 'medium' \| 'low'` | | `'low'` | 过滤低置信度结果，只返回不低于该等级的接口 |
| `verbose` | `boolean` | | `false` | 打印详细扫描日志（每个文件的接口数、框架检测等） |
| `concurrency` | `number` | | CPU 核心数 | Worker 线程数量，默认等于机器 CPU 核心数 |

```javascript
const result = await new Scanner().scan({
  rootDir: './src',
  minConfidence: 'medium',  // 只返回 medium 和 high
  verbose: true,            // 打印：[Scanner] src/api/user.java: 3 endpoint(s)
  concurrency: 4,           // 使用 4 个 Worker 线程
});
```

---

### 过滤参数

| 参数 | 类型 | 说明 |
|------|------|------|
| `include` | `string[]` | 仅扫描匹配的文件路径模式（glob） |
| `exclude` | `string[]` | 排除匹配的文件路径模式（glob） |

```javascript
await scanner.scan({
  rootDir: './src',
  include: ['**/*.java', '**/*.kt'],   // 只扫描 Java/Kotlin 文件
  exclude: ['**/test/**', '**/*Test.java'],  // 跳过测试文件
});
```

> **自动跳过的目录**：`node_modules` / `.git` / `dist` / `build` / `target` / `vendor` 等构建产物目录无需手动排除。

---

### 规则参数

| 参数 | 类型 | 说明 |
|------|------|------|
| `ruleDirs` | `string[]` | 额外的自定义规则目录路径，与内置规则合并加载 |

```javascript
await scanner.scan({
  rootDir: './src',
  ruleDirs: ['./my-rules', '/shared/company-rules'],
});
```

也可以在实例化后通过链式调用添加：

```javascript
const result = await new Scanner()
  .addRuleDir('./my-rules')
  .scan({ rootDir: './src' });
```

---

### 进度参数

| 参数 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| `progress` | `boolean` | `false` | 开启实时进度条（输出到 stderr） |
| `progressInterval` | `number` | `3000` | 进度刷新间隔（毫秒） |

```javascript
await scanner.scan({
  rootDir: './large-project',
  progress: true,           // 开启进度条
  progressInterval: 2000,   // 每 2 秒刷新一次
});
```

输出效果（写入 stderr，不影响 stdout 管道）：
```
[Scanner] [████████████░░░░░░░░░░░░░] 48% (144/300 files)
[Scanner] [█████████████████████████] 100% (300/300 files)
```

---

### 输出参数

| 参数 | 类型 | 说明 |
|------|------|------|
| `output` | `OutputOptions` | 将结果自动写入文件（目录不存在时自动创建） |

```javascript
// 导出 CSV
await scanner.scan({
  rootDir: './src',
  output: { format: 'csv', filePath: './result/endpoints.csv' },
});

// 导出 JSON
await scanner.scan({
  rootDir: './src',
  output: { format: 'json', filePath: './result/endpoints.json' },
});
```

---

## ScanResult 返回值

`scanner.scan()` 返回 `Promise<ScanResult>`：

```typescript
interface ScanResult {
  endpoints:  ApiEndpoint[];      // 所有识别出的接口列表
  frameworks: FrameworkMatch[];   // 检测到的框架信息
  stats:      ScanStats;          // 统计信息
  errors:     ScanError[];        // 扫描过程中的错误（不影响其他文件）
}
```

### ScanStats

```typescript
interface ScanStats {
  totalFiles:     number;                           // 收集到的文件总数
  scannedFiles:   number;                           // 实际解析的文件数（有路由信号的）
  skippedFiles:   number;                           // 跳过的文件数
  totalEndpoints: number;                           // 接口总数
  byFramework:    Record<string, number>;           // 按框架统计
  byLanguage:     Record<string, number>;           // 按语言统计
  byConfidence:   Record<ConfidenceLevel, number>;  // 按置信度统计
  durationMs:     number;                           // 扫描耗时（毫秒）
}
```

```javascript
const result = await scanner.scan({ rootDir: './src' });

console.log(`扫描耗时: ${result.stats.durationMs}ms`);
console.log(`接口总数: ${result.stats.totalEndpoints}`);
console.log(`框架分布:`, result.stats.byFramework);
// → { 'Spring MVC': 42, 'Express': 18 }
console.log(`置信度分布:`, result.stats.byConfidence);
// → { high: 48, medium: 10, low: 2 }

// 检查扫描错误
if (result.errors.length > 0) {
  result.errors.forEach(e => console.warn(`解析失败: ${e.file} — ${e.message}`));
}
```

---

## ApiEndpoint 字段说明

| 字段 | 类型 | 说明 |
|------|------|------|
| `path` | `string` | 原始路径，保留路径模板变量，如 `/users/{id}` |
| `resolvedPath` | `string` | 示例路径，将路径变量替换为示例值，如 `/users/1` |
| `method` | `HttpMethod` | HTTP 方法：`GET` / `POST` / `PUT` / `PATCH` / `DELETE` / `HEAD` / `OPTIONS` / `ANY` |
| `handlerName` | `string?` | 处理函数 / 方法名称 |
| `parameters` | `ApiParameter[]?` | 路径参数、查询参数等 |
| `sourceFile` | `string` | 来源文件绝对路径 |
| `sourceLine` | `number` | 来源行号 |
| `sourceColumn` | `number?` | 来源列号 |
| `framework` | `string` | 检测到的框架名称，如 `Spring MVC` |
| `language` | `Language` | 编程语言，如 `java` / `typescript` |
| `confidence` | `ConfidenceScore` | 置信度（见下方说明） |
| `detectedBy` | `'ast' \| 'regex' \| 'llm' \| 'rule'` | 使用的检测策略 |
| `rawAnnotation` | `string?` | 原始注解 / 装饰器文本，如 `@PostMapping("/users")` |

```javascript
for (const ep of result.endpoints) {
  console.log({
    method:     ep.method,            // "POST"
    path:       ep.path,              // "/V1/users/{id}"
    resolved:   ep.resolvedPath,      // "/V1/users/1"
    file:       ep.sourceFile,        // "/project/src/UserController.java"
    line:       ep.sourceLine,        // 42
    framework:  ep.framework,         // "Spring MVC"
    confidence: ep.confidence.level,  // "high"
    raw:        ep.rawAnnotation,     // "@PostMapping(\"/users/{id}\")"
  });
}
```

---

## 路径字段说明

每个接口提供两个路径字段：

| 字段 | 含义 | 示例 |
|------|------|------|
| `path`（原始路径） | 与代码中声明一致，保留变量占位符 | `/api/users/{userId}/orders/{orderId}` |
| `resolvedPath`（示例路径） | 将变量替换为示例值，可直接用于接口测试 | `/api/users/1/orders/1` |

**变量替换规则：**

| 占位符形式 | 替换值 |
|-----------|--------|
| `{id}` / `{userId}` / `{orderId}` 等含 `id` 的参数 | `1` |
| `{name}` / `{type}` 等其他参数 | 参数名本身 |
| `:id` / `:userId` 等 Express 风格参数 | 同上规则 |

---

## 置信度说明

每个识别出的接口都携带置信度分数，反映结果的可靠程度。

### 等级定义

| 等级 | 分数范围 | 含义 |
|------|----------|------|
| `high` | ≥ 0.80 | 高可信。路径格式规范，HTTP 方法明确，由注解/装饰器直接触发 |
| `medium` | 0.55 ~ 0.79 | 中等可信。符合路径特征，但部分确认信号缺失 |
| `low` | < 0.55 | 低可信。仅部分特征匹配，建议人工复核 |

### 分数构成

| 信号 | 加分 | 说明 |
|------|------|------|
| AST 解析命中 | 基础 0.55 | 使用自定义 AST 识别 |
| 正则解析命中 | 基础 0.50 | 使用正则模式匹配 |
| 路径格式合法 | +0.15~0.25 | 路径符合 `/path/{var}/:param` 规范 |
| HTTP 方法明确 | +0.10 | 非 `ANY`（确定为 GET/POST 等） |
| 注解/装饰器触发 | +0.10 | 由 `@GetMapping`、`@app.route` 等直接触发 |
| 有效参数 | +0.05 | 注解/调用有实际参数内容 |

### 使用示例

```javascript
// 只保留高置信度结果
const result = await scanner.scan({
  rootDir: './src',
  minConfidence: 'high',
});

// 查看每个接口的置信度详情
for (const ep of result.endpoints) {
  const { level, value, reason } = ep.confidence;
  console.log(`${ep.method} ${ep.path}`);
  console.log(`  置信度: ${level} (${(value * 100).toFixed(0)}%)`);
  console.log(`  原因:   ${reason}`);
}
// 示例输出：
// POST /api/users
//   置信度: high (85%)
//   原因:   ast-parsed, valid path, explicit method, decorator/annotation

// 按置信度统计
console.log(result.stats.byConfidence);
// → { high: 42, medium: 8, low: 3 }
```

---

## 常量路径解析

Java / Kotlin 项目中路径常由字符串常量拼接而成，SDK 内置常量解析引擎自动还原真实路径。

### 支持的模式

```java
// ① 同文件常量
private static final String BASE = "/api/v1";
@PostMapping(BASE + "/users")
// → /api/v1/users

// ② 字面量拼接（自动折叠）
private static final String VERSION = "/api" + "/v1";
@GetMapping(VERSION + "/health")
// → /api/v1/health

// ③ 跨文件静态引用
// GlobalConstant.java:  GLOBAL_VERSION = "/V1"
// UserController.java:  @PostMapping(GlobalConstant.GLOBAL_VERSION + "/users")
// → /V1/users

// ④ 多级传递（任意深度）
// GlobalConstant.java:  API_VER  = "/v1"
// BaseConfig.java:      ROOT     = API_VER + "/api"
// UserController.java:  @PostMapping(GlobalConstant.ROOT + "/users")
// → /v1/api/users

// ⑤ 类限定名引用
// BillService.java:  CONTROLLER_URL = "/billProcess"
// Controller.java:   @PostMapping(GlobalConstant.GLOBAL_VERSION
//                      + BillService.CONTROLLER_URL + "/start")
// → /V1/billProcess/start
```

### 解析机制

扫描前对全部文件做一次**全局常量预解析（pre-pass）**：

1. 收集所有文件中的 `String NAME = EXPR` 定义，同时以 `ClassName.NAME` 限定名存储
2. 迭代求值，每轮将已解析值代入未解析表达式（最多 30 轮）
3. 字面量拼接（`"a" + "b"`）在提取阶段立即折叠，不消耗迭代次数
4. 解析结果注入所有 Worker 线程的 parser，与各文件局部常量合并（局部优先）

### 无法解析时的占位符

当常量来自运行时注入、编译期宏或扫描范围之外的文件时，该部分以 `{CONST_NAME}` 占位：

```
/V1{MODULE_ATTENDANCE}/billProcess/start
```

将包含该常量的文件纳入扫描范围即可解析。

---

## 进度显示

适用于大型项目，让扫描过程可观察。

```javascript
const result = await scanner.scan({
  rootDir: './large-project',
  progress: true,           // 开启进度条
  progressInterval: 3000,   // 每 3 秒刷新（默认值）
});
```

- 进度输出到 **stderr**，不影响 stdout 的 JSON 输出或管道
- 使用 `\r` 原地刷新，不产生多余换行
- 扫描完成后打印最终 100% 行

```
[Scanner] [████████████░░░░░░░░░░░░░] 48% (144/300 files)
[Scanner] [█████████████████████████] 100% (300/300 files)
```

---

## 输出格式

### CSV

列顺序：`方法, 路径(原始), 路径(真实), 参数, 来源文件, 行号, 置信度`

```
方法,路径(原始),路径(真实),参数,来源文件,行号,置信度
POST,/V1/billProcess/start,/V1/billProcess/start,,/src/BillController.java,42,high(0.85)
GET,/users/{id},/users/1,id:path,/src/UserController.java,18,high(0.90)
```

### JSON

```json
{
  "generatedAt": "2025-06-01T10:00:00.000Z",
  "stats": { "totalFiles": 203, "scannedFiles": 47, "totalEndpoints": 5, "durationMs": 312 },
  "frameworks": ["Spring MVC", "Express"],
  "endpoints": [
    {
      "lineNumber": 42,
      "method": "POST",
      "rawPath": "/V1/billProcess/start",
      "resolvedPath": "/V1/billProcess/start",
      "params": "",
      "sourceFile": "/src/BillController.java",
      "confidence": "high(0.85)"
    }
  ]
}
```

### 手动导出 / OpenAPI

```javascript
const { writeReport, exportOpenAPI, toJSON, toYAML } = require('api-scanner-sdk');

// 手动写入报告
writeReport(result, { format: 'csv', filePath: './endpoints.csv' });
writeReport(result, { format: 'json', filePath: './endpoints.json' });

// 导出 OpenAPI 3.0
const doc = exportOpenAPI(result, {
  title: 'My API',
  version: '1.0.0',
  description: '由 api-scanner-sdk 自动生成',
});
require('fs').writeFileSync('openapi.json', toJSON(doc));
require('fs').writeFileSync('openapi.yaml', toYAML(doc));
```

---

### 过期行为

```javascript
// 过期后调用 Scanner 会立即抛出异常
const scanner = new Scanner();
// → Error: SDK license has expired. Please renew.
```

---

## 自定义规则

在任意目录下新建 YAML 文件，通过 `ruleDirs` 或 `addRuleDir()` 加载。

```yaml
# my-rules/my-framework.yaml

id: my-framework          # 规则唯一 ID
name: MyFramework         # 框架显示名称
language: typescript      # 目标语言（或数组: [java, kotlin]）

importIndicators:         # 文件中包含这些 import 时触发此规则
  - my-framework

# 可选：控制器类注解（Spring 风格）
controllerAnnotations:
  - MyController

# 可选：类级别基础路径注解
basePathAnnotation: MyRequestMapping

routePatterns:
  # 函数调用形式：router.get('/path', handler)
  - trigger: function_call
    names: [get, post, put, patch, delete]
    pathArgIndex: 0       # 路径参数位置（从 0 开始）
    method: from_name     # 从函数名推断 HTTP 方法

  # 装饰器/注解形式：@MyGet('/path')
  - trigger: decorator
    names: [MyGet, MyPost, MyPut, MyDelete]
    pathArgIndex: 0
    method: from_name
    methodFromNameMap:
      MyGet:    GET
      MyPost:   POST
      MyPut:    PUT
      MyDelete: DELETE

  # 固定方法形式：@MyRoute('/path')（始终为 POST）
  - trigger: annotation
    names: [MyRoute]
    pathArgName: value    # 命名参数：@MyRoute(value="/path")
    method: POST

  # C# 属性形式：[HttpGet("path")]
  - trigger: attribute
    names: [HttpGet, HttpPost]
    pathArgIndex: 0
    method: from_name
```

### TriggerType 说明

| 值 | 适用场景 | 示例 |
|----|---------|------|
| `decorator` | Python / TypeScript 装饰器 | `@app.route('/path')` |
| `annotation` | Java / Kotlin 注解 | `@GetMapping("/path")` |
| `function_call` | 链式 / 函数调用 | `router.get('/path', handler)` |
| `attribute` | C# 特性 | `[HttpGet("path")]` |
| `comment` | 注释中的路由声明 | `// GET /path` |

---

## 自定义 Parser

实现 `IParser` 接口可接入自定义解析逻辑（如 tree-sitter、LLM 等）：

```typescript
import { IParser, ApiEndpoint, FrameworkRule, Language } from 'api-scanner-sdk';

class MyParser implements IParser {
  readonly supportedLanguages: Language[] = ['typescript', 'javascript'];

  async parse(
    filePath: string,
    content: string,
    rules: FrameworkRule[],
  ): Promise<ApiEndpoint[]> {
    const endpoints: ApiEndpoint[] = [];
    // 自定义解析逻辑...
    return endpoints;
  }
}

// 注册到 Scanner（在主线程顺序执行）
const result = await new Scanner()
  .addParser(new MyParser())
  .scan({ rootDir: './src' });
```

> 自定义 Parser 在主线程中运行，不受 Worker 线程限制，可使用任意类实例和闭包。

---

## 支持的框架

| 语言 | 框架 |
|------|------|
| TypeScript / JavaScript | Express, NestJS, Fastify, Hono, Koa |
| Python | FastAPI, Flask, Django |
| Java / Kotlin | Spring MVC, Spring WebFlux, JAX-RS, Micronaut, Quarkus |
| Go | Gin, Echo, Fiber, Chi, net/http |
| Ruby | Rails, Sinatra |
| PHP | Laravel, Symfony |
| C# | ASP.NET Core（通过自定义规则）|

---
