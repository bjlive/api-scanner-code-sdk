package com.example.multifile.samepackage;

import org.springframework.web.bind.annotation.*;

/**
 * Tests same-package constant access WITHOUT an explicit import statement.
 * RouteConstants lives in the same package (com.example.multifile.samepackage),
 * so Java allows access to it without `import`.
 *
 * The scanner must detect that RouteConstants is a sibling class in the same
 * package and inject its constants into this file's resolution context.
 *
 *   @RequestMapping(RouteConstants.BASE + RouteConstants.KEYS)
 *   → base = "/internal" + "/keys" = "/internal/keys"
 *
 * Expected endpoints:
 *   GET    /internal/keys
 *   POST   /internal/keys
 *   DELETE /internal/keys/{keyId}
 */
@RestController
@RequestMapping(RouteConstants.BASE + RouteConstants.KEYS)
public class InternalController {

    @GetMapping("")
    public Object listKeys() { return null; }

    @PostMapping("")
    public Object createKey() { return null; }

    @DeleteMapping("/{keyId}")
    public Object revokeKey() { return null; }
}
