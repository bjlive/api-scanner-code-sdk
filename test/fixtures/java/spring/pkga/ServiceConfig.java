package com.example.service.a;

/**
 * Constants for service-a.
 * Tests: import-based disambiguation — same simple class name, different package.
 * Controllers that import THIS class should get BASE="/svc-a" and VER="/v1".
 */
public interface ServiceConfig {
    String BASE = "/svc-a";
    String VER  = "/v1";
}
