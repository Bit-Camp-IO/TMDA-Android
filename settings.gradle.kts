


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
include(":app")
include(":movies")

//include(":tvShow:ui")
include(":tvShow:tvShowComponent")
//include(":try")

include(":shared")
//
include(":authentication")
//include(":account")

