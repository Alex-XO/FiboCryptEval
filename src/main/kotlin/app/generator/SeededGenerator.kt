package app.generator

data class SeededGenerator(
    val name: String,
    val constructor: (Long) -> RandomGenerator,
    val seed: Long
) {
    companion object {
        fun create(name: String, constructor: (Long) -> RandomGenerator, seed: Long): SeededGenerator {
            return SeededGenerator(name, constructor, seed)
        }
    }
}
