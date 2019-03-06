package black.bracken.drainage.dsl

import black.bracken.drainage.Drainage
import black.bracken.drainage.dsl.component.InventoryLayout
import black.bracken.drainage.util.InventoryInformation
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.Inventory
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
        Drainage.registerEvent(
                object : Listener {

                    @EventHandler
                    fun onClick(event: InventoryClickEvent) {
                        if ({ event.clickedInventory }.isThisUI()) {
                            event.isCancelled = true

                            layout.getSlotAt(event.slot).fire(event)
                        }
                    }

                    @EventHandler
                    fun onOpen(event: InventoryOpenEvent) {
                        if ({ event.inventory }.isThisUI()) {
                            layout.fire(event)
                        }
                    }

                    @EventHandler
                    fun onClose(event: InventoryCloseEvent) {
                        if ({ event.inventory }.isThisUI()) {
                            layout.fire(event)
                        }
                    }

                    private fun (() -> Inventory?).isThisUI(): Boolean {
                        val holderClass = invoke()?.run { holder::class.java } ?: return false
                        return holderClass == uiClass
                    }

                }
        )
    }

}