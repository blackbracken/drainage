package black.bracken.drainage.dsl

import black.bracken.drainage.Drainage
import black.bracken.drainage.dsl.component.InventoryLayout
import black.bracken.drainage.dsl.component.Slot
import black.bracken.drainage.util.InventoryInformation
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * @author BlackBracken
 */
class LayoutBuilder internal constructor(private val inventoryInformation: InventoryInformation,
                                         private val uiInstance: InventoryUI,
                                         private val build: InventoryLayout.() -> Unit
) : ReadOnlyProperty<Any, (Player) -> InventoryLayout> {

    private var isInitialized = false

    override fun getValue(thisRef: Any, property: KProperty<*>): (Player) -> InventoryLayout = { player ->
        InventoryLayout(player, uiInstance, inventoryInformation)
                .apply(build)
                .apply(::initializeIfNeeded)
    }

    private fun initializeIfNeeded(layout: InventoryLayout) {
        if (isInitialized) return
        isInitialized = true

        val uiClass = uiInstance::class.java
        val listener: Listener = object : Listener {
            @EventHandler
            fun onClick(event: InventoryClickEvent) {
                val holderClass = event.clickedInventory?.run { holder::class.java } ?: return
                if (holderClass != uiClass) return

                event.isCancelled = true

                val slot = Slot().apply(layout[event.slot])
                slot.actionOnClick(event)
            }
        }

        Drainage.registerEvent(listener)
    }

}