package app.service

import app.generator.SeededGenerator
import app.model.EvaluationResult
import app.tester.GeneratorTest

object GeneratorEvaluator {
    fun evaluate(
        generators: List<SeededGenerator>,
        tests: List<GeneratorTest>
    ): List<EvaluationResult> {
        return generators.map { seeded ->
            val totalScore = tests.sumOf {
                it.runTest(seeded.constructor(seeded.seed))
            }
            val averageScore = totalScore / tests.size
            EvaluationResult(seeded.name, averageScore)
        }.sortedByDescending { it.score }
    }
}
