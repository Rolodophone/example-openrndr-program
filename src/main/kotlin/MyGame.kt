import ddf.minim.Minim
import org.openrndr.Fullscreen
import org.openrndr.KEY_ESCAPE
import org.openrndr.application
import org.openrndr.draw.loadFont
import org.openrndr.draw.loadImage
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

fun main() = application {
    configure {
        fullscreen = Fullscreen.CURRENT_DISPLAY_MODE
    }

    program {
        val font = loadFont("data/fonts/default.otf", 64.0)
        drawer.fontMap = font


        @Suppress("unused")
        val minim = Minim(object {
            fun sketchPath(fileName: String) = fileName
            fun createInput(fileName: String) = FileInputStream(File(fileName)) as InputStream
        })

        val musicPlayer = minim.loadFile("data/audio/hidden_wonders.mp3")

        musicPlayer.play()


        var playerX = width/2.0
        var playerY = height/2.0
        val playerW = 50.0
        val playerH = 100.0


        val playerImg = loadImage("data/images/car1.png")


        extend {
            drawer.image(playerImg, playerX, playerY, playerW, playerH)

            if ("w" in keyboard.pressedKeys) playerY -= 5
            if ("a" in keyboard.pressedKeys) playerX -= 5
            if ("s" in keyboard.pressedKeys) playerY += 5
            if ("d" in keyboard.pressedKeys) playerX += 5
        }


        keyboard.keyUp.listen {
            if (it.key == KEY_ESCAPE) application.exit()
        }


        ended.listen {
            musicPlayer.close()
            minim.stop()
        }
    }
}
