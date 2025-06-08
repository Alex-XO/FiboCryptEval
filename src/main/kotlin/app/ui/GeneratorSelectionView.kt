package app.ui

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.stage.Stage

class GeneratorSelectionView(private val primaryStage: Stage) : VBox(20.0) {

    init {
        padding = Insets(30.0)
        alignment = Pos.TOP_LEFT

        val title = Label("Выберите способ подбора генератора").apply {
            font = Font.font(18.0)
        }

        val toggleGroup = ToggleGroup()

        val autoRadio = RadioButton("Автоматический подбор").apply {
            this.toggleGroup = toggleGroup
            isSelected = true
        }

        val manualRadio = RadioButton("Ручной выбор").apply {
            this.toggleGroup = toggleGroup
        }

        val continueButton = Button("Продолжить").apply {
            setOnAction {
                val nextScene = if (autoRadio.isSelected) {
                    TestGeneratorsView(primaryStage)
                } else {
                    ManualSelectView(primaryStage)
                }
                primaryStage.scene = Scene(nextScene, 650.0, 500.0)
            }
        }

        children.addAll(title, autoRadio, manualRadio, continueButton)
    }
}