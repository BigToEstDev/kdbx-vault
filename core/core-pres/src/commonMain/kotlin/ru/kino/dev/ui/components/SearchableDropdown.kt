package ru.kino.dev.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ru.kino.dev.core.pres.*
import ru.kino.dev.ui.res.stringResource

/**
 * A field that takes one of [options], as [Dropdown] does, and lets the person type to find it: the list drops as
 * they type and keeps the choices whose title or subtitle holds the text, ignoring case (pass-docs, Step 134).
 *
 * Only a choice of the list is taken - what is typed is a search, never a new choice. Text that is no longer the
 * title of the [selected] one takes the choice away - [onClear]; a choice picked puts its title in the field. Nothing
 * fits - "Nothing found" in the list. [error], when there is one, is shown under the field, the field outlined in the
 * colour of errors: what refuses a save without a choice says so here.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SearchableDropdown(
    selected: T?,
    options: List<DropdownOption<T>>,
    onSelect: (T) -> Unit,
    onClear: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    error: String? = null,
) {
    val selectedTitle = options.firstOrNull { it.value == selected }?.title
    var text by rememberSaveable { mutableStateOf(selectedTitle.orEmpty()) }
    var expanded by rememberSaveable { mutableStateOf(false) }
    val open = expanded && enabled

    // A choice made, or the options read after the field: the field shows its title. None - what is typed stays
    LaunchedEffect(selectedTitle) { selectedTitle?.let { text = it } }

    // With a choice in the field every option is shown - the list is opened to change it, not to search
    val query = if (selectedTitle != null) "" else text.trim()
    val shown = if (query.isEmpty()) options else options.filter { it.holds(query) }

    ExposedDropdownMenuBox(
        expanded = open,
        onExpandedChange = { if (enabled) expanded = it },
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                expanded = true
                if (selectedTitle != null && it != selectedTitle) onClear()
            },
            enabled = enabled,
            singleLine = true,
            label = { Text(label) },
            isError = error != null,
            supportingText = error?.let { { Text(it) } },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = open,
                    modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.SecondaryEditable, enabled),
                )
            },
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, enabled)
                .fillMaxWidth(),
        )
        val scroll = rememberScrollState()
        ExposedDropdownMenu(
            expanded = open,
            onDismissRequest = { expanded = false },
            // A long list scrolls: its edges fade where there is more of it
            modifier = Modifier.verticalFadingEdges(scroll),
            scrollState = scroll,
        ) {
            if (shown.isEmpty()) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(Res.string.dropdown_nothing_found),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    },
                    onClick = {},
                    enabled = false,
                    contentPadding = ITEM_PADDING,
                )
            }
            shown.forEachIndexed { index, option ->
                // A line between the choices, as in [Dropdown]
                if (index > 0) HorizontalDivider()
                DropdownMenuItem(
                    text = { OptionText(option) },
                    onClick = {
                        expanded = false
                        text = option.title
                        onSelect(option.value)
                    },
                    contentPadding = ITEM_PADDING,
                )
            }
        }
    }
}

/** The choice holds [query] in its title or its subtitle - the path, for a group - ignoring case. */
private fun DropdownOption<*>.holds(query: String): Boolean =
    title.contains(query, ignoreCase = true) || subtitle?.contains(query, ignoreCase = true) == true
