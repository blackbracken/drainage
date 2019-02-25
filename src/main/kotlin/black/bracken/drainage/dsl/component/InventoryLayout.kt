package black.bracken.drainage.dsl.component

import black.bracken.drainage.util.InventoryInformation
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

/**
 * @author BlackBracken
 */
class InventoryLayout(val player: Player,
                      private val inventoryInformation: InventoryInformation) {

    var title: String? = null
    private val slotMap: MutableMap<Int, Slot.() -> Unit> = mutableMapOf()
    private var defaultSlot: Slot.() -> Unit = { Slot() }

    fun toInventory(): Inventory {
        val inventory = createInventory(player, inventoryInformation, title)

        for (slotIndex in 0 until inventory.size) {
            val slot = Slot().apply(slotMap[slotIndex] ?: defaultSlot)
            val icon = Icon().apply(slot.icon)

            inventory.setItem(slotIndex, icon.toItemStack())
        }

        return inventory
    }

    fun defaultSlot(build: Slot.() -> Unit) {
        defaultSlot = build
    }

    fun put(slotPosition: Int, build: Slot.() -> Unit) {
        slotMap[slotPosition] = build
    }

    private fun createInventory(player: Player, inventoryInformation: InventoryInformation, title: String?): Inventory {
        val existsTitle = title != null

        return if (inventoryInformation.size != null) {
            if (existsTitle) Bukkit.createInventory(player, inventoryInformation.size, title)
            else Bukkit.createInventory(player, inventoryInformation.size)
        } else {
            if (existsTitle) Bukkit.createInventory(player, inventoryInformation.type, title)
            else Bukkit.createInventory(player, inventoryInformation.type)
        }
    }

}