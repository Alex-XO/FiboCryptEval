package app.service

import app.generator.RandomGenerator
import app.tester.GeneratorTest
import org.slf4j.LoggerFactory

// Класс для выбора лучшего генератора и отображения результатов
class GeneratorSelector(
    private val generators: List<RandomGenerator>,
    private val tests: List<GeneratorTest>
) {
    private val logger = LoggerFactory.getLogger(GeneratorSelector::class.java)

    fun selectBest(): RandomGenerator {
        val scores = mutableListOf<Double>()
        val names = mutableListOf<String>()

        var bestScore = Double.NEGATIVE_INFINITY
        var bestGenerator: RandomGenerator? = null

        for (generator in generators) {
            logger.info("Тестирование генератора: ${generator::class.simpleName}")
            var totalScore = 0.0

            for (test in tests) {
                val score = test.runTest(generator)
                logger.info("Результат теста [${test.name}]: $score")
                totalScore += score
            }

            names.add(generator::class.simpleName ?: "Unknown")
            scores.add(totalScore)

            logger.info("Итоговый балл генератора ${generator::class.simpleName}: $totalScore")

            if (totalScore > bestScore) {
                bestScore = totalScore
                bestGenerator = generator
            }
        }

        // Рисуем график после всех тестов
        ChartDrawer.drawScores(names, scores)

        if (bestGenerator == null) {
            throw IllegalStateException("Не удалось выбрать лучший генератор")
        }

        logger.info("Выбран лучший генератор: ${bestGenerator::class.simpleName} с баллом $bestScore")

        return bestGenerator
    }
}