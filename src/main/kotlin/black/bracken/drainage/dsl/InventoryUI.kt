package black.bracken.drainage.dsl

import black.bracken.drainage.dsl.component.InventoryLayout
import black.bracken.drainage.util.InventoryInformation
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