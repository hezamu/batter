import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.dom.append
import kotlinx.html.js.canvas
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import kotlin.math.max

object Surface {
    private val cnv: HTMLCanvasElement by lazy {
        console.log("Initing canvas")
        document.body!!.append { canvas { } }.first() as HTMLCanvasElement
    }

    private val ctx: CanvasRenderingContext2D by lazy {
        cnv.getContext("2d")!! as CanvasRenderingContext2D
    }

    fun width() = cnv.width

    fun height() = cnv.height

    fun resizeCanvas() {
        cnv.width = window.innerWidth
        cnv.height = window.innerHeight

        draw()
    }

    fun draw() {
        ctx.fillStyle = "white"
        ctx.fillRect(0.0, 0.0, cnv.width.toDouble(), cnv.height.toDouble())
        ctx.fillStyle = "black"
        ctx.fillRect(0.0, 15.toDouble(), cnv.width.toDouble(), cnv.height.toDouble() - 30)

        ctx.fillStyle = "white"
        drawCenterLine()

        drawScore(width() * 0.25, Players.score(Player.LEFT))
        drawScore(width() * 0.75, Players.score(Player.RIGHT))

        Players.draw()
        Ball.draw()
    }

    fun rect(r: Rect) = ctx.fillRect(r.x, r.y, r.w, r.h)

    private fun drawCenterLine() {
        val gap = (height() - 30) / 30
        for (i in 15..gap * 31 step 2 * gap) {
            ctx.fillRect(width() / 2 - 10.0, i.toDouble(), 20.0, gap.toDouble())
        }
    }

    private fun drawScore(x: Double, score: Int) {
        ctx.font = "200px VT323"
        ctx.fillText(score.toString(), x, max(50.0, height()*0.2))
    }
}