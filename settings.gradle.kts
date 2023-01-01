@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("build-logic")
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

rootProject.name = "SoMovie"

include(":app")
include(":core:common")
include(":core:logger")
include(":core:imageurlprovider")
include(":data:database")
include(":data:datastore")
include(":data:mapper")
include(":data:network")
include(":data:repository")
include(":domain:exception")
include(":domain:model")
include(":domain:usecase")
include(":feature:home:account")
include(":feature:home:common")
include(":feature:home:container")
include(":feature:home:discover")
include(":feature:home:explore")
include(":feature:home:watchlist")
include(":feature:login")
include(":feature:moviedetails")
include(":feature:movielist")
include(":feature:navigation")
include(":test:fixtures")
include(":test:shared")
include(":ui:common")
include(":ui:component")
include(":ui:theme")
