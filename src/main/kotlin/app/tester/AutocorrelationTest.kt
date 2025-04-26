package app.tester

import app.generator.RandomGenerator
import kotlin.math.abs

// Тест на автокорреляцию
class AutocorrelationTest : GeneratorTest {
    override val name = "Autocorrelation Test"

    override fun runTest(generator: RandomGenerator): Double {
        val size = 10000
        val sequence = ByteArray(size) { generator.nextByte() }

        // Выбираем лаг (смещение)
        val lag = 1
        var sum = 0L

        for (i in 0 until size - lag) {
            val x = sequence[i].toInt() and 0xFF
            val y = sequence[i + lag].toInt() and 0xFF
            sum += if (x == y) 1 else 0
        }

        val expected = (size - lag) / 256.0
        val autocorrelation = abs(sum - expected) / expected

        // Чем ближе autocorrelation к 0, тем лучше
        return 1.0 / (1.0 + autocorrelation)
    }
}