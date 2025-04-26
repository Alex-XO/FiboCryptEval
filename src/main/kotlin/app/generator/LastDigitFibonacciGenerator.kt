package app.generator

class LastDigitFibonacciGenerator : RandomGenerator {
    private var a = 0
    private var b = 1

    override fun nextByte(): Byte {
        val next = (a + b) % 10
        a = b
        b = next
        return next.toByte()
    }

    override fun reseed(seed: Long) {
        a = (seed % 10).toInt()
        b = ((seed / 10) % 10).toInt()
    }
}