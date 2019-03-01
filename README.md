# Drainage
[![](https://img.shields.io/badge/language-Kotlin-orange.svg)](https://kotlinlang.org)
[![](https://jitpack.io/v/blackbracken/Drainage.svg)](https://jitpack.io/#blackbracken/Drainage)

*-- A Kotlin DSL to write inventory UI for Spigot 1.13.2+*

## 概要
Spigot上でインベントリを使ったUIを簡単に構築するためのライブラリとなるSpigotプラグインです.

## 例
![SampleUI](https://user-images.githubusercontent.com/18373318/53507569-7b384b80-3afb-11e9-81bb-a9831257fcbe.gif)

```kotlin
player.openInventory(SampleUI) // これでインベントリを開かせる お手軽!

// objectを作成しInventoryUIを実装する UIの実装はlayoutへbuildを使って記述する
object SampleUI : InventoryUI {

    // チェストタイプのUIを構築 標準は9x3
    // 大きさの値を入れることでサイズを指定したチェストタイプのUIも作成できる by build(27)...とこれは等価
    override val layout by build(InventoryType.CHEST) {

        // この階層のthisはInventoryUI型. (この階層含む)以下のブロックではplayerプロパティが使える

        title = "${ChatColor.DARK_BLUE}SampleUI" // インベントリのタイトル

        // デフォルトのスロット putによって埋められなかったスロットは標準でこのスロットになる
        defaultSlot {
            // 名前が1スペースの黒い板ガラス
            icon(Material.BLACK_STAINED_GLASS_PANE) {
                name = " "
            }

            // これはなくてもよい putと同じことを記述できるということを示すために書いた
            // UIの中のItemStackはクリックした際自動で全てイベントキャンセルされる
            onClick {}
        }

        // 8番目のスロットを, 閉じるという名前のバリアブロックの見た目にしてクリックするとUIを閉じるようにする
        put(8) {
            icon(Material.BARRIER) {
                name = "${ChatColor.RED}閉じる"

                // 説明文には文字リテラルを用いる
                // 改行することでItemStack側でも改行される
                lore {
                    """
                        ${ChatColor.GRAY}クリックすることで
                        ${ChatColor.GRAY}このウインドウを閉じます
                    """.trimIndent() // <- IDEAによって自動挿入される
                }
            }

            // クリックされたときの処理を書く
            // イベントの登録などの処理は全てDrainage側で行うのでここに記述するだけでOK
            onClick {

                // この階層のthisはInventoryClickEvent型.

                whoClicked.closeInventory() // player.closeInventory()でも良い(はず)
            }
        }

        // 23-25番目のスロットを少し耐久の減ったきらめく鉄の剣にする クリックするとテキストが届く
        // IntRangeも使えるので put(23..25) としても良い
        put(23, 24, 25) {
            // IconはItemStackをもとにして作ることもできる
            icon(ItemStack(Material.IRON_SWORD)) {
                damage = (Math.random() * 60.0).toInt()

                enchantments += Enchantment.ARROW_INFINITE to 1 // エンチャントを付与
                flags += ItemFlag.values() // 全てのステータスを隠す

                // rawブロックを使えば 生のItemStackを触ることもできる
                raw {

                    // この階層のthisはItemStack型.

                    itemMeta = itemMeta.apply {
                        displayName = "${ChatColor.RED}rawブロックで名前を書き換えた"
                    }
                }
            }

            onClick {
                player.sendMessage("${slot}番目のスロットにある剣をクリックした")
            }
        }
    }

}
```

## 導入

1. [Releases](https://github.com/blackbracken/Drainage/releases)から**for-serverの接尾の付いた**jarをダウンロードし, Spigotサーバのplugins下に配置する
2. 利用するプラグインのGradle/Mavenで依存関係に追加する 詳しくは下記に
3. 利用するプラグインのplugin.ymlのdependsに`Drainage`を加える
4. Drainageを楽しむ

### Gradle
```
repositories {
    // ...
    maven { url 'https://jitpack.io' }
}

dependencies {
    // ...
    compileOnly 'com.github.blackbracken:Drainage:<Drainageのバージョン>'
}
```

### Maven
```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.blackbracken</groupId>
    <artifactId>Drainage</artifactId>
    <version>Drainageのバージョン</version>
</dependency>
```

## ライセンス
* MIT License