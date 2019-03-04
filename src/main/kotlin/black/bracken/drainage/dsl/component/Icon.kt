package black.bracken.drainage.dsl.component

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable

/**
 * @author BlackBracken
 */
class Icon {

    var basedItemStack: ItemStack? = null
    var material: Material = Material.AIR
    var damage: Int = 0
    var amount: Int = 1
    var name: String? = null
    var lore: MutableList<String> = mutableListOf()
        private set
    val enchantments: MutableMap<Enchantment, Int> = mutableMapOf()
    val flags: MutableSet<ItemFlag> = mutableSetOf()
    private var raw: ItemStack.() -> Unit = {}

    fun toItemStack(): ItemStack {
        val itemStack = basedItemStack?.clone() ?: ItemStack(material)

        itemStack.amount = amount
        itemStack.itemMeta = itemStack.itemMeta
                ?.clone()
                ?.also { meta ->
                    meta.displayName = name
                    meta.lore = lore

                    for ((enchantment, level) in enchantments) meta.addEnchant(enchantment, level, true)
                    meta.addItemFlags(*flags.toTypedArray())

                    (meta as? Damageable)?.damage = damage
                }

        return itemStack.apply(raw)
    }

    fun lore(literal: () -> String) {
        lore = literal()
                .trimIndent()
                .split("\n", "\r") // didn't work by System.lineSeparator()
                .toMutableList()
    }

    fun raw(apply: ItemStack.() -> Unit) {
        raw = apply
    }

}