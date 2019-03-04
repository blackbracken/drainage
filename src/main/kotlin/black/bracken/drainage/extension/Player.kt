package black.bracken.drainage.extension

import black.bracken.drainage.dsl.InventoryUI
import org.bukkit.entity.Player

/**
 * @author BlackBracken
 */

fun Player.openInventory(ui: InventoryUI) {
    this.openInventory(ui.layout(this).buildInventory())
}