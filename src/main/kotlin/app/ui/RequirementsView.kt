package app.ui

import app.AppContext
import app.model.UserRequirements
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.stage.Stage

class RequirementsView(private val primaryStage: Stage) : VBox(15.0) {

    init {
        padding = Insets(20.0)
        alignment = Pos.TOP_LEFT

        val title = Label("Укажите важность критериев").apply {
            font = Font.font(18.0)
        }

        fun createSlider(label: String): Pair<Label, Slider> {
            val slider = Slider(0.0, 1.0, 0.5).apply {
                majorTickUnit = 0.25
                isShowTickMarks = true
                isShowTickLabels = true
                blockIncrement = 0.1
            }
            val lbl = Label("$label: 0.5")
            slider.valueProperty().addListener { _, _, new ->
                lbl.text = "$label: ${"%.2f".format(new)}"
            }
            return lbl to slider
        }

        val (robustnessLabel, robustnessSlider) = createSlider("Стойкость")
        val (qualityLabel, qualitySlider) = createSlider("Качество генерации")
        val (performanceLabel, performanceSlider) = createSlider("Производительность")
        val (resourceLabel, resourceSlider) = createSlider("Ресурсоёмкость")
        val (flexibilityLabel, flexibilitySlider) = createSlider("Гибкость")

        val continueButton = Button("Продолжить").apply {
            setOnAction {
                AppContext.requirements = UserRequirements(
                    robustnessSlider.value,
                    qualitySlider.value,
                    performanceSlider.value,
                    resourceSlider.value,
                    flexibilitySlider.value
                )
                primaryStage.scene = Scene(GeneratorSelectionView(primaryStage), 650.0, 500.0)
            }
        }

        children.addAll(
            title,
            robustnessLabel, robustnessSlider,
            qualityLabel, qualitySlider,
            performanceLabel, performanceSlider,
            resourceLabel, resourceSlider,
            flexibilityLabel, flexibilitySlider,
            continueButton
        )
    }
}