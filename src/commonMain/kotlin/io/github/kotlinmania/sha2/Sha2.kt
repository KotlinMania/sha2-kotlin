@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.sha2

import io.github.kotlinmania.digest.BlockSizeUser
import io.github.kotlinmania.digest.Digest
import io.github.kotlinmania.digest.DigestFactory
import io.github.kotlinmania.digest.Output
import io.github.kotlinmania.sha2.sha256.compress256
import io.github.kotlinmania.sha2.sha512.compress512

private const val SHA256_BLOCK_SIZE: Int = 64
private const val SHA512_BLOCK_SIZE: Int = 128
private const val SHA224_OUTPUT_SIZE: Int = 28
private const val SHA256_OUTPUT_SIZE: Int = 32
private const val SHA384_OUTPUT_SIZE: Int = 48
private const val SHA512_OUTPUT_SIZE: Int = 64
private const val SHA512_224_OUTPUT_SIZE: Int = 28
private const val SHA512_256_OUTPUT_SIZE: Int = 32

/** SHA-224 hasher. */
class Sha224 private constructor(
    private val engine: Sha256Engine,
) : Digest,
    BlockSizeUser {
    constructor() : this(Sha256Engine(H256_224, SHA224_OUTPUT_SIZE))

    override fun update(data: ByteArray) {
        engine.update(data)
    }

    override fun finalize(): ByteArray = engine.finalize()

    override fun finalizeReset(): ByteArray = engine.finalizeReset()

    override fun finalizeInto(out: Output<*>) {
        val result = finalize()
        result.copyInto(out)
    }

    override fun finalizeIntoReset(out: Output<*>) {
        val result = finalizeReset()
        result.copyInto(out)
    }

    override fun reset() {
        engine.reset()
    }

    fun copy(): Sha224 = Sha224(engine.copy())

    override val blockSize: Int get() = SHA256_BLOCK_SIZE
    override val outputSize: Int get() = SHA224_OUTPUT_SIZE

    companion object {
        init {
            Digest.register(
                Sha224::class,
                object : DigestFactory<Sha224> {
                    override fun new(): Sha224 = Sha224()

                    override val outputSize: Int = SHA224_OUTPUT_SIZE
                    override val blockSize: Int = SHA256_BLOCK_SIZE
                },
            )
        }

        fun new(): Sha224 = Sha224()

        fun newWithPrefix(data: ByteArray): Sha224 {
            val hasher = Sha224()
            hasher.update(data)
            return hasher
        }

        fun digest(data: ByteArray): ByteArray = Digest.digest(Sha224::class, data)
    }
}

/** SHA-256 hasher. */
class Sha256 private constructor(
    private val engine: Sha256Engine,
) : Digest,
    BlockSizeUser {
    constructor() : this(Sha256Engine(H256_256, SHA256_OUTPUT_SIZE))

    override fun update(data: ByteArray) {
        engine.update(data)
    }

    override fun finalize(): ByteArray = engine.finalize()

    override fun finalizeReset(): ByteArray = engine.finalizeReset()

    override fun finalizeInto(out: Output<*>) {
        val result = finalize()
        result.copyInto(out)
    }

    override fun finalizeIntoReset(out: Output<*>) {
        val result = finalizeReset()
        result.copyInto(out)
    }

    override fun reset() {
        engine.reset()
    }

    fun copy(): Sha256 = Sha256(engine.copy())

    override val blockSize: Int get() = SHA256_BLOCK_SIZE
    override val outputSize: Int get() = SHA256_OUTPUT_SIZE

    companion object {
        init {
            Digest.register(
                Sha256::class,
                object : DigestFactory<Sha256> {
                    override fun new(): Sha256 = Sha256()

                    override val outputSize: Int = SHA256_OUTPUT_SIZE
                    override val blockSize: Int = SHA256_BLOCK_SIZE
                },
            )
        }

        fun new(): Sha256 = Sha256()

        fun newWithPrefix(data: ByteArray): Sha256 {
            val hasher = Sha256()
            hasher.update(data)
            return hasher
        }

        fun digest(data: ByteArray): ByteArray = Digest.digest(Sha256::class, data)
    }
}

