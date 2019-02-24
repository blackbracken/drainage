package black.bracken.drainage

import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

/**
 * @author BlackBracken
 */
abstract class InventoryUI : InventoryHolder {

    abstract val layout: InventoryLayout

    override fun getInventory(): Inventory = _inventory

    private lateinit var _inventory: Inventory

    // add argument of InventoryUI if _inventory isn't its subtype.
    fun InventoryUI.build(inventoryType: InventoryType, build: InventoryLayout.() -> Unit): LayoutBuilder {
        _inventory = Bukkit.createInventory(this@InventoryUI, inventoryType)

        return LayoutBuilder(build)
    }

}