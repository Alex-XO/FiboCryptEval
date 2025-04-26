package app

import app.generator.*
import app.model.EncryptionResult
import app.model.SeededGenerator
import app.service.Encryptor
import app.service.GeneratorSelector
import app.tester.*
import app.util.ConsolePrinter
import org.slf4j.LoggerFactory
import kotlin.random.Random

fun main() {
    val logger = LoggerFactory.getLogger("Application")

    val fixedSeed = true // true = фиксированные сиды, false = случайные сиды

    // Генерация списка сидов
    val seeds = if (fixedSeed) {
        listOf(12345L, 54321L, 112233L, 445566L, 778899L)
    } else {
        List(5) { Random.nextLong() }
    }

    // Инициализация генераторов с привязанными сидами
    val seededGenerators = listOf(
        SeededGenerator(LastDigitFibonacciGenerator(), seeds[0]),
        SeededGenerator(LaggedFibonacciGenerator(), seeds[1]),
        SeededGenerator(FishGenerator(), seeds[2]),
        SeededGenerator(PikeGenerator(), seeds[3]),
        SeededGenerator(MushGenerator(), seeds[4])
    )

    // Засев всех генераторов для тестирования
    seededGenerators.forEach { (generator, seed) -> generator.reseed(seed) }

    // Список самих генераторов для тестирования
    val generators = seededGenerators.map { it.generator }

    // Инициализация тестов
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

    // Выбор лучшего генератора после тестов
    val selector = GeneratorSelector(generators, tests)
    val bestGenerator = selector.selectBest()

    logger.info("Выбран генератор: ${bestGenerator::class.simpleName}")

    // Находим seed соответствующего генератора
    val bestGeneratorClass = bestGenerator::class
    val encryptionSeed = seededGenerators
        .first { it.generator::class == bestGeneratorClass }
        .seed

    logger.info("Используем сид для шифрования: $encryptionSeed")

    // Пересоздаём новый экземпляр генератора
    val cleanGenerator = bestGeneratorClass.java.getDeclaredConstructor().newInstance()
    cleanGenerator.reseed(encryptionSeed)

    // Создаём Encryptor с новым генератором
    val encryptor = Encryptor(cleanGenerator, encryptionSeed)

    // Текст для шифрования
    val inputText = "Hello, world!"

    // Шифрование
    val result: EncryptionResult = encryptor.encrypt(inputText)

    logger.info("Текст зашифрован.")

    // Дешифрование через статическую функцию
    val decryptedText: String = Encryptor.decrypt(result)

    logger.info("Текст расшифрован.")

    // Красивый вывод результата
    ConsolePrinter.printEncryptionResult(
        originalText = inputText,
        encryptedBase64 = result.encryptedBase64,
        decryptedText = decryptedText
    )
}