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
 * "Move to" of a group card (pass-docs, Step 134): the group goes with everything in it - the field offers every
 * group but itself and those below it, the one it is in chosen; "Move" waits for another and shows progress while
 * the group moves and the file is written. A group of the same name there is said under the field.
 */
@Composable
internal fun GroupMoveDialog(
    dialog: GroupDialog.MoveGroup,
    onGroup: (GroupId) -> Unit,
    onGroupCleared: () -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    ConfirmDialog(
        title = stringResource(Res.string.group_move_title),
        text = stringResource(Res.string.group_move_text, dialog.name),
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
            problem = if (dialog.nameTaken) stringResource(Res.string.group_name_taken) else null,
        )
    }
}
