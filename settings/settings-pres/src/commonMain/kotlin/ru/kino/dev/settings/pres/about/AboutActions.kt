package ru.kino.dev.settings.pres.about

/** What the screen "About" is asked to do - done by [AboutProcessor] (pass-docs, `docs/app/mvi-arc.md`). */
interface AboutActions {

    /** The screen is shown: the version of the core is asked (pass-docs, Step 123). */
    fun onLoad()

    /** "Back" in the top bar. */
    fun onBack()

    /** "Support the project" - its page on the site, in the browser (pass-docs, Step 129). */
    fun onSupport()

    /** The privacy policy - its page on the site, in the browser (pass-docs, Step 129). */
    fun onOpenPrivacyPolicy()

    /** The site - in the browser. */
    fun onOpenSite()

    /** The address to write to - copied. */
    fun onCopyEmail()

    /** "Report a bug" - the issues of the repository, in the browser. */
    fun onReportIssue()

    /** The source code - the repository of the app, in the browser. */
    fun onOpenSourceCode()

    /** The source code of the core - its repository, in the browser (pass-docs, Step 129). */
    fun onOpenCoreSourceCode()

    /** The licenses of the libraries - the file of the release, in the browser (pass-docs, Step 129). */
    fun onOpenLicenses()
}
