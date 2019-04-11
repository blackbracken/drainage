package black.bracken.drainage.listener

import black.bracken.drainage.dsl.InventoryUI
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

/**
 * @author BlackBracken
 */
class UIListener : Listener {

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        val inventoryUI = event.clickedInventory?.holder as? InventoryUI ?: return
        val layout = inventoryUI.layout(event.whoClicked as? Player ?: return)

        event.isCancelled = true
        layout.getSlotAt(event.slot).fire(event)
    }

    @EventHandler
    fun onOpen(event: InventoryOpenEvent) {
        val inventoryUI = event.inventory.holder as? InventoryUI ?: return
        val layout = inventoryUI.layout(event.player as? Player ?: return)

        layout.fire(event)
    }

    @EventHandler
    fun onClose(event: InventoryCloseEvent) {
        val inventoryUI = event.inventory.holder as? InventoryUI ?: return
        val layout = inventoryUI.layout(event.player as? Player ?: return)

        layout.fire(event)
    }

}