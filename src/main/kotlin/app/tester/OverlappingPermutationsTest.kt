package app.tester

import app.generator.RandomGenerator
import kotlin.math.abs

// Тест Diehard: Overlapping Permutations
class OverlappingPermutationsTest : GeneratorTest {
    override val name = "Diehard Overlapping Permutations Test"

    override fun runTest(generator: RandomGenerator): Double {
        val n = 1000
        val k = 5 // длина перестановки

        val counts = mutableMapOf<String, Int>()

        // Генерация последовательностей по k байт
        for (i in 0 until n) {
            val window = List(k) { generator.nextByte().toInt() and 0xFF }
            val permutation = window.sorted().joinToString(",")

            counts[permutation] = counts.getOrDefault(permutation, 0) + 1
        }

        // Ожидаемое количество каждой перестановки
        val expected = n.toDouble() / counts.size

        // Вычисляем χ²
        var chiSquare = 0.0
        for (count in counts.values) {
            chiSquare += (count - expected) * (count - expected) / expected
        }

        return 1.0 / (1.0 + chiSquare)
    }
}