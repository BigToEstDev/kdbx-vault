package ru.kino.dev.vault.pres.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.kino.dev.core.pres.*
import ru.kino.dev.ui.components.SearchableDropdown
import ru.kino.dev.ui.components.SearchableList
import ru.kino.dev.ui.res.stringResource
import ru.kino.dev.vault.group.GroupId

/**
 * The field of a group to put something into - an entry on its edit screen, the entries restored from the bin, an
 * entry or a group moved (pass-docs, Step 134): typed to find the group, by its name or path; only a group of
 * [groups] is taken. [missing] - a save was asked with none chosen: "No group chosen" under the field.
 *
 * [inDialog] - the list of groups right under the field, inside the dialog ([SearchableList]): a list that drops is a
 * window the keyboard pushes over the field there. On a screen - the list drops ([SearchableDropdown]).
 */
@Composable
internal fun GroupField(
    selected: GroupId?,
    groups: List<GroupChoice>,
    missing: Boolean,
    onSelect: (GroupId) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    inDialog: Boolean = false,
) {
    val options = groups.map { it.option() }
    val label = stringResource(Res.string.entry_field_group)
    val error = if (missing) stringResource(Res.string.vault_group_missing) else null
    if (inDialog) {
        SearchableList(selected, options, onSelect, onClear, label, modifier, enabled, error)
    } else {
        SearchableDropdown(selected, options, onSelect, onClear, label, modifier, enabled, error)
    }
}
