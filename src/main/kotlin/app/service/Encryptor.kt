package app.service

import app.generator.RandomGenerator
import app.model.EncryptionResult
import java.util.Base64

// Класс для шифрования и дешифрования текста
class Encryptor(private val generator: RandomGenerator) {

    // Шифрование строки в Base64
    fun encrypt(input: String): EncryptionResult {
        val inputBytes = input.toByteArray(Charsets.UTF_8)
        val encrypted = inputBytes.map { byte ->
            (byte.toInt() xor (generator.nextByte().toInt() and 0xFF)).toByte()
        }.toByteArray()
        val base64 = Base64.getEncoder().encodeToString(encrypted)
        return EncryptionResult(base64)
    }

    // Дешифрование строки из Base64
    fun decrypt(encryptedBase64: String): String {
        val encryptedBytes = Base64.getDecoder().decode(encryptedBase64)
        val decrypted = encryptedBytes.map { byte ->
            (byte.toInt() xor (generator.nextByte().toInt() and 0xFF)).toByte()
        }.toByteArray()
        return decrypted.toString(Charsets.UTF_8)
    }
}