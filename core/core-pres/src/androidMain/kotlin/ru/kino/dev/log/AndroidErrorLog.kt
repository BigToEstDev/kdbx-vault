package ru.kino.dev.log

import android.content.Context
import android.content.pm.ApplicationInfo
import android.util.Log
import org.koin.core.scope.Scope

/**
 * [ErrorLog] into logcat. A release writes the types and the stack only: the text of an exception may hold the uri or
 * the name of a database, and logcat of a release is read by others (pass-docs, Step 129). A debug build - in full.
 */
internal class AndroidErrorLog(private val withMessages: Boolean) : ErrorLog {

    override fun unexpected(where: String, error: Throwable) {
        if (withMessages) {
            Log.e(where, MESSAGE, error)
        } else {
            Log.e(where, "$MESSAGE\n${error.withoutMessages()}")
        }
    }

    private companion object {
        const val MESSAGE = "unexpected failure"
    }
}

/** The failure as a stack trace with no texts: the type of each cause and its frames. */
internal fun Throwable.withoutMessages(): String =
    generateSequence(this) { it.cause }
        .take(MAX_CAUSES)
        .withIndex()
        .joinToString("\n") { (index, failure) ->
            buildString {
                if (index > 0) append("Caused by: ")
                append(failure.javaClass.name)
                failure.stackTrace.forEach { frame -> append("\n\tat ").append(frame) }
            }
        }

/** Guards against a chain of causes that loops. */
private const val MAX_CAUSES = 16

internal actual fun Scope.platformErrorLog(): ErrorLog {
    val debuggable = get<Context>().applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
    return AndroidErrorLog(withMessages = debuggable)
}
