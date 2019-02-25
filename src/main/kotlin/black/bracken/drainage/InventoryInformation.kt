package black.bracken.drainage

import org.bukkit.event.inventory.InventoryType

/**
 * @author BlackBracken
 */
data class InventoryInformation(val type: InventoryType,
                                val size: Int? = null)