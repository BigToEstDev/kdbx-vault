package ru.kino.dev.log

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class WithoutMessagesTest {

    @Test
    fun `the texts are left out - the types and the frames stay`() {
        val failure = IllegalStateException(
            "content://secret/base.kdbx",
            java.io.FileNotFoundException("base.kdbx: no access"),
        )

        val written = failure.withoutMessages()

        assertFalse(written.contains("secret"))
        assertFalse(written.contains("base.kdbx"))
        assertTrue(written.startsWith("java.lang.IllegalStateException"))
        assertTrue(written.contains("Caused by: java.io.FileNotFoundException"))
        assertTrue(written.contains("\tat ru.kino.dev.log.WithoutMessagesTest"))
    }
}
