package app.tester

import app.generator.RandomGenerator
import kotlin.math.abs

// Тест Diehard: Birthday Spacings
class BirthdaySpacingsTest : GeneratorTest {
    override val name = "Diehard Birthday Spacings Test"

    override fun runTest(generator: RandomGenerator): Double {
        val n = 512
        val birthdays = IntArray(n)

        // Генерация случайных "дней рождения"
        for (i in birthdays.indices) {
            birthdays[i] = (generator.nextByte().toInt() and 0xFF) shl 8 or
                    (generator.nextByte().toInt() and 0xFF)
        }

        birthdays.sort()

        // Считаем количество совпадений интервалов
        var collisions = 0
        for (i in 1 until birthdays.size) {
            if (birthdays[i] == birthdays[i - 1]) {
                collisions++
            }
        }

        // Чем меньше коллизий, тем лучше
        val expected = n.toDouble() * n / (2.0 * 65536.0)
        val deviation = abs(collisions - expected) / expected

        return 1.0 / (1.0 + deviation)
    }
}