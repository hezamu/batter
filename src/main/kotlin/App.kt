import kotlinx.browser.window

fun main() {
    window.addEventListener("resize", { Surface.resizeCanvas() } )

    Surface.resizeCanvas() // Canvas etc. will be initialized here

    // Main loop
    window.setInterval({
        Controls.handleKeys()
        Ball.moveAndBounce()
        Surface.draw()

        if(Ball.isOut()) Ball.reset()
    }, 16) // 60 fps
}
