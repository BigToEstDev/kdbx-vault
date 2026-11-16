// Top-level build file where you can add configuration options common to all sub-projects/modules.
// AGP/Kotlin/Compose plugin versions are managed by the `build-logic` convention plugins (included build).
// Applied here with `apply false` so the classpath is the root's and every module can use the plain plugin ids
plugins {
    id("kmp-lib") apply false
}

allprojects {
    group = "ru.kino.dev"
}

// The libraries of the app and their licenses - one text for a release (pass-docs, Step 129); the crates of the bridge
// are added by release.yml
tasks.register<ThirdPartyLicensesTask>("thirdPartyLicenses") {
    group = "release"
    description = "Writes the libraries of android and the desktop with the texts of their licenses"
    dependsOn(":app:exportLibraryDefinitions", ":desktopApp:exportLibraryDefinitions")
    libraries.from(
        layout.projectDirectory.file("app/build/$LIBRARIES_JSON"),
        layout.projectDirectory.file("desktopApp/build/$LIBRARIES_JSON"),
    )
    title = "KDBX Vault - the libraries of the app (Android, desktop) and their licenses"
    output = layout.buildDirectory.file("thirdPartyLicenses/libraries.txt")
}
