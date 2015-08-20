package fx

import javafx.{concurrent => jfxc}

import scala.concurrent.ExecutionContext
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.concurrent.Task
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout.VBox

object JokeTask extends Task(new jfxc.Task[String] {
  override def call(): String = {
    new AsyncRest().joke // AsyncRest fails on second request! It's a net.databinder.dispatch mystery.:)
  }
})

object RestApp extends JFXApp {
  private val ec = ExecutionContext.global

  val jokeLabel = new Label {
    text = "Joke:"
  }

  val jokeText = new TextArea {
    wrapText = true
    text <== JokeTask.value
  }

  val indicator = new ProgressIndicator {
    prefWidth = 50
    progress = -1.0
    visible <== JokeTask.running
  }

  val jokeButton = new Button {
    text = "New Joke"
    disable <== JokeTask.running
    onAction = (e: ActionEvent) => { ec.execute(JokeTask) }
  }

  val jokePane = new VBox {
    maxWidth = 400
    maxHeight = 400
    spacing = 6
    padding = Insets(6)
    children = List(jokeLabel, jokeText)
  }

  val toolbar = new ToolBar {
    content = List(jokeButton, new Separator(), indicator)
  }

  val appPane = new VBox {
    maxWidth = 400
    maxHeight = 400
    spacing = 6
    padding = Insets(6)
    children = List(toolbar, jokePane)
  }

  stage = new JFXApp.PrimaryStage {
    title.value = "Chuck Norris Jokes"
    scene = new Scene {
      root = appPane
    }
  }
}