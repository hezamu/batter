import kotlinx.browser.document

object Controls {
    private val keysDown = mutableSetOf<String>()

    init {
        document.onkeydown = { e ->
            keysDown.add(e.key)
            e.preventDefault()
        }

        document.onkeyup = { e ->
            keysDown.remove(e.key)
            e.preventDefault()
        }
    }

    fun handleKeys() = keysDown.forEach(::handleKey)

    private fun handleKey(key: String) = when(key) {
        "w" -> Players.movePlayer(Player.LEFT, true)
        "s" -> Players.movePlayer(Player.LEFT, false)
        "ArrowUp" -> Players.movePlayer(Player.RIGHT, true)
        "ArrowDown" -> Players.movePlayer(Player.RIGHT, false)
        else -> { } // NOP
    }
}