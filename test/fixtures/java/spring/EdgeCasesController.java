package com.example.controllers;

import org.springframework.web.bind.annotation.*;
import com.example.constants.ApiConstants;

/**
 * Tests the specific edge cases fixed during development:
 *
 *   1. Class base path that is a BARE CONSTANT (no concat, no string literal)
 *      @RequestMapping(ApiConstants.ORDERS) → base = /orders
 *
 *   2. Local class constant overrides global same-named constant
 *      CONTROLLER_URL = "/local-override" should shadow any global CONTROLLER_URL
 *
 *   3. Local constant as sole annotation arg
 *      @DeleteMapping(CONTROLLER_URL + "/remove") → /orders/local-override/remove
 *
 *   4. Class-level @RequestMapping must NOT be emitted as an endpoint itself
 *      (regression guard: line 19 should not appear in results)
 */
@RestController
@RequestMapping(ApiConstants.ORDERS)
public class EdgeCasesController {

    // Local constant — must shadow any global CONTROLLER_URL
    public static final String CONTROLLER_URL = "/local-override";

    // Local-constant concat → /orders/local-override/remove
    @DeleteMapping(CONTROLLER_URL + "/remove")
    public Object remove() { return null; }

    // Bare constant in annotation → /orders/local-override
    @GetMapping(CONTROLLER_URL)
    public Object getResource() { return null; }

    // No-leading-slash → /orders/sub (clean join)
    @PostMapping("sub")
    public Object sub() { return null; }
}
