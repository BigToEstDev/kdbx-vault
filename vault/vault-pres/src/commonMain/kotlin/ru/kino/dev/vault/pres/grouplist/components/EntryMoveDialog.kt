package ru.kino.dev.vault.pres.grouplist.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.kino.dev.core.pres.*
import ru.kino.dev.ui.components.ConfirmDialog
import ru.kino.dev.ui.res.stringResource
import ru.kino.dev.vault.group.GroupId
import ru.kino.dev.vault.pres.common.GroupField
import ru.kino.dev.vault.pres.grouplist.GroupDialog

/**
 * "Move to" of an entry card (pass-docs, Step 134): the field of the group, the one the entry is in chosen; "Move"
 * waits for another and shows progress while the entry moves and the file is written.
 */
@Composable
internal fun EntryMoveDialog(
    dialog: GroupDialog.MoveEntry,
    onGroup: (GroupId) -> Unit,
    onGroupCleared: () -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    ConfirmDialog(
        title = stringResource(Res.string.entry_move_title),
        text = stringResource(
            Res.string.entry_move_text,
            dialog.title.ifEmpty { stringResource(Res.string.entry_untitled) },
        ),
        confirmText = stringResource(Res.string.action_move),
        dismissText = stringResource(Res.string.action_cancel),
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        loading = dialog.saving,
        confirmEnabled = dialog.canMove,
    ) {
        GroupField(
            selected = dialog.pick.group,
            groups = dialog.pick.groups,
            missing = dialog.pick.missing,
            onSelect = onGroup,
            onClear = onGroupCleared,
            modifier = Modifier.fillMaxWidth(),
            enabled = !dialog.saving,
            inDialog = true,
        )
    }
}
