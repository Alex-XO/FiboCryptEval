package app.ui

import app.AppContext
import app.generator.SeededGenerator
import app.model.EncryptionResult
import app.service.Encryptor
import app.util.allGenerators
import javafx.geometry.Insets
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.scene.text.Text
import javafx.stage.Stage
import kotlin.random.Random

class EncryptView(
    private val primaryStage: Stage,
    private val fromTestView: Boolean = false
) : VBox() {

    init {
        spacing = 10.0
        padding = Insets(15.0)

        val inputLabel = Label("Введите текст для шифрования:")
        val inputField = TextField()

        val resultLabel = Label("Результат:")
        val resultArea = Text()

        // Комбо-бокс выбора генератора
        val generatorSelector = ComboBox<SeededGenerator>()

        if (!fromTestView) {
            val generators = allGenerators(List(5) { Random.nextLong() })
            generatorSelector.items.addAll(generators)

            // Отображаем только имя генератора
            generatorSelector.setCellFactory {
                object : ListCell<SeededGenerator>() {
                    override fun updateItem(item: SeededGenerator?, empty: Boolean) {
                        super.updateItem(item, empty)
                        text = if (empty || item == null) "" else item.name
                    }
                }
            }
            generatorSelector.buttonCell = object : ListCell<SeededGenerator>() {
                override fun updateItem(item: SeededGenerator?, empty: Boolean) {
                    super.updateItem(item, empty)
                    text = if (empty || item == null) "" else item.name
                }
            }

            generatorSelector.promptText = "Выберите генератор"
        }

        val encryptButton = Button("🔐 Зашифровать").apply {
            setOnAction {
                val text = inputField.text
                if (text.isNullOrEmpty()) {
                    resultArea.text = "Введите текст."
                    return@setOnAction
                }

                // Получаем сид и конструктор
                val seeded = if (fromTestView) {
                    AppContext.bestGenerator
                } else {
                    val selected = generatorSelector.value
                    if (selected == null) {
                        resultArea.text = "Выберите генератор."
                        return@setOnAction
                    }
                    selected
                }

                val generator = seeded.constructor(seeded.seed)
                val encryptor = Encryptor(generator, seeded.seed)
                val result: EncryptionResult = encryptor.encrypt(text)
                resultArea.text = "Base64: ${result.encryptedBase64}\nSeed: ${result.seed}"
            }
        }

        val backButton = Button("⬅ Назад в меню").apply {
            setOnAction {
                primaryStage.scene = javafx.scene.Scene(MainView(primaryStage).root, 500.0, 300.0)
            }
        }

        children.addAll(inputLabel, inputField)
        if (!fromTestView) children.add(generatorSelector)
        children.addAll(encryptButton, resultLabel, resultArea, backButton)
    }
}