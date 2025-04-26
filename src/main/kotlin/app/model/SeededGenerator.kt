package app.model

import app.generator.RandomGenerator

data class SeededGenerator(
    val generator: RandomGenerator,
    val seed: Long
)