/** SHA-384 hasher. */
class Sha384 private constructor(
    private val engine: Sha512Engine,
) : Digest,
    BlockSizeUser {
    constructor() : this(Sha512Engine(H512_384, SHA384_OUTPUT_SIZE))

    override fun update(data: ByteArray) {
        engine.update(data)
    }

    override fun finalize(): ByteArray = engine.finalize()

    override fun finalizeReset(): ByteArray = engine.finalizeReset()

    override fun finalizeInto(out: Output<*>) {
        val result = finalize()
        result.copyInto(out)
    }

    override fun finalizeIntoReset(out: Output<*>) {
        val result = finalizeReset()
        result.copyInto(out)
    }

    override fun reset() {
        engine.reset()
    }

    fun copy(): Sha384 = Sha384(engine.copy())

    override val blockSize: Int get() = SHA512_BLOCK_SIZE
    override val outputSize: Int get() = SHA384_OUTPUT_SIZE

    companion object {
        init {
            Digest.register(
                Sha384::class,
                object : DigestFactory<Sha384> {
                    override fun new(): Sha384 = Sha384()

                    override val outputSize: Int = SHA384_OUTPUT_SIZE
                    override val blockSize: Int = SHA512_BLOCK_SIZE
                },
            )
        }

        fun new(): Sha384 = Sha384()

        fun newWithPrefix(data: ByteArray): Sha384 {
            val hasher = Sha384()
            hasher.update(data)
            return hasher
        }

        fun digest(data: ByteArray): ByteArray = Digest.digest(Sha384::class, data)
    }
}

/** SHA-512 hasher. */
class Sha512 private constructor(
    private val engine: Sha512Engine,
) : Digest,
    BlockSizeUser {
    constructor() : this(Sha512Engine(H512_512, SHA512_OUTPUT_SIZE))

    override fun update(data: ByteArray) {
        engine.update(data)
    }

    override fun finalize(): ByteArray = engine.finalize()

    override fun finalizeReset(): ByteArray = engine.finalizeReset()

    override fun finalizeInto(out: Output<*>) {
        val result = finalize()
        result.copyInto(out)
    }

    override fun finalizeIntoReset(out: Output<*>) {
        val result = finalizeReset()
        result.copyInto(out)
    }

    override fun reset() {
        engine.reset()
    }

    fun copy(): Sha512 = Sha512(engine.copy())

    override val blockSize: Int get() = SHA512_BLOCK_SIZE
    override val outputSize: Int get() = SHA512_OUTPUT_SIZE

    companion object {
        init {
            Digest.register(
                Sha512::class,
                object : DigestFactory<Sha512> {
                    override fun new(): Sha512 = Sha512()

                    override val outputSize: Int = SHA512_OUTPUT_SIZE
                    override val blockSize: Int = SHA512_BLOCK_SIZE
                },
            )
        }

        fun new(): Sha512 = Sha512()

        fun newWithPrefix(data: ByteArray): Sha512 {
            val hasher = Sha512()
            hasher.update(data)
            return hasher
        }

        fun digest(data: ByteArray): ByteArray = Digest.digest(Sha512::class, data)
    }
}

/** SHA-512/224 hasher. */
class Sha512b224 private constructor(
    private val engine: Sha512Engine,
) : Digest,
    BlockSizeUser {
    constructor() : this(Sha512Engine(H512_224, SHA512_224_OUTPUT_SIZE))

    override fun update(data: ByteArray) {
        engine.update(data)
    }

    override fun finalize(): ByteArray = engine.finalize()

    override fun finalizeReset(): ByteArray = engine.finalizeReset()

    override fun finalizeInto(out: Output<*>) {
        val result = finalize()
        result.copyInto(out)
    }

    override fun finalizeIntoReset(out: Output<*>) {
        val result = finalizeReset()
        result.copyInto(out)
    }

    override fun reset() {
        engine.reset()
    }

    fun copy(): Sha512b224 = Sha512b224(engine.copy())

    override val blockSize: Int get() = SHA512_BLOCK_SIZE
    override val outputSize: Int get() = SHA512_224_OUTPUT_SIZE

    companion object {
        init {
            Digest.register(
                Sha512b224::class,
                object : DigestFactory<Sha512b224> {
                    override fun new(): Sha512b224 = Sha512b224()

                    override val outputSize: Int = SHA512_224_OUTPUT_SIZE
                    override val blockSize: Int = SHA512_BLOCK_SIZE
                },
            )
        }

        fun new(): Sha512b224 = Sha512b224()

        fun newWithPrefix(data: ByteArray): Sha512b224 {
            val hasher = Sha512b224()
            hasher.update(data)
            return hasher
        }

        fun digest(data: ByteArray): ByteArray = Digest.digest(Sha512b224::class, data)
    }
}

