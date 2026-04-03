package com.example.multifile.shared;

import org.springframework.web.bind.annotation.*;
import com.example.multifile.shared.constants.ApiPaths;

/**
 * Same ApiPaths import, different field: ApiPaths.ORDERS.
 * Expected base: /v2/orders
 *
 * Expected endpoints:
 *   GET  /v2/orders
 *   POST /v2/orders
 *   GET  /v2/orders/{id}
 */
@RestController
@RequestMapping(ApiPaths.ORDERS)
public class OrdersV2Controller {

    @GetMapping("")
    public Object list() { return null; }

    @PostMapping("")
    public Object create() { return null; }

    @GetMapping("/{id}")
    public Object getById() { return null; }
}
