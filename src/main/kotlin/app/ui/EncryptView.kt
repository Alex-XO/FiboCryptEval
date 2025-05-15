package app.ui

import app.AppContext
import app.generator.SeededGenerator
import app.model.EncryptionResult
import app.service.Encryptor
import app.util.allGenerators
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.stage.Stage
import kotlin.random.Random

class EncryptView(
    private val primaryStage: Stage,
    private val fromTests: Boolean = false
) : VBox() {

    init {
        spacing = 20.0
        padding = Insets(20.0)

        val title = Label("🔐 Шифрование текста").apply {
            font = Font.font(20.0)
        }

        val inputLabel = Label("Введите текст:")
        val inputField = TextField().apply {
            promptText = "Ваш текст..."
            maxWidth = 400.0
        }

        val generatorLabel = Label("Выберите генератор:")
        val generatorSelector = ComboBox<SeededGenerator>()

        if (!fromTests) {
            val generators = allGenerators(List(5) { Random.nextLong() })
            generatorSelector.items.addAll(generators)

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

        val resultArea = Text()

        val encryptButton = Button("🔐 Зашифровать").apply {
            setOnAction {
                val text = inputField.text
                if (text.isNullOrBlank()) {
                    resultArea.text = "Введите текст для шифрования"
                    return@setOnAction
                }

                val seeded = if (fromTests) {
                    AppContext.bestGenerator
                } else {
                    val selected = generatorSelector.selectionModel.selectedItem
                    if (selected == null) {
                        resultArea.text = "Выберите генератор"
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
                primaryStage.scene = Scene(MainView(primaryStage).root, 650.0, 450.0)
            }
        }

        val layout = VBox(12.0).apply {
            alignment = Pos.TOP_LEFT
            padding = Insets(10.0)
            children.add(title)

            children.add(VBox(5.0).apply {
                children.addAll(inputLabel, inputField)
            })

            if (!fromTests) {
                children.add(VBox(5.0).apply {
                    children.addAll(generatorLabel, generatorSelector)
                })
            }

            children.add(encryptButton)

            children.add(VBox(5.0).apply {
                children.addAll(Label("Результат:"), resultArea)
            })

            children.add(backButton)
        }

        children.add(layout)
    }
}