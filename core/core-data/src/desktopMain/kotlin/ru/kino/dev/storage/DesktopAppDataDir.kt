package ru.kino.dev.storage

import ru.kino.dev.launch.DesktopLaunch
import java.io.File

/**
 * `kdbxvault` in the directory each system keeps the data of an app of the user in: AppData, Application Support,
 * XDG. Only the settings live here, never a database (pass-docs, Step 86). A debug build has a directory of its own -
 * `kdbxvault-debug`: it remembers its own database and settings, as the package of its own on android (pass-docs,
 * Step 135).
 */
internal class DesktopAppDataDir : AppDataDir {

    override val path: String by lazy {
        val home = System.getProperty("user.home")
        val os = System.getProperty("os.name").lowercase()
        val base = when {
            os.contains("win") -> System.getenv("APPDATA") ?: File(home, "AppData/Roaming").path
            os.contains("mac") -> "$home/Library/Application Support"
            else -> System.getenv("XDG_DATA_HOME") ?: "$home/.local/share"
        }
        val dir = if (DesktopLaunch.installed) APP_DIR else APP_DIR + DEBUG_SUFFIX
        File(base, dir).apply { mkdirs() }.absolutePath
    }

    private companion object {
        const val APP_DIR = "kdbxvault"
        const val DEBUG_SUFFIX = "-debug"
    }
}
