package black.bracken.drainage.dsl.component

import black.bracken.drainage.dsl.InventoryUI
import black.bracken.drainage.util.InventoryInformation
import org.bukkit.Bukkit
import org.bukkit.entity.Player
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
    private val slotCondimentMap: MutableMap<Int, SlotCondiment> = mutableMapOf()
    private var defaultSlotCondiment: SlotCondiment = {}

    fun getSlotAt(slotIndex: Int): Slot {
        val slot = Slot().apply {
            slotCondimentMap
                    .getOrDefault(slotIndex, defaultSlotCondiment)
                    .invoke(this, slotIndex)
        }

        return if (slot.isAvailable()) {
            slot
        } else {
            Slot().apply { defaultSlotCondiment.invoke(this, slotIndex) }
        }
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
        positions.forEach { slotCondimentMap[it] = build }
    }

    fun put(positionRange: IntRange, build: SlotCondiment) {
        put(positions = *positionRange.toList().toIntArray(), build = build)
    }

    private fun createInventory(inventoryHolder: InventoryHolder, inventoryInformation: InventoryInformation, title: String?): Inventory {
        val existsTitle = title != null

        return if (inventoryInformation.size != null) {
            if (existsTitle) Bukkit.createInventory(inventoryHolder, inventoryInformation.size, title)
            else Bukkit.createInventory(inventoryHolder, inventoryInformation.size)
        } else {
            if (existsTitle) Bukkit.createInventory(inventoryHolder, inventoryInformation.type, title)
            else Bukkit.createInventory(inventoryHolder, inventoryInformation.type)
        }
    }

}