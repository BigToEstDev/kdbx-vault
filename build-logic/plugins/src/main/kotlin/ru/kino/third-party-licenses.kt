import groovy.json.JsonSlurper
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction

/** Where `third-party-licenses` puts the libraries of a module, under its build directory (pass-docs, Step 129). */
const val LIBRARIES_JSON = "thirdPartyLicenses/libraries.json"

/**
 * The libraries of every platform in one text (pass-docs, Step 129): the list - name, coordinates, versions,
 * licenses - then the text of each license once. Made of the exports of AboutLibraries ([LIBRARIES_JSON]); the crates
 * of the Rust bridge are added by the release workflow (`cargo about`).
 */
abstract class ThirdPartyLicensesTask : DefaultTask() {

    /** `libraries.json` of the modules - android, desktop. */
    @get:InputFiles
    @get:PathSensitive(PathSensitivity.NONE)
    abstract val libraries: ConfigurableFileCollection

    @get:Input
    abstract val title: Property<String>

    @get:OutputFile
    abstract val output: RegularFileProperty

    @TaskAction
    fun write() {
        val versions = sortedMapOf<String, MutableSet<String>>()
        val names = mutableMapOf<String, String>()
        val usedLicenses = mutableMapOf<String, MutableSet<String>>()
        val licenses = sortedMapOf<String, Pair<String, String>>()

        libraries.files.sortedBy { it.path }.forEach { file ->
            val export = JsonSlurper().parse(file) as Map<*, *>
            (export["libraries"] as List<*>).forEach { entry ->
                val library = entry as Map<*, *>
                val id = library["uniqueId"] as String
                names.putIfAbsent(id, (library["name"] as String?) ?: id)
                versions.getOrPut(id) { sortedSetOf() } += (library["artifactVersion"] as String?) ?: "?"
                usedLicenses.getOrPut(id) { sortedSetOf() } += (library["licenses"] as List<*>).map { it as String }
            }
            (export["licenses"] as Map<*, *>).forEach { (key, value) ->
                val license = value as Map<*, *>
                licenses.putIfAbsent(
                    key as String,
                    ((license["name"] as String?) ?: key) to ((license["content"] as String?) ?: license["url"] as String? ?: ""),
                )
            }
        }

        val text = buildString {
            appendLine(title.get())
            appendLine(RULE)
            versions.forEach { (id, shipped) ->
                appendLine("${names.getValue(id)} - $id ${shipped.joinToString(" / ")} - ${usedLicenses.getValue(id).joinToString(" OR ")}")
            }
            licenses.forEach { (id, license) ->
                appendLine()
                appendLine(RULE)
                appendLine("$id - ${license.first}")
                appendLine(RULE)
                appendLine(license.second.plain().trim())
            }
        }
        output.get().asFile.apply { parentFile.mkdirs() }.writeText(text)
    }

    /** The text of a license without the marks of the SPDX templates: a variable - its original words. */
    private fun String.plain(): String =
        replace(SPDX_VARIABLE) { it.groupValues[1].trim() }.replace(SPDX_OPTIONAL, "")

    private companion object {
        val RULE = "=".repeat(80)
        val SPDX_VARIABLE = Regex("""<<var;.*?original=(.*?);match=.*?>>""")
        val SPDX_OPTIONAL = Regex("""<<(?:begin|end)Optional.*?>>""")
    }
}
