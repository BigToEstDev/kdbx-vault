pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        // The Gradle plugin of AboutLibraries is published to the portal only (pass-docs, Step 129)
        gradlePluginPortal {
            content { includeGroup("com.mikepenz.aboutlibraries.plugin") }
        }
    }

    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"
include(":plugins")