/** SHA-512/256 hasher. */
class Sha512b256 private constructor(
    private val engine: Sha512Engine,
) : Digest,
    BlockSizeUser {
    constructor() : this(Sha512Engine(H512_256, SHA512_256_OUTPUT_SIZE))

    override fun update(data: ByteArray) {
        engine.update(data)
    }

    override fun finalize(): ByteArray = engine.finalize()

    override fun finalizeReset(): ByteArray = engine.finalizeReset()

    override fun finalizeInto(out: Output<*>) {
        val result = finalize()
        result.copyInto(out)
    }

    override fun finalizeIntoReset(out: Output<*>) {
        val result = finalizeReset()
        result.copyInto(out)
    }

    override fun reset() {
        engine.reset()
    }

    fun copy(): Sha512b256 = Sha512b256(engine.copy())

    override val blockSize: Int get() = SHA512_BLOCK_SIZE
    override val outputSize: Int get() = SHA512_256_OUTPUT_SIZE

    companion object {
        init {
            Digest.register(
                Sha512b256::class,
                object : DigestFactory<Sha512b256> {
                    override fun new(): Sha512b256 = Sha512b256()

                    override val outputSize: Int = SHA512_256_OUTPUT_SIZE
                    override val blockSize: Int = SHA512_BLOCK_SIZE
                },
            )
        }

        fun new(): Sha512b256 = Sha512b256()

        fun newWithPrefix(data: ByteArray): Sha512b256 {
            val hasher = Sha512b256()
            hasher.update(data)
            return hasher
        }

        fun digest(data: ByteArray): ByteArray = Digest.digest(Sha512b256::class, data)
    }
}

typealias Sha512_224 = Sha512b224
typealias Sha512_256 = Sha512b256

private class Sha256Engine(
    private val initialState: UIntArray,
    private val digestSize: Int,
) {
    private val state: UIntArray = initialState.copyOf()
    private val buffer: ByteArray = ByteArray(SHA256_BLOCK_SIZE)
    private var bufferPos: Int = 0
    private var bitLength: ULong = 0u

    fun update(data: ByteArray) {
        addBitLength(data.size)
        var offset = 0
        var remaining = data.size

        if (bufferPos > 0) {
            val take = minOf(remaining, SHA256_BLOCK_SIZE - bufferPos)
            data.copyInto(buffer, bufferPos, offset, offset + take)
            bufferPos += take
            offset += take
            remaining -= take
            if (bufferPos == SHA256_BLOCK_SIZE) {
                compress256(state, arrayOf(buffer.copyOf()))
                buffer.fill(0)
                bufferPos = 0
            }
        }

        while (remaining >= SHA256_BLOCK_SIZE) {
            compress256(state, arrayOf(data.copyOfRange(offset, offset + SHA256_BLOCK_SIZE)))
            offset += SHA256_BLOCK_SIZE
            remaining -= SHA256_BLOCK_SIZE
        }

        if (remaining > 0) {
            data.copyInto(buffer, 0, offset, offset + remaining)
            bufferPos = remaining
        }
    }

    fun finalize(): ByteArray {
        val finalState = state.copyOf()
        padWith64BitLength(buffer, bufferPos, bitLength, SHA256_BLOCK_SIZE) { block ->
            compress256(finalState, arrayOf(block))
        }
        return encodeUIntState(finalState, digestSize)
    }

    fun finalizeReset(): ByteArray {
        val digest = finalize()
        reset()
        return digest
    }

    fun reset() {
        initialState.copyInto(state)
        buffer.fill(0)
        bufferPos = 0
        bitLength = 0u
    }

    fun copy(): Sha256Engine {
        val engine = Sha256Engine(initialState, digestSize)
        state.copyInto(engine.state)
        buffer.copyInto(engine.buffer)
        engine.bufferPos = bufferPos
        engine.bitLength = bitLength
        return engine
    }

    private fun addBitLength(byteCount: Int) {
        bitLength += byteCount.toULong() * 8uL
    }
}

