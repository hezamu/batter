import kotlin.math.*
import kotlin.random.Random

data class Vector(val x: Double, val y: Double)

object Ball {
    private const val w = 25.0
    private const val h = 25.0
    private lateinit var pos: Vector // (x, y) as a percentage of screen width and height
    private lateinit var speed: Vector // Rate of travel in percentage of screen width and height per frame

    init {
        reset()
    }

    fun draw() = Surface.rect(rect())

    private fun rect() = Rect(pos.x * Surface.width(), pos.y * Surface.height(), w, h)

    fun moveAndBounce() {
        val py = (pos.y + speed.y) * Surface.height()

        // Check if the ball hits the top or bottom of the screen
        if(py <= 15 || (py + h + 15) > Surface.height()) {
            speed = Vector(speed.x, speed.y * -1.0)
        }

        // Check if the ball hits either paddle while traveling towards the paddle
        if (rectIntersect(Players.paddleRect(Player.LEFT)) && speed.x < 0 ||
            rectIntersect(Players.paddleRect(Player.RIGHT)) && speed.x > 0) {
            speed = Vector(speed.x * (-1.0 - Random.nextDouble() * 0.2), speed.y) // Reverse and increase X speed
        }

        pos = Vector(max(0.0, min(1 - w / Surface.width(), pos.x + speed.x)), pos.y + speed.y)
    }

    private fun rectIntersect(r: Rect?) = r?.let { rect().intersect(it) } ?: false

    private fun isOut(x: Double): Boolean {
        val leftLimit = floor(Player.LEFT.paddleX()).toInt()
        val rightLimit = ceil(Player.RIGHT.paddleX() + Players.paddleWidth()).toInt()

        return if(ceil(x).toInt() < leftLimit) {
            Players.point(Player.RIGHT)
            true
        } else if(floor(x + w).toInt() > rightLimit) {
            Players.point(Player.LEFT)
            true
        } else false
    }

    fun isOut() = isOut(pos.x * Surface.width())

    private fun randomSpeed() = (Random.nextDouble(0.005) + 0.003) * if(Random.nextBoolean()) -1 else 1

    fun reset() {
        pos = Vector(0.5, 0.5)
        speed = Vector(randomSpeed(), randomSpeed())
    }
}