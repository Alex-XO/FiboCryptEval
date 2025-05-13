package app.ui

import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.VBox
import javafx.stage.Stage

class MainView(private val primaryStage: Stage) {

    val root = VBox(10.0).apply {
        spacing = 10.0

        val testButton = Button("🔬 Тестировать генераторы").apply {
            setOnAction {
                // Переключение на окно тестирования генераторов
                primaryStage.scene = Scene(TestGeneratorsView(primaryStage), 800.0, 600.0)
            }
        }

        val encryptButton = Button("🔐 Зашифровать текст").apply {
            setOnAction {
                // Переход на Окно шифрования с выбором генератора
                primaryStage.scene = Scene(EncryptView(primaryStage = primaryStage, fromTestView = false), 800.0, 600.0)
            }
        }

        val reportButton = Button("📁 Показать отчёты").apply {
            setOnAction {
                // TODO: реализовать переход на ReportView
            }
        }

        children.addAll(testButton, encryptButton, reportButton)
    }
}