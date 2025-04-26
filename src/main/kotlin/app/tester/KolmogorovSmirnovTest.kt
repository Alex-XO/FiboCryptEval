package app.tester

import app.generator.RandomGenerator
import kotlin.math.abs

class KolmogorovSmirnovTest : GeneratorTest {
    override val name = "Kolmogorov-Smirnov Test"

    override fun runTest(generator: RandomGenerator): Double {
        val samples = DoubleArray(1000) {
            (generator.nextByte().toInt() and 0xFF) / 255.0
        }.sorted()

        var d = 0.0
        for (i in samples.indices) {
            val cdf = samples[i]
            val expected = (i + 1).toDouble() / samples.size
            d = maxOf(d, abs(cdf - expected))
        }
        return 1.0 - d
    }
}