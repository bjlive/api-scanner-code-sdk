package com.example.multifile.shared;

import org.springframework.web.bind.annotation.*;
import com.example.multifile.shared.constants.ApiPaths;

/**
 * Imports ApiPaths and uses ApiPaths.USERS as base.
 * Expected base: /v2/users
 *
 * Expected endpoints:
 *   GET    /v2/users
 *   POST   /v2/users
 *   GET    /v2/users/{id}
 *   DELETE /v2/users/{id}
 */
@RestController
@RequestMapping(ApiPaths.USERS)
public class UsersV2Controller {

    @GetMapping("")
    public Object list() { return null; }

    @PostMapping("")
    public Object create() { return null; }

    @GetMapping("/{id}")
    public Object getById() { return null; }

    @DeleteMapping("/{id}")
    public Object delete() { return null; }
}
