import Players.paddleWidth
import kotlin.math.max
import kotlin.math.min

enum class Player {
    LEFT { override fun paddleX() = 20.0 },
    RIGHT { override fun paddleX() = Surface.width() - 20 - paddleWidth() };

    abstract fun paddleX(): Double
}

data class Rect(val x: Double, val y: Double, val w: Double, val h: Double) {
    private val right = x + w
    private val bottom = y + h

    fun intersect(other: Rect) = !(other.x > right || other.right < x || other.y > bottom || other.bottom < y)
}

object Players {
    private const val moveRate = 1.2

    private val playerState: MutableMap<Player, Pair<Double, Int>> = mutableMapOf(
        Pair(Player.LEFT, Pair(50.0, 0)),
        Pair(Player.RIGHT, Pair(50.0, 0))
    )

    fun movePlayer(player: Player, up: Boolean) = playerState[player]?.let { (y, s) ->
        playerState[player] = Pair(max(5.0, min(95.0,y + moveRate * if(up) -1 else 1)), s)
    }

    fun draw() = Player.values().forEach(::drawPlayer)

    private fun drawPlayer(player: Player) = paddleRect(player)?.let(Surface::rect)

    fun paddleWidth() = min(35.0, max(15.0, Surface.width() * 0.02))

    fun paddleRect(player: Player) = playerState[player]?.let { (y, _) ->
        val px = player.paddleX()
        val py = Surface.height() * y / 100.0
        val ph = Surface.height() * 0.07

        Rect(px, py - ph / 2.0, paddleWidth(), ph)
    }

    fun score(player: Player) = playerState[player]!!.second

    fun point(player: Player) = playerState[player]?.let { (y, s) ->
        playerState[player] = Pair(y, s + 1)
    }
}