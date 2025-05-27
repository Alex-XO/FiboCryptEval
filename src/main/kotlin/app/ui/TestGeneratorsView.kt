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
import app.model.GeneratorTestRow
import javafx.collections.FXCollections
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import app.generator.SeededGenerator
import javafx.application.Platform

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

        fun createResultsTable(generators: List<SeededGenerator>): TableView<GeneratorTestRow> {
            fun fmt(score: Double): String = "%.4f".format(score)

            val rows = generators.map { seeded ->
                GeneratorTestRow(
                    name = seeded.name,
                    pearson = fmt(PearsonTest().runTest(seeded.constructor(seeded.seed))),
                    ks = fmt(KolmogorovSmirnovTest().runTest(seeded.constructor(seeded.seed))),
                    autocorrelation = fmt(AutocorrelationTest().runTest(seeded.constructor(seeded.seed))),
                    entropy = fmt(EntropyTest().runTest(seeded.constructor(seeded.seed))),
                    periodicity = fmt(PeriodicityTest().runTest(seeded.constructor(seeded.seed))),
                    monteCarlo = fmt(MonteCarloTest().runTest(seeded.constructor(seeded.seed))),
                    birthday = fmt(BirthdaySpacingsTest().runTest(seeded.constructor(seeded.seed))),
                    permutations = fmt(OverlappingPermutationsTest().runTest(seeded.constructor(seeded.seed)))
                )
            }

            return TableView<GeneratorTestRow>().apply {
                columns.add(TableColumn<GeneratorTestRow, String>("Генератор").apply {
                    cellValueFactory = PropertyValueFactory("name")
                })
                columns.add(TableColumn<GeneratorTestRow, String>("Pearson").apply {
                    cellValueFactory = PropertyValueFactory("pearson")
                })
                columns.add(TableColumn<GeneratorTestRow, String>("K-S").apply {
                    cellValueFactory = PropertyValueFactory("ks")
                })
                columns.add(TableColumn<GeneratorTestRow, String>("Autocorrelation").apply {
                    cellValueFactory = PropertyValueFactory("autocorrelation")
                })
                columns.add(TableColumn<GeneratorTestRow, String>("Entropy").apply {
                    cellValueFactory = PropertyValueFactory("entropy")
                })
                columns.add(TableColumn<GeneratorTestRow, String>("Periodicity").apply {
                    cellValueFactory = PropertyValueFactory("periodicity")
                })
                columns.add(TableColumn<GeneratorTestRow, String>("Monte Carlo").apply {
                    cellValueFactory = PropertyValueFactory("monteCarlo")
                })
                columns.add(TableColumn<GeneratorTestRow, String>("Birthday").apply {
                    cellValueFactory = PropertyValueFactory("birthday")
                })
                columns.add(TableColumn<GeneratorTestRow, String>("Permutations").apply {
                    cellValueFactory = PropertyValueFactory("permutations")
                })

                items = FXCollections.observableArrayList(rows)
                prefHeight = 300.0
            }
        }


        val results: List<EvaluationResult> = GeneratorEvaluator.evaluate(generators, tests)
        val best = results.first()
        val seededBest = generators.first { it.name == best.name }
        AppContext.bestGenerator = seededBest

        val chartPanel = ChartDrawer.buildChart(results)
        val swingNode = SwingNode()
        Platform.runLater {
            swingNode.content = chartPanel
        }

        val encryptButton = Button("Зашифровать с лучшим генератором").apply {
            setOnAction {
                primaryStage.scene = Scene(EncryptView(primaryStage = primaryStage), 650.0, 450.0)
            }
        }

        val backButton = Button("⬅ Назад в меню").apply {
            setOnAction {
                primaryStage.scene = Scene(MainView(primaryStage).root, 650.0, 450.0)
            }
        }

        val tableView = createResultsTable(generators)
        children.addAll(swingNode, tableView, encryptButton, backButton)
    }
}