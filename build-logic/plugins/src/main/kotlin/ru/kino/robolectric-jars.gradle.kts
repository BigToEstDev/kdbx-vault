/**
 * The image of Android the Robolectric host tests run on (`android-all-instrumented`, the `sdk` of
 * `robolectric.properties`) - resolved by Gradle and cached with its dependencies, not downloaded by the tests from
 * Maven Central as they run: a runner of CI refused that download (pass-docs, Step 136). With
 * `robolectric.dependency.dir` set Robolectric never goes to the network; an image missing from it fails the test
 * at once ("Path is not a file").
 */

val androidAll: Configuration = configurations.create("robolectricAndroidAll") {
    isCanBeConsumed = false
    isCanBeResolved = true
    isTransitive = false
}

val catalog: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    add(androidAll.name, catalog.findLibrary("robolectric-android-all").get())
}

val jars = tasks.register<Sync>("robolectricJars") {
    from(androidAll)
    into(layout.buildDirectory.dir("robolectric-jars"))
}

// The host tests of the android target of a KMP module (`withHostTest` of kmp-lib)
tasks.withType<Test>().matching { it.name == "testAndroidHostTest" }.configureEach {
    inputs.files(jars).withPropertyName("robolectricJars").withPathSensitivity(PathSensitivity.NAME_ONLY)
    systemProperty("robolectric.dependency.dir", jars.get().destinationDir.absolutePath)
}
