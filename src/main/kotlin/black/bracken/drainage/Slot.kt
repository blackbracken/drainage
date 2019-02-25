package black.bracken.drainage

import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent

/**
 * @author BlackBracken
 */
class Slot(iconMaterial: Material = Material.AIR) {

    var icon: Icon = Icon(iconMaterial)
        private set

    var actionOnClick: InventoryClickEvent.() -> Unit = {}
        private set

    fun icon(build: Icon.() -> Unit) {
        icon.apply(build)
    }

    fun icon(iconMaterial: Material, build: Icon.() -> Unit) {
        icon { material { iconMaterial } }
        icon(build)
    }

    fun onClick(action: InventoryClickEvent.() -> Unit) {

    }

}