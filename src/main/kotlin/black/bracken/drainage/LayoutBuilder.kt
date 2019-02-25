package black.bracken.drainage

import org.bukkit.entity.Player
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * @author BlackBracken
 */
class LayoutBuilder internal constructor(private val inventoryInformation: InventoryInformation,
                                         private val build: InventoryLayout.() -> Unit
) : ReadOnlyProperty<Any, (Player) -> InventoryLayout> {

    override fun getValue(thisRef: Any, property: KProperty<*>): (Player) -> InventoryLayout = { player ->
        InventoryLayout(player, inventoryInformation).apply(build)
    }

}