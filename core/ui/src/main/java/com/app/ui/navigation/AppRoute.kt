package com.app.ui.navigation

/**
 * Base sealed interface for all navigation routes in the app.
 * Each route should provide a 'path' that can be used with Navigation Compose.
 */
 interface AppRoute {
    val path: String
}

/**
 * Represents the entry point or graph route for a feature.
 * Useful if a feature itself is a nested navigation graph.
 *
 * @property startDestination The first screen in the feature's navigation graph.
 * @property routes All the screens in the feature's navigation graph.
 */
interface FeatureRoute : AppRoute {
    val startDestination: AppRoute
    val routes: List<AppRoute>
}
