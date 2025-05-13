package app.generator

class LaggedFibonacciGenerator(
    private val lag: Int = 24,
    private val shortLag: Int = 55,
    seed: Long
) : RandomGenerator {

    private val buffer = LongArray(shortLag) { it.toLong() + 1 }
    private var index = 0

    init {
        reseed(seed)
    }

    override fun nextByte(): Byte {
        val next = (buffer[(index + shortLag - lag) % shortLag] + buffer[index]) and 0xFFFFFFFF
        buffer[index] = next
        index = (index + 1) % shortLag
        return (next and 0xFF).toByte()
    }

    override fun reseed(seed: Long) {
        for (i in buffer.indices) {
            buffer[i] = seed + i
        }
    }
}