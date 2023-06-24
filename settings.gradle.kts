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
        maven ("https://oss.sonatype.org/content/repositories/snapshots/" )
    }
}

rootProject.name = "TMDA"
include(":app")
include(":moviesComponent")
include(":tvShowComponent")
include(":userComponent")
include(":sharedComponent")
include(":moviesFeature")
include(":sharedUi")
include(":tvShowFeature")
include(":searchFeature")
include(":profileFeature")
