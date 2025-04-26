package app.tester

import app.generator.RandomGenerator
import kotlin.math.ln

// Тест на энтропию Шеннона
class EntropyTest : GeneratorTest {
    override val name = "Entropy Test"

    override fun runTest(generator: RandomGenerator): Double {
        val counts = IntArray(256)
        val size = 10000

        repeat(size) {
            val value = generator.nextByte().toInt() and 0xFF
            counts[value]++
        }

        var entropy = 0.0
        for (count in counts) {
            if (count > 0) {
                val p = count.toDouble() / size
                entropy -= p * ln(p) / ln(2.0) // Переводим в биты
            }
        }

        // Максимальная энтропия для 8 бит — 8
        return entropy / 8.0
    }
}