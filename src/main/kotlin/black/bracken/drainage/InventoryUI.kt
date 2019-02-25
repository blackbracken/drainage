package black.bracken.drainage

import org.bukkit.entity.Player

/**
 * @author BlackBracken
 */
abstract class InventoryUI {

    abstract val layout: (Player) -> InventoryLayout

    fun InventoryUI.build(inventoryInformation: InventoryInformation, build: InventoryLayout.() -> Unit)
            : LayoutBuilder {
        return LayoutBuilder(inventoryInformation, build)
    }

}