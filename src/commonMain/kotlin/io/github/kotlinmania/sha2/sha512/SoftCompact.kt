// port-lint: source src/sha512/soft_compact.rs
package io.github.kotlinmania.sha2.sha512

import io.github.kotlinmania.sha2.K64

internal object SoftCompact {

    private fun toU64s(block: ByteArray): ULongArray {
        val res = ULongArray(16)
        for (i in 0 until 16) {
            val b0 = (block[8 * i].toLong() and 0xFFL) shl 56
            val b1 = (block[8 * i + 1].toLong() and 0xFFL) shl 48
            val b2 = (block[8 * i + 2].toLong() and 0xFFL) shl 40
            val b3 = (block[8 * i + 3].toLong() and 0xFFL) shl 32
            val b4 = (block[8 * i + 4].toLong() and 0xFFL) shl 24
            val b5 = (block[8 * i + 5].toLong() and 0xFFL) shl 16
            val b6 = (block[8 * i + 6].toLong() and 0xFFL) shl 8
            val b7 = block[8 * i + 7].toLong() and 0xFFL
            res[i] = (b0 or b1 or b2 or b3 or b4 or b5 or b6 or b7).toULong()
        }
        return res
    }

    private fun compressU64(state: ULongArray, block: ULongArray) {
        var a = state[0]
        var b = state[1]
        var c = state[2]
        var d = state[3]
        var e = state[4]
        var f = state[5]
        var g = state[6]
        var h = state[7]

        val w = ULongArray(80)
        block.copyInto(w, 0, 0, 16)

        for (i in 16 until 80) {
            val w15 = w[i - 15]
            val s0 = w15.rotateRight(1) xor w15.rotateRight(8) xor (w15 shr 7)
            val w2 = w[i - 2]
            val s1 = w2.rotateRight(19) xor w2.rotateRight(61) xor (w2 shr 6)
            w[i] = w[i - 16] + s0 + w[i - 7] + s1
        }

        for (i in 0 until 80) {
            val s1 = e.rotateRight(14) xor e.rotateRight(18) xor e.rotateRight(41)
            val ch = (e and f) xor (e.inv() and g)
            val t1 = s1 + ch + K64[i] + w[i] + h
            val s0 = a.rotateRight(28) xor a.rotateRight(34) xor a.rotateRight(39)
            val maj = (a and b) xor (a and c) xor (b and c)
            val t2 = s0 + maj

            h = g
            g = f
            f = e
            e = d + t1
            d = c
            c = b
            b = a
            a = t1 + t2
        }

        state[0] = state[0] + a
        state[1] = state[1] + b
        state[2] = state[2] + c
        state[3] = state[3] + d
        state[4] = state[4] + e
        state[5] = state[5] + f
        state[6] = state[6] + g
        state[7] = state[7] + h
    }

    fun compress(state: ULongArray, blocks: Array<ByteArray>) {
        for (block in blocks) {
            compressU64(state, toU64s(block))
        }
    }
}
