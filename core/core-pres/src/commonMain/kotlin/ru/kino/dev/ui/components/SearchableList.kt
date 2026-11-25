package ru.kino.dev.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A field to type into and, right under it, the list it searches - inside the layout, not a window that drops
 * (pass-docs, Step 134): for a dialog, where the keyboard pushes a dropped list over the field. It takes one of
 * [options] as [SearchableDropdown] does - the same search, the same [onClear] and [error]; the one [selected] is
 * marked in the list. The list is at most [maxListHeight] tall and scrolls, its edges fading where there is more.
 */
@Composable
fun <T> SearchableList(
    selected: T?,
    options: List<DropdownOption<T>>,
    onSelect: (T) -> Unit,
    onClear: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    error: String? = null,
    maxListHeight: Dp = LIST_MAX_HEIGHT,
) {
    val search = rememberOptionSearch(selected, options, onClear)
    val shown = search.shown

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = search.text,
            onValueChange = { search.type(it) },
            enabled = enabled,
            singleLine = true,
            label = { Text(label) },
            isError = error != null,
            supportingText = error?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth(),
        )
        val scroll = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = maxListHeight)
                .clip(LIST_SHAPE)
                .background(MaterialTheme.colorScheme.surfaceContainerHighest)
                .verticalFadingEdges(scroll)
                .verticalScroll(scroll),
        ) {
            if (shown.isEmpty()) {
                Box(Modifier.padding(ITEM_PADDING)) { NothingFoundText() }
            }
            shown.forEachIndexed { index, option ->
                // A line between the choices, as in [Dropdown]
                if (index > 0) HorizontalDivider()
                // The one chosen stands out, as a picked card does
                val colors = MaterialTheme.colorScheme
                val background = if (option.value == selected) colors.secondaryContainer else colors.surfaceContainerHighest
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(background)
                        .clickable(enabled = enabled) {
                            search.pick(option)
                            onSelect(option.value)
                        }
                        .padding(ITEM_PADDING),
                ) {
                    OptionText(option)
                }
            }
        }
    }
}

/** About four two-line choices: the field, the list and the buttons fit a dialog over the keyboard. */
private val LIST_MAX_HEIGHT = 240.dp

private val LIST_SHAPE = RoundedCornerShape(12.dp)
