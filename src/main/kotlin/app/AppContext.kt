package app

import app.generator.SeededGenerator
import app.model.UserRequirements

object AppContext {
    lateinit var bestGenerator: SeededGenerator
    var requirements: UserRequirements? = null

    var fixedSeed: Boolean = false

    val seeds: List<Long> by lazy {
        if (fixedSeed) {
            listOf(12345L, 54321L, 112233L, 445566L, 778899L)
        } else {
            List(5) { kotlin.random.Random.nextLong() }
        }
    }

    fun isInitialized(): Boolean = this::bestGenerator.isInitialized
}
