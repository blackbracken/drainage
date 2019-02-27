# Drainage
[![](https://img.shields.io/badge/language-Kotlin-orange.svg)](https://kotlinlang.org)

*-- A Kotlin DSL to write inventory UI for Spigot 1.13.2+*

## 概要
Spigot上でインベントリを使ったUIを簡単に構築するためのライブラリとなるSpigotプラグインです.

## 例
```kotlin
// OPEN: player.openInventory(ExampleUI.layout(player).toInventory())

// objectを作成しInventoryUIを実装する UIの実装はlayoutへ以下のように記述する
object ExampleUI : InventoryUI {

    // チェストタイプのUIを構築 標準は9x3
    override val layout by build(InventoryInformation(InventoryType.CHEST)) {
        title = "TestUI" // インベントリのタイトル

        // デフォルトのスロット putによって埋められなかったスロットは標準でこのスロットになる
        defaultSlot {
            icon(Material.AIR) {}
            
            onClick {} // この行はあってもなくても同じ putと同じように構築できるということを例として示しているだけ
        }

        // 指定したスロットにアイテムやリスナを埋める これは3番目(最上列の左から4番目)
        put(3) {
            icon(Material.IRON_AXE) { // このスロットのアイコンの設定
                damage = 10
                amount = 3
                name = "${ChatColor.YELLOW}${player.name}の鉄の斧のアイコン"
                lore { // 説明文は文字列リテラルで記述する 改行も判別する
                    """
                        ${ChatColor.RED}赤文字
                        ${ChatColor.AQUA}青文字
                    """.trimIndent() // IDEAで自動補完される
                }

                // エンチャントやItemFlagも自在
                enchantments += Enchantment.ARROW_INFINITE to 1
                flags += ItemFlag.values()

                // どうしても生のItemStackが触りたければこのブロックの中で触れる
                raw {
                    val copiedMeta = itemMeta
                    copiedMeta.displayName = "${ChatColor.YELLOW}名前を書き換えた!"
                    itemMeta = copiedMeta
                }
            }

            // このアイコンをクリックしたときのリスナ
            // イベント周りの処理は全て自動で行われるのでここに書くだけでOK
            onClick {
                whoClicked.closeInventory()
            }
        }
    }

}
```

## ライセンス
* MIT License