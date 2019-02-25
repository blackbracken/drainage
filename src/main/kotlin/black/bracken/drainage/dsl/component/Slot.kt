package black.bracken.drainage.dsl.component

import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent

/**
 * @author BlackBracken
 */
class Slot(iconMaterial: Material = Material.AIR) {

    var icon: Icon.() -> Unit = { }
        private set

    var actionOnClick: InventoryClickEvent.() -> Unit = { }
        private set

    fun icon(build: Icon.() -> Unit) {
        icon = build
    }

    fun icon(iconMaterial: Material, build: Icon.() -> Unit = {}) {
        icon = {
            material = iconMaterial
            build()
        }
    }

    fun onClick(action: InventoryClickEvent.() -> Unit) {
        actionOnClick = action
    }

}