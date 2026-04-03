package com.example.controllers;

import org.springframework.web.bind.annotation.*;
import com.example.service.a.ServiceConfig;

/**
 * Tests import-based constant disambiguation.
 * Imports ServiceConfig from package "a" (BASE="/svc-a", VER="/v1").
 * Expected endpoints must use package-a values, not package-b values.
 */
@RestController
@RequestMapping(ServiceConfig.BASE)
public class ServiceAController {

    // /svc-a/v1/items  (VER from pkg-a = "/v1")
    @GetMapping(ServiceConfig.VER + "/items")
    public Object items() { return null; }

    // /svc-a/ping
    @GetMapping("/ping")
    public Object ping() { return null; }
}