private class Sha512Engine(
    private val initialState: ULongArray,
    private val digestSize: Int,
) {
    private val state: ULongArray = initialState.copyOf()
    private val buffer: ByteArray = ByteArray(SHA512_BLOCK_SIZE)
    private var bufferPos: Int = 0
    private var bitLengthHigh: ULong = 0u
    private var bitLengthLow: ULong = 0u

    fun update(data: ByteArray) {
        addBitLength(data.size)
        var offset = 0
        var remaining = data.size

        if (bufferPos > 0) {
            val take = minOf(remaining, SHA512_BLOCK_SIZE - bufferPos)
            data.copyInto(buffer, bufferPos, offset, offset + take)
            bufferPos += take
            offset += take
            remaining -= take
            if (bufferPos == SHA512_BLOCK_SIZE) {
                compress512(state, arrayOf(buffer.copyOf()))
                buffer.fill(0)
                bufferPos = 0
            }
        }

        while (remaining >= SHA512_BLOCK_SIZE) {
            compress512(state, arrayOf(data.copyOfRange(offset, offset + SHA512_BLOCK_SIZE)))
            offset += SHA512_BLOCK_SIZE
            remaining -= SHA512_BLOCK_SIZE
        }

        if (remaining > 0) {
            data.copyInto(buffer, 0, offset, offset + remaining)
            bufferPos = remaining
        }
    }

    fun finalize(): ByteArray {
        val finalState = state.copyOf()
        padWith128BitLength(buffer, bufferPos, bitLengthHigh, bitLengthLow) { block ->
            compress512(finalState, arrayOf(block))
        }
        return encodeULongState(finalState, digestSize)
    }

    fun finalizeReset(): ByteArray {
        val digest = finalize()
        reset()
        return digest
    }

    fun reset() {
        initialState.copyInto(state)
        buffer.fill(0)
        bufferPos = 0
        bitLengthHigh = 0u
        bitLengthLow = 0u
    }

    fun copy(): Sha512Engine {
        val engine = Sha512Engine(initialState, digestSize)
        state.copyInto(engine.state)
        buffer.copyInto(engine.buffer)
        engine.bufferPos = bufferPos
        engine.bitLengthHigh = bitLengthHigh
        engine.bitLengthLow = bitLengthLow
        return engine
    }

    private fun addBitLength(byteCount: Int) {
        val addedLow = byteCount.toULong() * 8uL
        val previousLow = bitLengthLow
        bitLengthLow += addedLow
        if (bitLengthLow < previousLow) {
            bitLengthHigh += 1u
        }
    }
}

internal inline fun padWith64BitLength(
    buffer: ByteArray,
    bufferPos: Int,
    bitLength: ULong,
    blockSize: Int,
    compressor: (ByteArray) -> Unit,
) {
    val block = ByteArray(blockSize)
    buffer.copyInto(block, 0, 0, bufferPos)
    block[bufferPos] = 0x80.toByte()

    if (bufferPos >= blockSize - 8) {
        compressor(block)
        block.fill(0)
    }

    writeULongBigEndian(bitLength, block, blockSize - 8)
    compressor(block)
}

internal inline fun padWith128BitLength(
    buffer: ByteArray,
    bufferPos: Int,
    bitLengthHigh: ULong,
    bitLengthLow: ULong,
    compressor: (ByteArray) -> Unit,
) {
    val block = ByteArray(SHA512_BLOCK_SIZE)
    buffer.copyInto(block, 0, 0, bufferPos)
    block[bufferPos] = 0x80.toByte()

    if (bufferPos >= SHA512_BLOCK_SIZE - 16) {
        compressor(block)
        block.fill(0)
    }

    writeULongBigEndian(bitLengthHigh, block, SHA512_BLOCK_SIZE - 16)
    writeULongBigEndian(bitLengthLow, block, SHA512_BLOCK_SIZE - 8)
    compressor(block)
}

private fun encodeUIntState(state: UIntArray, digestSize: Int): ByteArray {
    val output = ByteArray(state.size * UInt.SIZE_BYTES)
    for ((wordIndex, word) in state.withIndex()) {
        writeUIntBigEndian(word, output, wordIndex * UInt.SIZE_BYTES)
    }
    return output.copyOf(digestSize)
}

private fun encodeULongState(state: ULongArray, digestSize: Int): ByteArray {
    val output = ByteArray(state.size * ULong.SIZE_BYTES)
    for ((wordIndex, word) in state.withIndex()) {
        writeULongBigEndian(word, output, wordIndex * ULong.SIZE_BYTES)
    }
    return output.copyOf(digestSize)
}

internal fun writeUIntBigEndian(value: UInt, output: ByteArray, offset: Int) {
    for (byteIndex in 0 until UInt.SIZE_BYTES) {
        val shift = (UInt.SIZE_BYTES - 1 - byteIndex) * Byte.SIZE_BITS
        output[offset + byteIndex] = ((value shr shift) and 0xFFu).toByte()
    }
}

internal fun writeULongBigEndian(value: ULong, output: ByteArray, offset: Int) {
    for (byteIndex in 0 until ULong.SIZE_BYTES) {
        val shift = (ULong.SIZE_BYTES - 1 - byteIndex) * Byte.SIZE_BITS
        output[offset + byteIndex] = ((value shr shift) and 0xFFuL).toByte()
    }
}
