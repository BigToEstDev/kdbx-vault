package ru.kino.dev.log

import org.koin.core.scope.Scope

/** [ErrorLog] of the platform: logcat on android, the error stream on the desktop. Koin gives what the platform needs. */
internal expect fun Scope.platformErrorLog(): ErrorLog
