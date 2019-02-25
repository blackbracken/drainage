package black.bracken.drainage

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

/**
 * @author BlackBracken
 */
class InventoryLayout(val player: Player,
                      private val inventoryInformation: InventoryInformation) {

    private var title: String = ""

    private val slotMap: MutableMap<Int, Slot.() -> Unit> = mutableMapOf()

    private var defaultSlot: Slot.() -> Unit = { Slot() }

    fun toInventory(): Inventory {
        val inventory = if (inventoryInformation.size != null) {
            Bukkit.createInventory(player, inventoryInformation.size, title)
        } else {
            Bukkit.createInventory(player, inventoryInformation.type, title)
        }

        for (slotIndex in 0 until inventory.size) {
            val slot = Slot().apply(slotMap[slotIndex] ?: defaultSlot)
            val icon = Icon().apply(slot.icon)

            inventory.setItem(slotIndex, icon.toItemStack())
        }

        return inventory
    }

    fun title(build: () -> String) {
        title = build()
    }

    fun defaultSlot(build: Slot.() -> Unit) {
        defaultSlot = build
    }

    fun put(slotPosition: Int, build: Slot.() -> Unit) {
        slotMap[slotPosition] = build
    }

}