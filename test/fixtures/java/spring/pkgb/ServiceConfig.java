package com.example.service.b;

/**
 * Constants for service-b.
 * Tests: import-based disambiguation — same simple class name, different package.
 * Controllers that import THIS class should get BASE="/svc-b" and VER="/v2".
 */
public interface ServiceConfig {
    String BASE = "/svc-b";
    String VER  = "/v2";
}
