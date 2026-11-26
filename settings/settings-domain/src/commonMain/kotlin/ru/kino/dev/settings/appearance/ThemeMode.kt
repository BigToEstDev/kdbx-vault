package ru.kino.dev.settings.appearance

/** Light or dark: as the system is, or always one of them (pass-docs, `settings/settings.md`, "Appearance"). */
enum class ThemeMode {
    System,
    Light,
    Dark,
    ;

    companion object {
        /** Until one is chosen - dark, not as the system is (pass-docs, Step 135). */
        val DEFAULT: ThemeMode = Dark
    }
}
