package ru.kino.dev.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable

/**
 * What is typed to find one of [options] - shared by [SearchableDropdown] and [SearchableList] (pass-docs, Step 134).
 *
 * The field shows the title of the [selectedTitle] one; text that is no longer it takes the choice away - onClear.
 * With a choice in the field every option is shown: the list is looked at to change it, not to search; without one
 * the list keeps the options whose title or subtitle holds the text, ignoring case.
 */
internal class OptionSearch<T>(
    private val typed: MutableState<String>,
    private val selectedTitle: String?,
    private val options: List<DropdownOption<T>>,
    private val onClear: () -> Unit,
) {
    val text: String get() = typed.value

    val shown: List<DropdownOption<T>>
        get() {
            val query = if (selectedTitle != null) "" else text.trim()
            return if (query.isEmpty()) options else options.filter { it.holds(query) }
        }

    fun type(value: String) {
        typed.value = value
        if (selectedTitle != null && value != selectedTitle) onClear()
    }

    /** [option] picked: its title in the field. The caller says who picked it. */
    fun pick(option: DropdownOption<T>) {
        typed.value = option.title
    }
}

@Composable
internal fun <T> rememberOptionSearch(
    selected: T?,
    options: List<DropdownOption<T>>,
    onClear: () -> Unit,
): OptionSearch<T> {
    val selectedTitle = options.firstOrNull { it.value == selected }?.title
    val typed = rememberSaveable { mutableStateOf(selectedTitle.orEmpty()) }
    // A choice made, or the options read after the field: the field shows its title. None - what is typed stays
    LaunchedEffect(selectedTitle) { selectedTitle?.let { typed.value = it } }
    return OptionSearch(typed, selectedTitle, options, onClear)
}

/** The choice holds [query] in its title or its subtitle - the path, for a group - ignoring case. */
private fun DropdownOption<*>.holds(query: String): Boolean =
    title.contains(query, ignoreCase = true) || subtitle?.contains(query, ignoreCase = true) == true
