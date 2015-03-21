package fx

import scala.concurrent.ExecutionContext

import scalafx.Includes._
import scalafx.application.{JFXApp, Platform}
import scalafx.event.ActionEvent
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout.{BorderPane, HBox, VBox}

import rest.AsyncRest

object RestApp extends JFXApp {
  private implicit def executor: ExecutionContext = ExecutionContext.global

  private val jokeLabel = new Label {
    text = "Joke:"
  }

  private val jokeText = new TextArea() {
    wrapText = true
  }

  private val indicator = new ProgressBar {
    progress = 0.0
  }

  private val jokeButton = new Button {
    text = "New Joke"
    defaultButton = true
    onAction = (e: ActionEvent) => {
      Platform.runLater(indicator.progress = 0.99)
      val future = AsyncRest.asyncJoke
      future.map {
        text => Platform.runLater(jokeText.text = text)
        Platform.runLater(indicator.progress = 0.0)
      }
    }
  }

  private val jokePane = new BorderPane {
    center = new VBox {
      maxHeight = 140
      spacing = 6
      padding = Insets(6)
      children = List(jokeLabel, jokeText, jokeButton)
    }
    bottom = new HBox {
      alignment = Pos.BaselineRight
      spacing = 6
      padding = Insets(6)
      children = indicator
    }
  }

  private val appPane = new VBox {
    maxWidth = 400
    maxHeight = 400
    spacing = 6
    padding = Insets(6)
    children = List(jokePane)
  }

  stage = new JFXApp.PrimaryStage {
    title.value = "Chuck Norris Jokes"
    scene = new Scene {
      root = appPane
    }
  }
}