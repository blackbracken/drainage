package black.bracken.drainage

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

/**
 * @author BlackBracken
 */
class Icon(iconMaterial: Material) {

    var material = iconMaterial
        private set

    var damage = 0
        private set

    var amount = 1
        private set

    var name = ""
        private set

    var lore: List<String> = listOf()
        private set

    var enchantmentMap: Map<Enchantment, Int> = mapOf()
        private set

    var flagSet: Set<ItemFlag> = setOf()
        private set

    var editRawItemStack: ItemStack.() -> Unit = {}
        private set

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
                .split(System.lineSeparator())
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