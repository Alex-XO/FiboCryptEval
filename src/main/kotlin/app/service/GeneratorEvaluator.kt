package app.service

import app.generator.SeededGenerator
import app.model.EvaluationResult
import app.model.UserRequirements
import app.tester.*

object GeneratorEvaluator {
    fun evaluate(
        generators: List<SeededGenerator>,
        tests: List<GeneratorTest>,
        weights: UserRequirements
    ): List<EvaluationResult> {
        val testMap = mapOf(
            "Pearson" to weights.quality,
            "Kolmogorov-Smirnov" to weights.quality,
            "Entropy" to weights.quality,
            "Autocorrelation" to weights.robustness,
            "Periodicity" to weights.robustness,
            "Monte Carlo" to weights.flexibility,
            "Diehard Birthday Spacings Test" to weights.performance,
            "Diehard Overlapping Permutations Test" to weights.resourceUsage
        )

        return generators.map { seeded ->
            var total = 0.0
            var weightSum = 0.0

            for (test in tests) {
                val score = test.runTest(seeded.constructor(seeded.seed))
                val weight = testMap[test.name] ?: 0.0
                total += score * weight
                weightSum += weight
            }

            val finalScore = if (weightSum == 0.0) 0.0 else total / weightSum
            EvaluationResult(seeded.name, finalScore)
        }.sortedByDescending { it.score }
    }
}