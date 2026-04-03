package com.example.controllers;

import org.springframework.web.bind.annotation.*;
import com.example.constants.ApiConstants;

/**
 * Tests:
 *   - Class base path built from multi-segment constant concat (ApiConstants.V1 + ApiConstants.USERS)
 *   - All five HTTP-method shortcut annotations
 *   - Empty string path ("") → inherits base path exactly
 *   - Path param in annotation string
 *   - Named-arg form: @GetMapping(value = "/search")
 *   - Named-arg form with constant concat: @GetMapping(value = ApiConstants.V1 + "/abs")
 */
@RestController
@RequestMapping(ApiConstants.V1 + ApiConstants.USERS)
public class UserController {

    // "" → base path /api/v1/users
    @GetMapping("")
    public Object list() { return null; }

    // /{id} → /api/v1/users/{id}
    @GetMapping("/{id}")
    public Object getById() { return null; }

    // POST "" → /api/v1/users
    @PostMapping("")
    public Object create() { return null; }

    // PUT /{id} → /api/v1/users/{id}
    @PutMapping("/{id}")
    public Object update() { return null; }

    // DELETE /{id} → /api/v1/users/{id}
    @DeleteMapping("/{id}")
    public Object delete() { return null; }

    // PATCH /{id}/status → /api/v1/users/{id}/status
    @PatchMapping("/{id}/status")
    public Object patchStatus() { return null; }

    // Named-arg literal → /api/v1/users/search
    @GetMapping(value = "/search")
    public Object search() { return null; }

    // Named-arg with concat → /api/v1/users/api/v1/abs
    @GetMapping(value = ApiConstants.V1 + "/abs")
    public Object abs() { return null; }
}
