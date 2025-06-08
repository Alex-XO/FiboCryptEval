package app.service

import app.AppContext
import app.generator.SeededGenerator
import app.tester.*
import app.util.allGenerators

object GeneratorSelectorService {

    data class ScoredGenerator(
        val generator: app.generator.SeededGenerator,
        val scores: Map<String, Double>,
        val totalScore: Double
    )

    fun getScoredGenerators(minScore: Double = 0.0): List<ScoredGenerator> {
        val seeds = AppContext.seeds
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

        val weights = requireNotNull(AppContext.requirements)

        return generators.map { seeded ->
            val scores = tests.associate { it.name to it.runTest(seeded.constructor(seeded.seed)) }

            val weightedSum = scores.entries.sumOf { (name, score) ->
                val weight = when (name) {
                    "Pearson", "Kolmogorov-Smirnov", "Entropy" -> weights.quality
                    "Autocorrelation", "Periodicity" -> weights.robustness
                    "Monte Carlo" -> weights.flexibility
                    "Diehard Birthday Spacings Test" -> weights.performance
                    "Diehard Overlapping Permutations Test" -> weights.resourceUsage
                    else -> 0.0
                }
                score * weight
            }

            val weightTotal = weights.run {
                quality * 3 + robustness * 2 + performance + resourceUsage + flexibility
            }

            val finalScore = if (weightTotal == 0.0) {
                1.0 // если веса все нули — считаем генератор подходящим
            } else {
                weightedSum / weightTotal
            }

            ScoredGenerator(
                generator = seeded,
                scores = scores,
                totalScore = finalScore
            )
        }
            .filter { it.totalScore >= minScore }
            .sortedByDescending { it.totalScore }
    }
}

