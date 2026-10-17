// port-lint: source core_api.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.sha2

import io.github.kotlinmania.digest.Block
import io.github.kotlinmania.digest.BlockSizeUser
import io.github.kotlinmania.digest.HashMarker
import io.github.kotlinmania.digest.InvalidOutputSize
import io.github.kotlinmania.digest.Output
import io.github.kotlinmania.digest.OutputSizeUser
import io.github.kotlinmania.digest.Reset
import io.github.kotlinmania.digest.coreapi.AlgorithmName
import io.github.kotlinmania.digest.coreapi.Buffer
import io.github.kotlinmania.digest.coreapi.BufferKind
import io.github.kotlinmania.digest.coreapi.BufferKindUser
import io.github.kotlinmania.digest.coreapi.Eager
import io.github.kotlinmania.digest.coreapi.TruncSide
import io.github.kotlinmania.digest.coreapi.UpdateCore
import io.github.kotlinmania.digest.coreapi.VariableOutputCore
import io.github.kotlinmania.digest.fmt.FmtResult
import io.github.kotlinmania.digest.fmt.Formatter
import io.github.kotlinmania.sha2.sha256.compress256
import io.github.kotlinmania.sha2.sha512.compress512

typealias BlockSize = Int
typealias BufferKind = Eager
typealias OutputSize = Int

/** Core block-level SHA-256 hasher with variable output size.
 *
 *  Supports initialization only for 28 and 32 byte output sizes,
 *  i.e. 224 and 256 bits respectively.
 */
class Sha256VarCore(
    private var state: UIntArray,
    private var blockLen: ULong,
) : HashMarker,
    BlockSizeUser,
    BufferKindUser,
    UpdateCore,
    OutputSizeUser,
    VariableOutputCore,
    AlgorithmName,
    Reset {
    override val blockSize: Int get() = BLOCK_SIZE
    override val bufferKind: BufferKind get() = Eager
    override val outputSize: Int get() = OUTPUT_SIZE
    override val truncSide: TruncSide get() = TruncSide.Left

    companion object {
        private const val BLOCK_SIZE: Int = 64
        private const val OUTPUT_SIZE: Int = 32

        /** Creates a new core for the given output size (28 or 32 bytes). */
        fun create(outputSize: Int): Sha256VarCore {
            val initialState =
                when (outputSize) {
                    28 -> H256_224
                    32 -> H256_256
                    else -> throw IllegalArgumentException("Invalid output size: $outputSize")
                }
            return Sha256VarCore(initialState.copyOf(), 0uL)
        }

        fun new(outputSize: Int): Sha256VarCore = create(outputSize)
    }

    override fun new(outputSize: Int): Result<VariableOutputCore> =
        try {
            Result.success(create(outputSize))
        } catch (_: IllegalArgumentException) {
            Result.failure(InvalidOutputSize())
        }

    /** Updates the state with whole blocks (each exactly 64 bytes). */
    override fun updateBlocks(blocks: List<Block<*>>) {
        blockLen += blocks.size.toULong()
        compress256(state, blocks.toTypedArray())
    }

    /** Updates the state with whole blocks (each exactly 64 bytes). */
    fun updateBlocks(blocks: Array<ByteArray>) {
        blockLen += blocks.size.toULong()
        compress256(state, blocks)
    }

    /** Finalizes the hash with the remaining buffered data and writes the output. */
    override fun finalizeVariableCore(buffer: Buffer<*>, out: Output<*>) {
        val bitLen = (buffer.getPos().toULong() + BLOCK_SIZE.toULong() * blockLen) * 8uL
        buffer.len64PaddingBe(bitLen) { block ->
            compress256(state, arrayOf(block))
        }
        for (i in state.indices) {
            writeUIntBigEndian(state[i], out, i * UInt.SIZE_BYTES)
        }
    }

    /** Finalizes the hash with the remaining buffered data and writes the output.
     *
     *  [buffer] is the partial block, [bufferPos] is the number of valid bytes in it.
     *  [output] receives the big-endian state words (truncated to the digest size by the caller).
     */
    fun finalizeVariableCore(buffer: ByteArray, bufferPos: Int, output: ByteArray) {
        val bitLen = (bufferPos.toULong() + BLOCK_SIZE.toULong() * blockLen) * 8uL
        padWith64BitLength(buffer, bufferPos, bitLen, BLOCK_SIZE) { block ->
            compress256(state, arrayOf(block))
        }
        for (i in state.indices) {
            writeUIntBigEndian(state[i], output, i * UInt.SIZE_BYTES)
        }
    }

    override fun writeAlgName(formatter: Formatter): FmtResult = formatter.writeString("Sha256")

    fun fmt(formatter: Formatter): FmtResult = formatter.writeString("Sha256VarCore { ... }")

    override fun reset() {
        state = H256_256.copyOf()
        blockLen = 0uL
    }

    fun copy(): Sha256VarCore = Sha256VarCore(state.copyOf(), blockLen)

    /** Returns a copy of the current state. */
    fun stateCopy(): UIntArray = state.copyOf()

    override fun toString(): String = "Sha256VarCore { ... }"
}

