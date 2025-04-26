package app.util

// Класс для красивого вывода в консоль
object ConsolePrinter {

    fun printEncryptionResult(originalText: String, encryptedBase64: String, decryptedText: String) {
        println()
        println("=".repeat(60))
        println("Результаты шифрования:")
        println("=".repeat(60))
        println(String.format("%-20s: %s", "Исходный текст", originalText))
        println(String.format("%-20s: %s", "Зашифрованный (Base64)", encryptedBase64))
        println(String.format("%-20s: %s", "Расшифрованный текст", decryptedText))
        println("=".repeat(60))
    }
}