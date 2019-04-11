package black.bracken.drainage.dsl

import black.bracken.drainage.dsl.component.InventoryLayout
import black.bracken.drainage.util.InventoryInformation
import org.bukkit.entity.Player
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * @author BlackBracken
 */
class LayoutBuilder internal constructor(private val inventoryInformation: InventoryInformation,
                                         private val uiInstance: InventoryUI,
                                         private val build: InventoryLayout.() -> Unit
) : ReadOnlyProperty<Any, (Player) -> InventoryLayout> {

    private val layoutBuilder by lazy<(Player) -> InventoryLayout> {
        return@lazy { player -> InventoryLayout(player, uiInstance, inventoryInformation).apply(build) }
    }

    override fun getValue(thisRef: Any, property: KProperty<*>) = layoutBuilder

}