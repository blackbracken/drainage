package black.bracken.drainage

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * @author BlackBracken
 */
class LayoutBuilder internal constructor(private val build: InventoryLayout.() -> Unit)
    : ReadOnlyProperty<Any, InventoryLayout> {

    override fun getValue(thisRef: Any, property: KProperty<*>): InventoryLayout {
        return InventoryLayout().also(build)
    }

}