package black.bracken.drainage

/**
 * @author BlackBracken
 */
class InventoryLayout internal constructor() {

    var title: String = ""
        private set

    var defaultSlot: Slot = Slot()
        private set

    var slotMap: Map<Int, Slot> = mapOf()
        private set

    fun title(get: () -> String) {
        title = get()
    }

    fun defaultSlot(build: Slot.() -> Unit) {
        defaultSlot.apply(build)
    }

    fun put(slot: Int, build: Slot.() -> Unit) {
        slotMap = slotMap.plus(slot to Slot().apply(build))
    }

}