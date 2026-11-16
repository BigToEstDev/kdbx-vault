package ru.kino.dev.settings.about

/**
 * What the app tells of itself on the screen "About" (pass-docs, Step 122). Given by the platform: the build knows the
 * version and its type - `BuildConfig` on android, the launcher on the desktop; the addresses come from
 * `gradle.properties`, one place for both.
 *
 * @param version the version of the build, as the person sees it
 * @param buildType a debug build or one for a release
 * @param repoUrl the public repository of the app - not of the core
 * @param coreRepoUrl the public repository of the core
 * @param contactEmail where to write
 * @param siteUrl the site of the app
 */
data class AppInfo(
    val version: String,
    val buildType: BuildType,
    val repoUrl: String,
    val coreRepoUrl: String,
    val contactEmail: String,
    val siteUrl: String,
) {

    /** Where bugs are reported - the issues of the repository of the app. */
    val issuesUrl: String = "${repoUrl.trimEnd('/')}/issues"

    /** The privacy policy - a page of the site (pass-docs, Step 129). */
    val privacyPolicyUrl: String = "${siteUrl.trimEnd('/')}/privacy"

    /** "Support the project" - a page of the site (pass-docs, Step 129). */
    val supportUrl: String = "${siteUrl.trimEnd('/')}/donate"

    /**
     * The libraries of the app and their licenses - the file of the release of this version (pass-docs, Step 129); its
     * name is given by `release.yml`. A debug build has no release of its own - the link leads nowhere.
     */
    val licensesUrl: String =
        "${repoUrl.trimEnd('/')}/releases/download/v$version/kdbx-vault-$version-THIRD_PARTY_LICENSES.txt"
}

/** The type of the build. */
enum class BuildType { Debug, Release }
