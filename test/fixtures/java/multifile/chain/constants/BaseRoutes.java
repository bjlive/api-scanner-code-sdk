package com.example.multifile.chain.constants;

/**
 * Leaf constants — simple literals only.
 * These are the "bottom" of the chain: CatalogController uses them transitively
 * through ComposedRoutes, NOT by importing this class directly.
 */
public interface BaseRoutes {
    String ROOT = "/api";
    String VER  = "/v2";
}
