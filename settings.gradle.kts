import java.net.URL

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
include(":authentication")
include(":account")
include(":core")
include(":tvShow:ui")
include(":tvShow:tvShowComponent")
