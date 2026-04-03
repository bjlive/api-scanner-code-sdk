package com.example.multifile.chain;

import org.springframework.web.bind.annotation.*;
import com.example.multifile.chain.constants.ComposedRoutes;

/**
 * Tests 3-level constant chain resolution across files:
 *   BaseRoutes.ROOT ("/api") + BaseRoutes.VER ("/v2") + "/catalog"
 *   → ComposedRoutes.CATALOG = "/api/v2/catalog"
 *   → @RequestMapping(ComposedRoutes.CATALOG) → base = /api/v2/catalog
 *
 * The controller imports only ComposedRoutes — it never sees BaseRoutes directly.
 * The scanner must resolve the chain through multi-file constant propagation.
 *
 * Expected endpoints:
 *   GET    /api/v2/catalog/items
 *   POST   /api/v2/catalog/items
 *   GET    /api/v2/catalog/items/{sku}
 *   DELETE /api/v2/catalog/items/{sku}
 */
@RestController
@RequestMapping(ComposedRoutes.CATALOG)
public class CatalogController {

    @GetMapping("/items")
    public Object listItems() { return null; }

    @PostMapping("/items")
    public Object createItem() { return null; }

    @GetMapping("/items/{sku}")
    public Object getItem() { return null; }

    @DeleteMapping("/items/{sku}")
    public Object deleteItem() { return null; }
}
