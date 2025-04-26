package app.service

import app.generator.RandomGenerator
import app.model.EncryptionResult
import java.util.Base64
import kotlin.reflect.full.createInstance

// Класс для шифрования и дешифрования текста
class Encryptor(
    private val generator: RandomGenerator,
    private val seed: Long
) {

    fun encrypt(input: String): EncryptionResult {
        generator.reseed(seed) // Сброс генератора перед шифрованием

        val inputBytes = input.toByteArray(Charsets.UTF_8)
        val encrypted = inputBytes.map { byte ->
            (byte.toInt() xor (generator.nextByte().toInt() and 0xFF)).toByte()
        }.toByteArray()
        val base64 = Base64.getEncoder().encodeToString(encrypted)
        return EncryptionResult(base64, generator::class.simpleName ?: "Unknown", seed)
    }

    companion object {
        // Дешифрование
        fun decrypt(result: EncryptionResult): String {
            // Находим класс генератора по имени
            val generatorClass = Class.forName("app.generator.${result.generatorName}").kotlin
            val generator = generatorClass.createInstance() as RandomGenerator
            generator.reseed(result.seed) // Сбрасываем генератор в правильное состояние

            val encryptedBytes = Base64.getDecoder().decode(result.encryptedBase64)
            val decrypted = encryptedBytes.map { byte ->
                (byte.toInt() xor (generator.nextByte().toInt() and 0xFF)).toByte()
            }.toByteArray()

            return decrypted.toString(Charsets.UTF_8)
        }
    }
}