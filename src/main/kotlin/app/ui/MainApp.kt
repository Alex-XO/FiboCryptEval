package app.ui

import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage

class MainApp : Application() {
    override fun start(primaryStage: Stage) {
        val mainView = MainView(primaryStage)
        val scene = Scene(mainView.root, 650.0, 450.0)
        primaryStage.title = "FiboCryptEval"
        primaryStage.scene = scene
        primaryStage.show()
    }
}

fun main() {
    Application.launch(MainApp::class.java)
}