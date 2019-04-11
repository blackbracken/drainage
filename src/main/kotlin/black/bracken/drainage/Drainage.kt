package black.bracken.drainage

import black.bracken.drainage.listener.UIListener
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author BlackBracken
 */
class Drainage : JavaPlugin() {

    override fun onEnable() {
        server.pluginManager.registerEvents(UIListener(), this)
    }


}