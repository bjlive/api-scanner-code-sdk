package com.example.multifile.crossref;

import org.springframework.web.bind.annotation.*;
import com.example.multifile.crossref.constants.Versions;
import com.example.multifile.crossref.constants.ResourcePaths;

/**
 * Tests cross-file concat: TWO separate constant classes combined in one annotation.
 *   @RequestMapping(Versions.CURRENT + ResourcePaths.BILLING)
 *   → base = "/v3" + "/billing" = "/v3/billing"
 *
 * Both constants are imported from different files. The scanner must resolve
 * each operand independently and produce the correct joined path.
 *
 * Expected endpoints:
 *   GET    /v3/billing/invoices
 *   POST   /v3/billing/invoices
 *   GET    /v3/billing/invoices/{id}
 *   DELETE /v3/billing/invoices/{id}
 */
@RestController
@RequestMapping(Versions.CURRENT + ResourcePaths.BILLING)
public class BillingController {

    @GetMapping("/invoices")
    public Object listInvoices() { return null; }

    @PostMapping("/invoices")
    public Object createInvoice() { return null; }

    @GetMapping("/invoices/{id}")
    public Object getInvoice() { return null; }

    @DeleteMapping("/invoices/{id}")
    public Object deleteInvoice() { return null; }
}
