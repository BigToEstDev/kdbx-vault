package ru.kino.dev.desktop.di

import org.koin.dsl.module
import ru.kino.dev.launch.DesktopLaunch
import ru.kino.dev.settings.about.AppInfo
import ru.kino.dev.settings.about.BuildType

/**
 * What the desktop app tells of itself on the screen "About" (pass-docs, Step 122): the properties the build puts on
 * its launch (`desktopApp/build.gradle.kts`). A release or a debug build - [DesktopLaunch]; the version of a debug
 * build marked "-debug" as on android (pass-docs, Step 123).
 */
val appInfoModule = module {
    single {
        val buildType = if (DesktopLaunch.installed) BuildType.Release else BuildType.Debug
        AppInfo(
            version = property(VERSION) + if (buildType == BuildType.Debug) DEBUG_SUFFIX else "",
            buildType = buildType,
            repoUrl = property(REPO_URL),
            coreRepoUrl = property(CORE_REPO_URL),
            contactEmail = property(CONTACT_EMAIL),
            siteUrl = property(SITE_URL),
        )
    }
}

/** A property the build always sets: without it the launch is broken - better seen at once. */
private fun property(name: String): String =
    checkNotNull(System.getProperty(name)) { "The launch of the app has no -D$name" }

private const val VERSION = "app.version"
private const val REPO_URL = "app.repoUrl"
private const val CORE_REPO_URL = "app.coreRepoUrl"
private const val CONTACT_EMAIL = "app.contactEmail"
private const val SITE_URL = "app.siteUrl"

/** What the version of a debug build ends with - as `versionNameSuffix` of android. */
private const val DEBUG_SUFFIX = "-debug"
