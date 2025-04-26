package app

import app.generator.*
import app.service.Encryptor
import app.service.GeneratorSelector
import app.tester.*
import org.slf4j.LoggerFactory
import kotlin.random.Random

fun main() {
    val logger = LoggerFactory.getLogger("Application")

    val fixedSeed = false // Здесь выбираем режим

    // Генерация сидов
    val seeds = if (fixedSeed) {
        listOf(12345L, 54321L, 112233L, 445566L, 778899L)
    } else {
        List(5) { Random.nextLong() }
    }

    // Инициализация генераторов с сидами
    val generators = listOf(
        LastDigitFibonacciGenerator().apply { reseed(seeds[0]) },
        LaggedFibonacciGenerator().apply { reseed(seeds[1]) },
        FishGenerator().apply { reseed(seeds[2]) },
        PikeGenerator().apply { reseed(seeds[3]) },
        MushGenerator().apply { reseed(seeds[4]) }
    )

    // Инициализация всех тестов
    val tests = listOf(
        PearsonTest(),
        KolmogorovSmirnovTest(),
        AutocorrelationTest(),
        EntropyTest(),
        PeriodicityTest(),
        MonteCarloTest(),
        BirthdaySpacingsTest(),
        OverlappingPermutationsTest()
    )

    logger.info("Режим запуска: ${if (fixedSeed) "ФИКСИРОВАННЫЕ сиды" else "СЛУЧАЙНЫЕ сиды"}")

    // Выбор лучшего генератора
    val selector = GeneratorSelector(generators, tests)
    val bestGenerator = selector.selectBest()

    // Шифрование текста
    val encryptor = Encryptor(bestGenerator)
    val inputText = "Hello, world!"
    val result = encryptor.encrypt(inputText)

    logger.info("Зашифрованный текст (Base64): ${result.encryptedBase64}")

// Дешифрование текста
    val decryptedText = encryptor.decrypt(result.encryptedBase64)
    logger.info("Расшифрованный текст: $decryptedText")
}