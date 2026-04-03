package com.example.multifile.chain.constants;

/**
 * Mid-level constants that compose BaseRoutes values.
 * The resolver must expand BaseRoutes.ROOT + BaseRoutes.VER + "/catalog"
 * before CatalogController can use ComposedRoutes.CATALOG as a base path.
 *
 * Expected resolved value:  CATALOG = "/api/v2/catalog"
 */
public interface ComposedRoutes {
    String CATALOG = BaseRoutes.ROOT + BaseRoutes.VER + "/catalog";
}
