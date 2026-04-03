package com.example.controllers;

import org.springframework.web.bind.annotation.*;
import com.example.service.b.ServiceConfig;

/**
 * Tests import-based constant disambiguation.
 * Imports ServiceConfig from package "b" (BASE="/svc-b", VER="/v2").
 * Expected endpoints must differ from ServiceAController despite same class name.
 */
@RestController
@RequestMapping(ServiceConfig.BASE)
public class ServiceBController {

    // /svc-b/v2/items  (VER from pkg-b = "/v2")
    @GetMapping(ServiceConfig.VER + "/items")
    public Object items() { return null; }

    // /svc-b/ping
    @GetMapping("/ping")
    public Object ping() { return null; }
}
