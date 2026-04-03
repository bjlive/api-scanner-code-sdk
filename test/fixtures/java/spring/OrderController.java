package com.example.controllers;

import org.springframework.web.bind.annotation.*;

/**
 * Tests:
 *   - Plain string literal base path (no constants)
 *   - @RequestMapping with value= named arg
 *   - No-leading-slash method path → combined cleanly with base
 *   - Double-slash normalisation (base ends with "/" + method starts with "/")
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    // Positional string → /orders
    @GetMapping("")
    public Object list() { return null; }

    // Path param → /orders/{id}
    @GetMapping("/{id}")
    public Object get() { return null; }

    // No leading slash on method path → /orders/create (not /orders//create)
    @PostMapping("create")
    public Object create() { return null; }

    // Double-slash normalisation: base=/orders, method=//detail → /orders/detail
    @PutMapping("//{id}")
    public Object update() { return null; }

    // DELETE → /orders/{id}
    @DeleteMapping("/{id}")
    public Object delete() { return null; }

    // @RequestMapping with value= named arg → /orders/admin
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public Object admin() { return null; }
}
