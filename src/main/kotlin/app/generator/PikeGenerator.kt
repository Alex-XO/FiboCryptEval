package app.generator

class PikeGenerator(seed: Long) : RandomGenerator {
    private var state = LongArray(2)

    init {
        reseed(seed)
    }

    override fun nextByte(): Byte {
        val next = (state[0] + state[1]) % 256
        state[0] = state[1]
        state[1] = (state[1] + next + 1) % 256
        return next.toByte()
    }

    override fun reseed(seed: Long) {
        state[0] = seed and 0xFF
        state[1] = (seed shr 8) and 0xFF
    }
}