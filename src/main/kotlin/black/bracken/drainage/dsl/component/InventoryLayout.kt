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
    private val slotMap: MutableMap<Int, SlotCondiment> = mutableMapOf()
    private var defaultSlot: SlotCondiment = { Slot() }

    operator fun get(slotIndex: Int): SlotCondiment {
        return slotMap[slotIndex] ?: defaultSlot
    }

    fun toInventory(): Inventory {
        val inventory = createInventory(uiInstance, inventoryInformation, title)

        for (slotIndex in 0 until inventory.size) {
            val slot = Slot().apply(this[slotIndex], slotIndex)
                    .takeIf { it.filter() } ?: Slot().apply(defaultSlot, slotIndex)
            val slotItem = Icon().apply(slot.iconCondiment).toItemStack()

            inventory.setItem(slotIndex, slotItem)
        }

        return inventory
    }

    fun defaultSlot(build: SlotCondiment) {
        defaultSlot = build
    }

    fun put(vararg positions: Int, build: SlotCondiment) {
        positions.forEach { slotMap[it] = build }
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

    private fun Slot.apply(build: Slot.(Int) -> Unit, index: Int): Slot {
        build.invoke(this, index)
        return this
    }

}