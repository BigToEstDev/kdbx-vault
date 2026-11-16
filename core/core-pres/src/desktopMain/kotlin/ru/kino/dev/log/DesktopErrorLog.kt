package ru.kino.dev.log

import org.koin.core.scope.Scope

/** [ErrorLog] into the error stream of the process - what a console or the log of the launcher shows. */
internal class DesktopErrorLog : ErrorLog {

    override fun unexpected(where: String, error: Throwable) {
        System.err.println("$where: unexpected failure")
        error.printStackTrace()
    }
}

internal actual fun Scope.platformErrorLog(): ErrorLog = DesktopErrorLog()
