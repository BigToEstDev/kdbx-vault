package ru.kino.dev.settings.pres.about

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.orbitmvi.orbit.test.testWithInternalState
import ru.kino.dev.navigation.MviNavEvent
import ru.kino.dev.navigation.Navigator
import ru.kino.dev.settings.about.AppInfo
import ru.kino.dev.settings.about.BuildType
import ru.kino.dev.settings.about.FakeCoreVersionRepository
import ru.kino.dev.settings.pres.testing.RecordingLog
import ru.kino.dev.settings.pres.testing.recordNavigation
import ru.kino.dev.settings.pres.testing.runToEnd

class AboutProcessorTest {

    private val info = AppInfo(
        version = "1.2.3",
        buildType = BuildType.Release,
        repoUrl = "https://github.com/owner/app/",
        contactEmail = "mail@example.com",
        siteUrl = "https://owner.example.io/app/",
    )

    @Test
    fun `what the app tells of itself is shown at once`() = runTest {
        val state = processor(recordNavigation().first).container.stateFlow.value

        assertEquals(
            AboutState(
                version = "1.2.3",
                buildType = BuildType.Release,
                email = "mail@example.com",
                repoUrl = "https://github.com/owner/app/",
                siteUrl = "https://owner.example.io/app/",
            ),
            state,
        )
        assertEquals("github.com/owner/app", state.repoShown)
        assertEquals("owner.example.io/app", state.siteShown)
    }

    @Test
    fun `back leaves the screen`() = runTest {
        val (navigator, events) = recordNavigation()

        processor(navigator).testWithInternalState(this) { containerHost.runToEnd { onBack() } }

        assertEquals(listOf<MviNavEvent>(MviNavEvent.NavigateBack), events)
    }

    @Test
    fun `the email is copied`() = runTest {
        processor(recordNavigation().first).testWithInternalState(this) {
            containerHost.onCopyEmail()

            expectSideEffect(AboutSideEffect.CopyEmail("mail@example.com"))
        }
    }

    @Test
    fun `a bug is reported in the issues of the repository`() = runTest {
        processor(recordNavigation().first).testWithInternalState(this) {
            containerHost.onReportIssue()

            expectSideEffect(AboutSideEffect.OpenLink("https://github.com/owner/app/issues"))
        }
    }

    @Test
    fun `the source code opens the repository`() = runTest {
        processor(recordNavigation().first).testWithInternalState(this) {
            containerHost.onOpenSourceCode()

            expectSideEffect(AboutSideEffect.OpenLink("https://github.com/owner/app/"))
        }
    }

    @Test
    fun `the site opens`() = runTest {
        processor(recordNavigation().first).testWithInternalState(this) {
            containerHost.onOpenSite()

            expectSideEffect(AboutSideEffect.OpenLink("https://owner.example.io/app/"))
        }
    }

    @Test
    fun `the privacy policy opens its page of the site`() = runTest {
        processor(recordNavigation().first).testWithInternalState(this) {
            containerHost.onOpenPrivacyPolicy()

            expectSideEffect(AboutSideEffect.OpenLink("https://owner.example.io/app/privacy"))
        }
    }

    @Test
    fun `the support opens its page of the site`() = runTest {
        processor(recordNavigation().first).testWithInternalState(this) {
            containerHost.onSupport()

            expectSideEffect(AboutSideEffect.OpenLink("https://owner.example.io/app/donate"))
        }
    }

    @Test
    fun `the version of the core is shown once the core answers`() = runTest {
        val state = loaded(FakeCoreVersionRepository(version = "1.2.3"))

        assertEquals(CoreVersionShown.Known("1.2.3"), state.coreVersion)
    }

    @Test
    fun `the core not answering - the version is unknown and the failure logged`() = runTest {
        val state = loaded(FakeCoreVersionRepository(fail = true))

        assertEquals(CoreVersionShown.Unknown, state.coreVersion)
        assertEquals(1, log.logged.size)
    }

    private val log = RecordingLog()

    private suspend fun TestScope.loaded(versions: FakeCoreVersionRepository): AboutState {
        lateinit var state: AboutState
        AboutProcessor(info, versions, recordNavigation().first, log).testWithInternalState(this) {
            containerHost.onLoad()
            state = awaitInternalState()
        }
        return state
    }

    private fun processor(navigator: Navigator) = AboutProcessor(info, FakeCoreVersionRepository(), navigator, log)
}
