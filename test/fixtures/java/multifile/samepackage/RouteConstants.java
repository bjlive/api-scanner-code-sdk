package com.example.multifile.samepackage;

/**
 * Constants in the SAME package as InternalController.
 * InternalController can reference this class without any import statement,
 * because Java allows same-package access by default.
 *
 * The scanner's applyImportContext() must inject same-package constants
 * automatically when building the per-file constant map.
 */
public interface RouteConstants {
    String BASE = "/internal";
    String KEYS = "/keys";
}
