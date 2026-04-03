package com.example.controllers;

import org.springframework.web.bind.annotation.*;
import com.example.constants.ApiConstants;

/**
 * Tests Spring MVC array-form path annotation:
 *   @PostMapping(value = { expr1, expr2 })  — named-arg array
 *   @GetMapping({ expr1, expr2 })           — positional array (implicit value)
 *
 * Each array element must produce an independent endpoint.
 * Elements can be:
 *   - A string literal                    "/oauth/token"
 *   - A constant reference                ApiConstants.V1 + "/token"
 *   - A concat of multiple constants      ApiConstants.V1 + ApiConstants.USERS + "/search"
 *
 * Expected endpoints:
 *   POST  /api/v1/auth/token          ← ApiConstants.V1 + "/auth/token"
 *   POST  /oauth/token                ← literal fallback
 *   GET   /api/v1/users/search        ← concat of V1 + USERS + "/search"
 *   GET   /users/search               ← literal fallback
 *   DELETE /api/v1/users/{id}         ← positional array element 1
 *   DELETE /users/{id}                ← positional array element 2 (literal)
 */
@RestController
public class TokenController {

    // named-arg array: one concat + one literal
    @PostMapping(value = {ApiConstants.V1 + "/auth/token", "/oauth/token"})
    public Object token() { return null; }

    // named-arg array: two concat elements
    @GetMapping(value = {ApiConstants.V1 + ApiConstants.USERS + "/search", "/users/search"})
    public Object search() { return null; }

    // positional array (no "value =" key): literal + concat
    @DeleteMapping({"/users/{id}", ApiConstants.V1 + ApiConstants.USERS + "/{id}"})
    public Object delete() { return null; }
}
