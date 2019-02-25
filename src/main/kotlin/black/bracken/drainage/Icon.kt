package black.bracken.drainage

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

    private var material: Material = Material.AIR
    private var damage: Int = 0
    private var amount: Int = 1
    private var name: String? = null
    private var lore: List<String>? = null
    private var enchantmentMap: Map<Enchantment, Int> = mapOf()
    private var flagSet: Set<ItemFlag> = setOf()
    private var editRawItemStack: ItemStack.() -> Unit = {}

    fun toItemStack(): ItemStack {
        val itemStack = ItemStack(material)
        val additionalMeta = Bukkit.getItemFactory().getItemMeta(material) ?: return itemStack

        (additionalMeta as? Damageable)?.damage = damage
        if (name != null) additionalMeta.displayName = name
        if (lore != null) additionalMeta.lore = lore
        for ((enchantment, level) in enchantmentMap) additionalMeta.addEnchant(enchantment, level, true)
        additionalMeta.addItemFlags(*flagSet.toTypedArray())

        itemStack.itemMeta = additionalMeta
        itemStack.amount = amount

        return itemStack.apply(editRawItemStack)
    }

    fun material(build: () -> Material) {
        material = build()
    }

    fun damage(build: () -> Int) {
        damage = build()
    }

    fun amount(build: () -> Int) {
        amount = build()
    }

    fun name(build: () -> String) {
        name = build()
    }

    fun lore(build: () -> String) {
        lore = build()
                .trimIndent()
                .split("\n", "\r") // didn't work by System.lineSeparator()
                .toList()
    }

    fun enchantment(build: () -> Map<Enchantment, Int>) {
        enchantmentMap = build()
    }

    fun flag(build: () -> Set<ItemFlag>) {
        flagSet = build()
    }

    fun raw(apply: ItemStack.() -> Unit) {
        editRawItemStack = apply
    }

}