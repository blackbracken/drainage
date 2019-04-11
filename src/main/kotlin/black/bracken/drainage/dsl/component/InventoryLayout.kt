package black.bracken.drainage.dsl.component

import black.bracken.drainage.dsl.InventoryUI
import black.bracken.drainage.util.InventoryInformation
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

/**
 * @author BlackBracken
 */

private typealias SlotCondiment = Slot.(Int) -> Unit

class InventoryLayout(val player: Player,
                      private val uiInstance: InventoryUI,
                      private val inventoryInformation: InventoryInformation) {

    var title: String? = null
    private val slotMap: MutableMap<Int, Slot> = mutableMapOf()

    private var defaultSlotCondiment: SlotCondiment = {}
    private var actionOnOpen: InventoryOpenEvent.() -> Unit = {}
    private var actionOnClose: InventoryCloseEvent.() -> Unit = {}

    fun getSlotAt(slotIndex: Int): Slot {
        return slotMap.getOrDefault(slotIndex, Slot().apply { defaultSlotCondiment(slotIndex) })
    }

    fun buildInventory(): Inventory {
        val inventory = createInventory(uiInstance, inventoryInformation, title)

        for (slotIndex in 0 until inventory.size) {
            inventory.setItem(
                    slotIndex,
                    getSlotAt(slotIndex).buildIcon().toItemStack()
            )
        }

        return inventory
    }

    fun defaultSlot(build: SlotCondiment) {
        defaultSlotCondiment = build
    }

    fun put(vararg positions: Int, build: SlotCondiment) {
        positions
                .map { position -> position to Slot().apply { build(position) } }
                .filter { (_, slot) -> slot.isAvailable() }
                .forEach { (position, slot) -> slotMap[position] = slot }
    }

    fun put(positionRange: IntRange, build: SlotCondiment) {
        put(positions = *positionRange.toList().toIntArray(), build = build)
    }

    fun onOpen(action: InventoryOpenEvent.() -> Unit) {
        actionOnOpen = action
    }

    fun onClose(action: InventoryCloseEvent.() -> Unit) {
        actionOnClose = action
    }

    internal fun fire(event: InventoryOpenEvent) {
        actionOnOpen(event)
    }

    internal fun fire(event: InventoryCloseEvent) {
        actionOnClose(event)
    }

    private fun createInventory(inventoryHolder: InventoryHolder, inventoryInformation: InventoryInformation, title: String?): Inventory {
        return if (inventoryInformation.size != null) {
            if (title != null) {
                Bukkit.createInventory(inventoryHolder, inventoryInformation.size, title)
            } else {
                Bukkit.createInventory(inventoryHolder, inventoryInformation.size)
            }
        } else {
            if (title != null) {
                Bukkit.createInventory(inventoryHolder, inventoryInformation.type, title)
            } else {
                Bukkit.createInventory(inventoryHolder, inventoryInformation.type)
            }
        }
    }

}