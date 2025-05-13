package app.generator

class MushGenerator(seed: Long) : RandomGenerator {
    private var a = 1L
    private var b = 1L

    init {
        reseed(seed)
    }

    override fun nextByte(): Byte {
        val next = ((a * 3 + b * 7) % 256)
        a = b
        b = (b + next) % 256
        return next.toByte()
    }

    override fun reseed(seed: Long) {
        a = seed and 0xFF
        b = (seed shr 8) and 0xFF
    }
}