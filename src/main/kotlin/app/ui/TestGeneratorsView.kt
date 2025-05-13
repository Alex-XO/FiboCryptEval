package app.ui

import app.AppContext
import app.model.EvaluationResult
import app.service.ChartDrawer
import app.service.GeneratorEvaluator
import app.tester.*
import app.util.allGenerators
import javafx.embed.swing.SwingNode
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.VBox
import javafx.stage.Stage
import kotlin.random.Random

class TestGeneratorsView(private val primaryStage: Stage) : VBox() {

    init {
        spacing = 10.0

        val fixedSeed = true
        val seeds = if (fixedSeed) {
            listOf(12345L, 54321L, 112233L, 445566L, 778899L)
        } else {
            List(5) { Random.nextLong() }
        }

        val generators = allGenerators(seeds)

        val tests = listOf(
            PearsonTest(),
            KolmogorovSmirnovTest(),
            AutocorrelationTest(),
            EntropyTest(),
            PeriodicityTest(),
            MonteCarloTest(),
            BirthdaySpacingsTest(),
            OverlappingPermutationsTest()
        )

        val results: List<EvaluationResult> = GeneratorEvaluator.evaluate(generators, tests)
        val best = results.first()
        val seededBest = generators.first { it.name == best.name }
        AppContext.bestGenerator = seededBest

        val chartPanel = ChartDrawer.buildChart(results)
        val swingNode = SwingNode().apply {
            content = chartPanel
        }

        val encryptButton = Button("Зашифровать с лучшим генератором").apply {
            setOnAction {
                primaryStage.scene = Scene(EncryptView(primaryStage = primaryStage, fromTestView = false), 800.0, 600.0)
            }
        }

        val backButton = Button("⬅ Назад в меню").apply {
            setOnAction {
                primaryStage.scene = Scene(MainView(primaryStage).root, 500.0, 300.0)
            }
        }

        children.addAll(swingNode, encryptButton, backButton)
    }
}