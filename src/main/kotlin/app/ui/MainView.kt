package app.ui

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.stage.Stage

class MainView(private val primaryStage: Stage) {

    val root = VBox(20.0).apply {
        padding = Insets(40.0)
        alignment = Pos.CENTER

        val title = Label("FiboCryptEval").apply {
            font = Font.font(24.0)
        }

        val description = Text(
            "Эта программа позволяет оценить качество генераторов случайных чисел " +
                    "и использовать лучший из них для шифрования текста методом XOR с последующей Base64 кодировкой."
        ).apply {
            wrappingWidth = 600.0
        }

        val startButton = Button("🚀 Начать шифрование").apply {
            prefWidth = 250.0
            setOnAction {
                primaryStage.scene = Scene(RequirementsView(primaryStage), 650.0, 500.0)
            }
        }

        children.addAll(title, description, startButton)
    }
}