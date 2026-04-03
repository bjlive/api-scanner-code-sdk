import { Controller, Get, Post, Put, Patch, Delete, Param, Body } from '@nestjs/common';

/**
 * Tests:
 *   - @Controller prefix used as base path
 *   - All five HTTP-method decorators with path arg
 *   - Path params inside decorator string (':id')
 */
@Controller('users')
export class UsersController {
  // /users/:id  (must NOT emit /users without :id — @Get() with empty arg is not emitted)
  @Get(':id')
  findOne(@Param('id') id: string) { return {}; }

  @Post('create')
  create(@Body() dto: any) { return {}; }

  @Put(':id')
  update(@Param('id') id: string) { return {}; }

  @Patch(':id/status')
  patchStatus(@Param('id') id: string) { return {}; }

  @Delete(':id')
  remove(@Param('id') id: string) { return {}; }
}

/**
 * Second controller — tests that @Controller prefix is independent per class.
 */
@Controller('products')
export class ProductsController {
  @Get(':id')
  findOne() { return {}; }

  @Post('new')
  create() { return {}; }
}
