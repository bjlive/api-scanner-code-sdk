package com.example.controllers;

import org.springframework.web.bind.annotation.*;
import com.example.constants.ApiConstants;

/**
 * Tests that commented-out annotations are NOT emitted as endpoints.
 *
 * Anti-patterns that must NOT appear in results:
 *   - Single-line comment: // @GetMapping("/should-not-appear")
 *   - Block comment:       /* @PostMapping("/also-not") * /
 *   - Commented constant:  // String COMMENTED_PATH = "/bad"
 *
 * Live endpoints that MUST appear:
 *   GET  /api/v1/users/active
 *   PUT  /api/v1/users/{id}/role
 */
@RestController
@RequestMapping(ApiConstants.V1 + ApiConstants.USERS)
public class CommentedController {

    // @GetMapping("/should-not-appear")
    // public Object commentedOut() { return null; }

    /* @PostMapping("/also-not-appear")
       public Object alsoCommented() { return null; } */

    /**
     * @GetMapping("/javadoc-not-an-endpoint") — in Javadoc, must be ignored
     */
    @GetMapping("/active")
    public Object listActive() { return null; }

    @PutMapping("/{id}/role")
    public Object changeRole() { return null; }
}
