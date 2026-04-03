package com.example.multifile.crossref;

import org.springframework.web.bind.annotation.*;
import com.example.multifile.crossref.constants.Versions;
import com.example.multifile.crossref.constants.ResourcePaths;

/**
 * Second controller in the crossref scenario.
 * Uses the same Versions class but a different ResourcePaths field.
 *   @RequestMapping(Versions.CURRENT + ResourcePaths.ACCOUNTS)
 *   → base = "/v3/accounts"
 *
 * Verifies that selecting a different field from the same constants file
 * produces a distinct base path without contaminating BillingController.
 *
 * Expected endpoints:
 *   GET  /v3/accounts
 *   GET  /v3/accounts/{accountId}
 *   POST /v3/accounts
 */
@RestController
@RequestMapping(Versions.CURRENT + ResourcePaths.ACCOUNTS)
public class AccountsController {

    @GetMapping("")
    public Object listAccounts() { return null; }

    @GetMapping("/{accountId}")
    public Object getAccount() { return null; }

    @PostMapping("")
    public Object createAccount() { return null; }
}
