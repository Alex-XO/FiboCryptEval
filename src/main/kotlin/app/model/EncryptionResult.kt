package app.model

// Модель для результата шифрования
data class EncryptionResult(
    val encryptedBase64: String,
    val generatorName: String,
    val seed: Long
)