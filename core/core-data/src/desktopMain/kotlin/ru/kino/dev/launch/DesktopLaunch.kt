package ru.kino.dev.launch

/**
 * How the desktop app was started - the one place that tells a release from a debug build (pass-docs, Step 135).
 * A release is the app installed: the launcher of jpackage names its path; run from Gradle - a debug build.
 */
object DesktopLaunch {

    /** Installed with jpackage - a release; false when run from Gradle. */
    val installed: Boolean by lazy { System.getProperty(JPACKAGE_APP_PATH) != null }

    /** Set by the launcher of an app packaged with jpackage. */
    private const val JPACKAGE_APP_PATH = "jpackage.app-path"
}
