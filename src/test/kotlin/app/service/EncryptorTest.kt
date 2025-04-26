package app.service

import app.generator.LaggedFibonacciGenerator
import app.model.EncryptionResult
import kotlin.test.Test
import kotlin.test.assertEquals

class EncryptorTest {

    @Test
    fun testEncryptDecrypt() {
        val generator = LaggedFibonacciGenerator()
        val seed = 123456789L

        val encryptor = Encryptor(generator, seed)

        val originalText = "Test encryption and decryption"

        val result: EncryptionResult = encryptor.encrypt(originalText)

        val decryptedText: String = Encryptor.decrypt(result)

        assertEquals(
            expected = originalText,
            actual = decryptedText,
            message = "Расшифрованный текст не совпадает с исходным"
        )
    }
}