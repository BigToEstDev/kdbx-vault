package ru.kino.dev.settings.pres.about

import ru.kino.dev.settings.about.AppInfo
import ru.kino.dev.settings.about.BuildType

/**
 * What the screen "About" shows (pass-docs, Step 122): the app is known when the screen opens; the version of the
 * core is asked of the core (Step 123).
 *
 * @param version the version of the build
 * @param buildType a debug build or one for a release
 * @param email the address to write to
 * @param repoUrl the repository of the app
 * @param coreRepoUrl the repository of the core
 * @param siteUrl the site of the app
 * @param coreVersion the version of the core, as far as it is known
 */
data class AboutState(
    val version: String,
    val buildType: BuildType,
    val email: String,
    val repoUrl: String,
    val coreRepoUrl: String,
    val siteUrl: String,
    val coreVersion: CoreVersionShown = CoreVersionShown.Loading,
) {

    /** The repository as it reads - with no scheme: `github.com/…`. */
    val repoShown: String = repoUrl.shown()

    /** The repository of the core as it reads - with no scheme. */
    val coreRepoShown: String = coreRepoUrl.shown()

    /** The site as it reads - with no scheme. */
    val siteShown: String = siteUrl.shown()

    companion object {

        fun of(info: AppInfo) = AboutState(
            version = info.version,
            buildType = info.buildType,
            email = info.contactEmail,
            repoUrl = info.repoUrl,
            coreRepoUrl = info.coreRepoUrl,
            siteUrl = info.siteUrl,
        )
    }
}

/** An address as it reads - with no scheme and no trailing slash. */
private fun String.shown(): String = substringAfter("://").trimEnd('/')

/** The version of the core on the screen. */
sealed interface CoreVersionShown {

    /** Asked, not answered yet - a skeleton in its place. */
    data object Loading : CoreVersionShown

    data class Known(val version: String) : CoreVersionShown

    /** The core did not answer: the line says the version is unknown. */
    data object Unknown : CoreVersionShown
}
