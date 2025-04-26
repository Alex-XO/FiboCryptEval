package app.tester

import app.generator.RandomGenerator
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.math.abs
import kotlin.math.PI

// Тест Монте-Карло на основе оценки числа π
class MonteCarloTest : GeneratorTest {
    override val name = "Monte Carlo Test"

    override fun runTest(generator: RandomGenerator): Double {
        val samples = 10000
        var insideCircle = 0

        for (i in 0 until samples) {
            // Генерация двух случайных чисел от 0 до 1
            val x = (generator.nextByte().toInt() and 0xFF) / 255.0
            val y = (generator.nextByte().toInt() and 0xFF) / 255.0

            // Проверяем, попала ли точка в круг
            if (x.pow(2) + y.pow(2) <= 1.0) {
                insideCircle++
            }
        }

        // Оценка числа π
        val piEstimate = 4.0 * insideCircle / samples

        // Абсолютная ошибка
        val error = abs(PI - piEstimate)

        // Чем меньше ошибка, тем лучше
        return 1.0 / (1.0 + error)
    }
}