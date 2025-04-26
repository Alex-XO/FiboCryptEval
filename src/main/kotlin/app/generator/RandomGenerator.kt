package app.generator

interface RandomGenerator {
    fun nextByte(): Byte
    fun reseed(seed: Long)
}