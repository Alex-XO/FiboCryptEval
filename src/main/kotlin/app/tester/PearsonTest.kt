package app.tester

import app.generator.RandomGenerator

class PearsonTest : GeneratorTest {
    override val name = "Pearson Chi-Square Test"

    override fun runTest(generator: RandomGenerator): Double {
        val counts = IntArray(256)
        repeat(10000) {
            val value = generator.nextByte().toInt() and 0xFF
            counts[value]++
        }
        val expected = 10000.0 / 256
        var chiSquare = 0.0
        for (count in counts) {
            chiSquare += (count - expected) * (count - expected) / expected
        }
        return 1.0 / (1.0 + chiSquare)
    }
}