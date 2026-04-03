package com.example.multifile.shared;

import org.springframework.web.bind.annotation.*;
import com.example.multifile.shared.constants.ApiPaths;

/**
 * Same ApiPaths import, third field: ApiPaths.PAYMENTS.
 * Expected base: /v2/payments
 *
 * Expected endpoints:
 *   POST /v2/payments/charge
 *   GET  /v2/payments/{txId}
 */
@RestController
@RequestMapping(ApiPaths.PAYMENTS)
public class PaymentsV2Controller {

    @PostMapping("/charge")
    public Object charge() { return null; }

    @GetMapping("/{txId}")
    public Object getTransaction() { return null; }
}
