package app.ui

import app.generator.*
import app.model.EvaluationResult
import app.service.ChartDrawer
import app.service.GeneratorEvaluator
import app.tester.*
import javafx.embed.swing.SwingNode
import javafx.scene.control.Button
import javafx.scene.layout.VBox
import kotlin.random.Random

class TestGeneratorsView : VBox() {

    init {
        spacing = 10.0

        // Список тестов
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

        // Генерация сидов и генераторов
        val generators = allGenerators(seed = Random.nextLong())

        // Выполнение оценки генераторов
        val results: List<EvaluationResult> = GeneratorEvaluator.evaluate(generators, tests)

        // Создание панели с графиком
        val chartPanel = ChartDrawer.buildChart(results)
        val swingNode = SwingNode().apply {
            content = chartPanel
        }

        // Кнопка шифрования (пока без обработчика)
        val encryptButton = Button("Зашифровать с лучшим генератором")

        // Добавляем элементы в интерфейс
        children.addAll(swingNode, encryptButton)
    }
}