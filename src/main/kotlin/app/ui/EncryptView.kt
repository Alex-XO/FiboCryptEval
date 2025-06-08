package app.ui

import app.AppContext
import app.model.EncryptionResult
import app.service.Encryptor
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.stage.Stage

class EncryptView(
    private val primaryStage: Stage,
    private val fromTests: Boolean = false) : VBox(20.0) {

    init {
        padding = Insets(20.0)
        alignment = Pos.TOP_LEFT

        val title = Label("🔐 Шифрование текста").apply {
            font = Font.font(20.0)
        }

        val inputLabel = Label("Введите текст:")
        val inputField = TextField().apply {
            promptText = "Ваш текст..."
            maxWidth = 400.0
        }

        val generatorInfo = Label().apply {
            font = Font.font(14.0)
        }

        val resultArea = Text()

        val encryptButton = Button("🔐 Зашифровать").apply {
            setOnAction {
                val text = inputField.text
                if (text.isNullOrBlank()) {
                    resultArea.text = "Введите текст для шифрования"
                    return@setOnAction
                }

                if (!AppContext.isInitialized()) {
                    resultArea.text = "Генератор не выбран"
                    return@setOnAction
                }

                val seeded = AppContext.bestGenerator
                val generator = seeded.constructor(seeded.seed)
                val encryptor = Encryptor(generator, seeded.seed)
                val result: EncryptionResult = encryptor.encrypt(text)

                resultArea.text = "Base64: ${result.encryptedBase64}\nSeed: ${result.seed}"
            }
        }

        val backButton = Button("⬅ Назад в меню").apply {
            setOnAction {
                AppContext.requirements = null
                primaryStage.scene = Scene(MainView(primaryStage).root, 650.0, 450.0)
            }
        }

        // Показываем информацию о генераторе
        if (AppContext.isInitialized()) {
            val seeded = AppContext.bestGenerator
            generatorInfo.text = "Генератор: ${seeded.name} (seed: ${seeded.seed})"
        } else {
            generatorInfo.text = "Генератор: не выбран"
        }

        children.addAll(
            title,
            inputLabel,
            inputField,
            generatorInfo,
            encryptButton,
            Label("Результат:"),
            resultArea,
            backButton
        )
    }
}