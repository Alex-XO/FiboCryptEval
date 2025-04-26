package app.tester

import app.generator.RandomGenerator

interface GeneratorTest {
    val name: String
    fun runTest(generator: RandomGenerator): Double
}