/** Core block-level SHA-512 hasher with variable output size.
 *
 *  Supports initialization only for 28, 32, 48, and 64 byte output sizes,
 *  i.e. 224, 256, 384, and 512 bits respectively.
 */
class Sha512VarCore(
    private var state: ULongArray,
    private var blockLen: ULong,
) : HashMarker,
    BlockSizeUser,
    BufferKindUser,
    UpdateCore,
    OutputSizeUser,
    VariableOutputCore,
    AlgorithmName,
    Reset {
    override val blockSize: Int get() = BLOCK_SIZE
    override val bufferKind: BufferKind get() = Eager
    override val outputSize: Int get() = OUTPUT_SIZE
    override val truncSide: TruncSide get() = TruncSide.Left

    companion object {
        private const val BLOCK_SIZE: Int = 128
        private const val OUTPUT_SIZE: Int = 64

        /** Creates a new core for the given output size (28, 32, 48, or 64 bytes). */
        fun create(outputSize: Int): Sha512VarCore {
            val initialState =
                when (outputSize) {
                    28 -> H512_224
                    32 -> H512_256
                    48 -> H512_384
                    64 -> H512_512
                    else -> throw IllegalArgumentException("Invalid output size: $outputSize")
                }
            return Sha512VarCore(initialState.copyOf(), 0uL)
        }

        fun new(outputSize: Int): Sha512VarCore = create(outputSize)
    }

    override fun new(outputSize: Int): Result<VariableOutputCore> =
        try {
            Result.success(create(outputSize))
        } catch (_: IllegalArgumentException) {
            Result.failure(InvalidOutputSize())
        }

    /** Updates the state with whole blocks (each exactly 128 bytes). */
    override fun updateBlocks(blocks: List<Block<*>>) {
        blockLen += blocks.size.toULong()
        compress512(state, blocks.toTypedArray())
    }

    /** Updates the state with whole blocks (each exactly 128 bytes). */
    fun updateBlocks(blocks: Array<ByteArray>) {
        blockLen += blocks.size.toULong()
        compress512(state, blocks)
    }

    /** Finalizes the hash with the remaining buffered data and writes the output. */
    override fun finalizeVariableCore(buffer: Buffer<*>, out: Output<*>) {
        val bitLenLow = (buffer.getPos().toULong() + BLOCK_SIZE.toULong() * blockLen) * 8uL
        padWith128BitLength(buffer.remainingBytes(), buffer.getPos(), 0uL, bitLenLow) { block ->
            compress512(state, arrayOf(block))
        }
        buffer.reset()
        for (i in state.indices) {
            writeULongBigEndian(state[i], out, i * ULong.SIZE_BYTES)
        }
    }

    /** Finalizes the hash with the remaining buffered data and writes the output.
     *
     *  [buffer] is the partial block, [bufferPos] is the number of valid bytes in it.
     *  [output] receives the big-endian state words (truncated to the digest size by the caller).
     */
    fun finalizeVariableCore(buffer: ByteArray, bufferPos: Int, output: ByteArray) {
        val bitLenLow = (bufferPos.toULong() + BLOCK_SIZE.toULong() * blockLen) * 8uL
        padWith128BitLength(buffer, bufferPos, 0uL, bitLenLow) { block ->
            compress512(state, arrayOf(block))
        }
        for (i in state.indices) {
            writeULongBigEndian(state[i], output, i * ULong.SIZE_BYTES)
        }
    }

    override fun writeAlgName(formatter: Formatter): FmtResult = formatter.writeString("Sha512")

    fun fmt(formatter: Formatter): FmtResult = formatter.writeString("Sha512VarCore { ... }")

    override fun reset() {
        state = H512_512.copyOf()
        blockLen = 0uL
    }

    fun copy(): Sha512VarCore = Sha512VarCore(state.copyOf(), blockLen)

    /** Returns a copy of the current state. */
    fun stateCopy(): ULongArray = state.copyOf()

    override fun toString(): String = "Sha512VarCore { ... }"
}
