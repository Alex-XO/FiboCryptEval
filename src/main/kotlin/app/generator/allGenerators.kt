package app.generator

fun allGenerators(seed: Long = System.currentTimeMillis()): List<SeededGenerator> {
    return listOf(
        SeededGenerator("FibonacciLastDigit", LastDigitFibonacciGenerator(seed)),
        SeededGenerator("LaggedFibonacci", LaggedFibonacciGenerator(seed = seed)),
        SeededGenerator("Fish", FishGenerator(seed)),
        SeededGenerator("Pike", PikeGenerator(seed)),
        SeededGenerator("Mush", MushGenerator(seed))
    )
}