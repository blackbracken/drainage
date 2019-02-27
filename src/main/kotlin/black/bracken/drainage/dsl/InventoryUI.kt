package black.bracken.drainage.dsl

import black.bracken.drainage.dsl.component.InventoryLayout
import black.bracken.drainage.util.InventoryInformation
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

/**
 * @author BlackBracken
 */
interface InventoryUI : InventoryHolder {

    val layout: (Player) -> InventoryLayout

    @Deprecated(
            message = "Use InventoryUI#layout(Player).toInventory() instead.",
            level = DeprecationLevel.WARNING,
            replaceWith = ReplaceWith("layout().toInventory()"))
    override fun getInventory(): Inventory? = null

    fun InventoryUI.build(inventoryInformation: InventoryInformation, build: InventoryLayout.() -> Unit): LayoutBuilder {
        return LayoutBuilder(inventoryInformation, this@build, build)
    }

}