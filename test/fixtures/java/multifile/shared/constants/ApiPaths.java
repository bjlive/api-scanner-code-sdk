package com.example.multifile.shared.constants;

/**
 * Central path registry — all resource paths live here.
 * Three independent controllers import this single file and pick different fields.
 * Tests that the correct field value is used for each controller and that
 * no field "bleeds" into another controller's endpoints.
 */
public interface ApiPaths {
    String USERS    = "/v2/users";
    String ORDERS   = "/v2/orders";
    String PAYMENTS = "/v2/payments";
}
