import fastify from 'fastify';

const server = fastify({ logger: true });

// Tests all supported HTTP methods for Fastify
server.get('/ping',         async () => ({ pong: true }));
server.get('/users',        async () => []);
server.get('/users/:id',    async (req) => req.params);
server.post('/users',       async () => ({}));
server.put('/users/:id',    async () => ({}));
server.patch('/users/:id',  async () => ({}));
server.delete('/users/:id', async () => ({}));

// Route with options object (still first arg is path)
server.get('/status', { schema: {} }, async () => ({ ok: true }));

server.listen({ port: 3000 });
