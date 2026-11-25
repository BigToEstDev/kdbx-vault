package ru.kino.dev.vault.pres.common

import ru.kino.dev.vault.group.GroupId

/**
 * The group field of a "Move to" dialog as its state holds it (pass-docs, Step 134): what can be chosen, what is,
 * and whether a move was asked with none.
 *
 * @param groups the groups to choose from, in the order of the tree
 * @param group the one chosen; null - the text typed in the field fits no group
 * @param missing a move was asked with none chosen - "No group chosen" under the field, until one is picked
 */
data class GroupPick(
    val groups: List<GroupChoice>,
    val group: GroupId?,
    val missing: Boolean = false,
) {
    /** [id] picked, the warning gone. */
    fun chosen(id: GroupId): GroupPick = copy(group = id, missing = false)

    /** The text typed fits no group: none chosen. */
    fun cleared(): GroupPick = copy(group = null)

    /** A move asked with none chosen: the warning under the field. */
    fun refused(): GroupPick = copy(missing = true)
}
