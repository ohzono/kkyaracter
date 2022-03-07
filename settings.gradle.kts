pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "kyaracter"
include(":android")
include(":infra:repository")
include(":infra:db")
include(":model")
include(":domain")
include(":presentation:uimodel")
 