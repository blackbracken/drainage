package black.bracken.drainage

/**
 * @author BlackBracken
 */
class InventoryLayout internal constructor() {

    private var title: String = ""

    fun title(value: String) = title { value }

    fun title(get: () -> String) {
        title = get()
    }

}