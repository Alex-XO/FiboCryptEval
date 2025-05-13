package app.util

import app.generator.*

fun allGenerators(seeds: List<Long>): List<SeededGenerator> {
    require(seeds.size >= 5) { "Необходимо минимум 5 сидов" }

    return listOf(
        SeededGenerator.create(name = "FibonacciLastDigit", ::LastDigitFibonacciGenerator, seeds[0]),
        SeededGenerator.create(name = "LaggedFibonacci", { s -> LaggedFibonacciGenerator(seed = s) }, seeds[1]),
        SeededGenerator.create(name = "Fish", ::FishGenerator, seeds[2]),
        SeededGenerator.create(name = "Pike", ::PikeGenerator, seeds[3]),
        SeededGenerator.create(name = "Mush", ::MushGenerator, seeds[4])
    )
}