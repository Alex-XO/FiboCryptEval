package app.ui

import app.AppContext
import app.service.GeneratorSelectorService
import app.service.GeneratorSelectorService.ScoredGenerator
import javafx.application.Platform
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.stage.Stage

class ManualSelectView(private val primaryStage: Stage) : VBox(20.0) {

    init {
        padding = Insets(20.0)
        alignment = Pos.TOP_LEFT

        val scoredGenerators = GeneratorSelectorService.getScoredGenerators()
        if (scoredGenerators.isEmpty()) {
            Platform.runLater {
                showAlert(
                    title = "Нет подходящих генераторов",
                    message = "Ни один генератор не удовлетворяет критериям."
                )
                primaryStage.scene = Scene(MainView(primaryStage).root, 650.0, 450.0)
            }
        } else {
            setupUI(scoredGenerators)
        }
    }

    private fun setupUI(generators: List<ScoredGenerator>) {
        val title = Label("Ручной выбор генератора").apply {
            font = Font.font(18.0)
        }

        val threshold = 0.15

        val generatorSelector = ComboBox<ScoredGenerator>().apply {
            items.addAll(generators)

            setCellFactory {
                object : ListCell<ScoredGenerator>() {
                    override fun updateItem(item: ScoredGenerator?, empty: Boolean) {
                        super.updateItem(item, empty)
                        if (empty || item == null) {
                            text = ""
                            style = ""
                        } else {
                            text = item.generator.name
                            style = if (item.totalScore < threshold) {
                                "-fx-text-fill: red;"
                            } else {
                                ""
                            }
                        }
                    }
                }
            }

            buttonCell = object : ListCell<ScoredGenerator>() {
                override fun updateItem(item: ScoredGenerator?, empty: Boolean) {
                    super.updateItem(item, empty)
                    text = if (empty || item == null) "" else item.generator.name
                }
            }

            promptText = "Выберите генератор"
        }

        val descriptionArea = TextArea().apply {
            isEditable = false
            isWrapText = true
            prefRowCount = 10
        }

        generatorSelector.valueProperty().addListener { _, _, selected ->
            if (selected != null) {
                val sb = StringBuilder()
                sb.appendLine("Генератор: ${selected.generator.name}")
                sb.appendLine("Seed: ${selected.generator.seed}")
                sb.appendLine("Общая оценка: %.4f".format(selected.totalScore))
                sb.appendLine()
                sb.appendLine("Результаты тестов:")
                selected.scores.forEach { (test, score) ->
                    sb.appendLine(" - $test: %.4f".format(score))
                }
                descriptionArea.text = sb.toString()
            } else {
                descriptionArea.clear()
            }
        }

        val continueButton = Button("Перейти к шифрованию").apply {
            setOnAction {
                val selected = generatorSelector.selectionModel.selectedItem
                if (selected == null) {
                    showAlert("Ошибка", "Выберите генератор перед продолжением.")
                    return@setOnAction
                }

                AppContext.bestGenerator = selected.generator
                primaryStage.scene = Scene(EncryptView(primaryStage), 650.0, 500.0)
            }
        }

        children.addAll(title, generatorSelector, descriptionArea, continueButton)
    }

    private fun showAlert(title: String, message: String) {
        val alert = Alert(Alert.AlertType.WARNING)
        alert.title = title
        alert.headerText = null
        alert.contentText = message
        alert.showAndWait()
    }
}