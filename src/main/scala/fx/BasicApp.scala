package fx

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle

object BasicApp extends JFXApp {
  stage = new JFXApp.PrimaryStage {
    title.value = "Basic App"
    width = 200
    height = 200
    scene = new Scene {
      fill = Color.Grey
      content = new Rectangle {
        x = 50
        y = 40
        width = 100
        height = 100
        fill <== when (hover) choose Color.Purple otherwise Color.Yellow
      }
    }
  }
}