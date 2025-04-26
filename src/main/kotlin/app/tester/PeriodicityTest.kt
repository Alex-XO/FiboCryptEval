package app.tester

import app.generator.RandomGenerator

// Тест на периодичность генератора
class PeriodicityTest : GeneratorTest {
    override val name = "Periodicity Test"

    override fun runTest(generator: RandomGenerator): Double {
        val size = 10000
        val sequence = ByteArray(size) { generator.nextByte() }

        // Используем хеш-карту для поиска повторов
        val seen = mutableMapOf<String, Int>()

        var minPeriod = size

        // Проверяем подпоследовательности длиной от 2 до 16 байт
        for (patternLength in 2..16) {
            seen.clear()
            for (i in 0..size - patternLength) {
                val pattern = sequence.sliceArray(i until i + patternLength).joinToString(",")
                if (pattern in seen) {
                    val period = i - seen[pattern]!!
                    if (period < minPeriod) {
                        minPeriod = period
                    }
                    break
                } else {
                    seen[pattern] = i
                }
            }
        }

        // Чем больше минимальный период, тем лучше
        return if (minPeriod == size) 1.0 else minPeriod.toDouble() / size
    }
}