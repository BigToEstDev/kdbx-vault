import com.mikepenz.aboutlibraries.plugin.StrictMode

/**
 * The libraries the app ships and their licenses (pass-docs, Step 129): `exportLibraryDefinitions` writes them into
 * [LIBRARIES_JSON] of the module; the root task `thirdPartyLicenses` makes the text attached to a release. No screen
 * in the app - the plugin of AboutLibraries only.
 *
 * A license not in the list fails the export: the app is GPL-3.0, a library under a license that does not go with it
 * must be seen before a release.
 */
plugins {
    id("com.mikepenz.aboutlibraries.plugin")
}

aboutLibraries {
    collect {
        // No GitHub API: the licenses come from the poms, their texts - from SPDX
        fetchRemoteLicense = false
        fetchRemoteFunding = false
    }
    export {
        outputFile = layout.buildDirectory.file(LIBRARIES_JSON).get().asFile
        excludeFields.addAll("developers", "funding", "description", "organization")
    }
    license {
        strictMode = StrictMode.FAIL
        allowedLicenses.addAll(
            "Apache-2.0",
            "MIT",
            "BSD-2-Clause",
            "BSD-3-Clause",
            // JNA - LGPL-2.1-or-later or Apache-2.0, both listed for it
            "LGPL-2.1-or-later",
        )
    }
}
