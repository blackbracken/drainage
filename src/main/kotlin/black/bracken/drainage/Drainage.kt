package black.bracken.drainage

import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author BlackBracken
 */
class Drainage : JavaPlugin() {

    companion object {
        private lateinit var instance: Drainage

        internal fun registerEvent(listener: Listener) {
            instance.server.pluginManager.registerEvents(listener, instance)
        }
    }

    override fun onEnable() {
        super.onEnable()
        instance = this
    }

}