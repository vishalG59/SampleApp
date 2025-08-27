pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "SampleApp"
include(":app")
include(":core:network")
include(":core:ui")
include(":core:data")
include(":core:domain")
include(":feature:recipehub:data")
include(":feature:recipehub:presentation")
include(":feature:recipehub:domain")
