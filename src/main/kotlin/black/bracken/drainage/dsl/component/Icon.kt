package black.bracken.drainage.dsl.component

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable

/**
 * @author BlackBracken
 */
class Icon {

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
        val itemStack = ItemStack(material)
        val additionalMeta = Bukkit.getItemFactory().getItemMeta(material) ?: return itemStack

        (additionalMeta as? Damageable)?.damage = damage
        if (name != null) additionalMeta.displayName = name
        if (lore.isNotEmpty()) additionalMeta.lore = lore
        for ((enchantment, level) in enchantments) additionalMeta.addEnchant(enchantment, level, true)
        additionalMeta.addItemFlags(*flags.toTypedArray())

        itemStack.itemMeta = additionalMeta
        itemStack.amount = amount

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