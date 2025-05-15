package app.ui

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.stage.Stage

class MainView(private val primaryStage: Stage) {

    val root = VBox(20.0).apply {
        padding = Insets(40.0)
        alignment = Pos.CENTER

        // Заголовок
        val title = Label("FiboCryptEval").apply {
            font = Font.font(24.0)
        }

        // Кнопки
        val testButton = Button("🔬 Тестировать генераторы").apply {
            prefWidth = 250.0
            setOnAction {
                primaryStage.scene = Scene(TestGeneratorsView(primaryStage), 650.0, 450.0)
            }
        }

        val encryptButton = Button("🔐 Зашифровать текст").apply {
            prefWidth = 250.0
            setOnAction {
                primaryStage.scene = Scene(EncryptView(primaryStage), 650.0, 450.0)
            }
        }

        children.addAll(title, testButton, encryptButton)
    }
}