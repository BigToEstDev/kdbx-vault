package ru.kino.dev.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

/**
 * A field that takes one of [options], as [Dropdown] does, and lets the person type to find it: the list drops as
 * they type and keeps the choices whose title or subtitle holds the text, ignoring case (pass-docs, Step 134).
 *
 * Only a choice of the list is taken - what is typed is a search, never a new choice. Text that is no longer the
 * title of the [selected] one takes the choice away - [onClear]; a choice picked puts its title in the field. Nothing
 * fits - "Nothing found" in the list. [error], when there is one, is shown under the field, the field outlined in the
 * colour of errors: what refuses a save without a choice says so here.
 *
 * For a screen. In a dialog the list that drops is a window of its own that the keyboard pushes over the field -
 * there [SearchableList] keeps the list inside the dialog.
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
    val search = rememberOptionSearch(selected, options, onClear)
    var expanded by rememberSaveable { mutableStateOf(false) }
    val open = expanded && enabled

    ExposedDropdownMenuBox(
        expanded = open,
        onExpandedChange = { if (enabled) expanded = it },
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = search.text,
            onValueChange = {
                search.type(it)
                expanded = true
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
        val shown = search.shown
        ExposedDropdownMenu(
            expanded = open,
            onDismissRequest = { expanded = false },
            // A long list scrolls: its edges fade where there is more of it
            modifier = Modifier.verticalFadingEdges(scroll),
            scrollState = scroll,
        ) {
            if (shown.isEmpty()) {
                DropdownMenuItem(
                    text = { NothingFoundText() },
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
                        search.pick(option)
                        onSelect(option.value)
                    },
                    contentPadding = ITEM_PADDING,
                )
            }
        }
    }
}
