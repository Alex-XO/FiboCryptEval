package app.ui

import app.AppContext
import app.generator.SeededGenerator
import app.util.allGenerators
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.stage.Stage
import kotlin.random.Random

class ManualSelectView(private val primaryStage: Stage) : VBox(20.0) {

    init {
        padding = Insets(20.0)
        alignment = Pos.TOP_LEFT

        val title = Label("Ручной выбор генератора").apply {
            font = Font.font(18.0)
        }

        val generatorSelector = ComboBox<SeededGenerator>().apply {
            val generators = allGenerators(AppContext.seeds)
            items.addAll(generators)

            setCellFactory {
                object : ListCell<SeededGenerator>() {
                    override fun updateItem(item: SeededGenerator?, empty: Boolean) {
                        super.updateItem(item, empty)
                        text = if (empty || item == null) "" else item.name
                    }
                }
            }
            buttonCell = object : ListCell<SeededGenerator>() {
                override fun updateItem(item: SeededGenerator?, empty: Boolean) {
                    super.updateItem(item, empty)
                    text = if (empty || item == null) "" else item.name
                }
            }

            promptText = "Выберите генератор"
        }

        val continueButton = Button("Перейти к шифрованию").apply {
            setOnAction {
                val selected = generatorSelector.selectionModel.selectedItem
                if (selected == null) {
                    showAlert("Ошибка", "Выберите генератор перед продолжением.")
                    return@setOnAction
                }

                AppContext.bestGenerator = selected
                primaryStage.scene = Scene(EncryptView(primaryStage, fromTests = true), 650.0, 500.0)
            }
        }

        children.addAll(title, generatorSelector, continueButton)
    }

    private fun showAlert(title: String, message: String) {
        val alert = Alert(Alert.AlertType.WARNING)
        alert.title = title
        alert.headerText = null
        alert.contentText = message
        alert.showAndWait()
    }
}