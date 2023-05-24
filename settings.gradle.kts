@file:Suppress("UnstableApiUsage")

include(":account:core:data")


include(":account:core:network")


include(":account:core")


pluginManagement {
    repositories {
        google()
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

rootProject.name = "TMDA"
include(":app",":movies",":tv",":authentication",":account", ":shared")
