// port-lint: source src/sha512/soft.rs
package io.github.kotlinmania.sha2.sha512

import io.github.kotlinmania.sha2.BLOCK_LEN
import io.github.kotlinmania.sha2.K64X2

internal object Soft {
    private fun add(a: ULongArray, b: ULongArray): ULongArray =
        ulongArrayOf(a[0] + b[0], a[1] + b[1])

    /** Not an intrinsic, but works like an unaligned load. */
    private fun sha512load(v0: ULongArray, v1: ULongArray): ULongArray =
        ulongArrayOf(v1[1], v0[0])

    /** Performs 2 rounds of the SHA-512 message schedule update. */
    fun sha512ScheduleX2(v0: ULongArray, v1: ULongArray, v4to5: ULongArray, v7: ULongArray): ULongArray {
        // sigma 0
        fun sigma0(x: ULong): ULong =
            ((x shl 63) or (x shr 1)) xor ((x shl 56) or (x shr 8)) xor (x shr 7)

        // sigma 1
        fun sigma1(x: ULong): ULong =
            ((x shl 45) or (x shr 19)) xor ((x shl 3) or (x shr 61)) xor (x shr 6)

        val w1 = v0[0]
        val w0 = v0[1]
        val w2 = v1[1]
        val w10 = v4to5[0]
        val w9 = v4to5[1]
        val w15 = v7[0]
        val w14 = v7[1]

        val w16 = sigma1(w14) + w9 + sigma0(w1) + w0
        val w17 = sigma1(w15) + w10 + sigma0(w2) + w1

        return ulongArrayOf(w17, w16)
    }

    /** Performs one round of the SHA-512 message block digest. */
    fun sha512DigestRound(
        ae: ULongArray,
        bf: ULongArray,
        cg: ULongArray,
        dh: ULongArray,
        wk0: ULong,
    ): ULongArray {
        fun bigSigma0(a: ULong): ULong = a.rotateRight(28) xor a.rotateRight(34) xor a.rotateRight(39)

        fun bigSigma1(a: ULong): ULong = a.rotateRight(14) xor a.rotateRight(18) xor a.rotateRight(41)

        // Choose, MD5F, SHA1C
        fun bool3ary202(a: ULong, b: ULong, c: ULong): ULong = c xor (a and (b xor c))

        // Majority, SHA1M
        fun bool3ary232(a: ULong, b: ULong, c: ULong): ULong = (a and b) xor (a and c) xor (b and c)

        val a0 = ae[0]
        val e0 = ae[1]
        val b0 = bf[0]
        val f0 = bf[1]
        val c0 = cg[0]
        val g0 = cg[1]
        val d0 = dh[0]
        val h0 = dh[1]

        // a round
        val x0 = bigSigma1(e0) + bool3ary202(e0, f0, g0) + wk0 + h0
        val y0 = bigSigma0(a0) + bool3ary232(a0, b0, c0)
        val a1 = x0 + y0
        val e1 = x0 + d0

        return ulongArrayOf(a1, e1)
    }

    /** Process a block with the SHA-512 algorithm. */
    fun sha512DigestBlockU64(state: ULongArray, block: ULongArray) {
        val k = K64X2

        var ae = ulongArrayOf(state[0], state[4])
        var bf = ulongArrayOf(state[1], state[5])
        var cg = ulongArrayOf(state[2], state[6])
        var dh = ulongArrayOf(state[3], state[7])

        val rounds4 = { wk0: ULongArray, wk1: ULongArray ->
            val u = wk0[0]
            val t = wk0[1]
            val w = wk1[0]
            val v = wk1[1]

            dh = sha512DigestRound(ae, bf, cg, dh, t)
            cg = sha512DigestRound(dh, ae, bf, cg, u)
            bf = sha512DigestRound(cg, dh, ae, bf, v)
            ae = sha512DigestRound(bf, cg, dh, ae, w)
        }

        fun schedule(v0: ULongArray, v1: ULongArray, v4: ULongArray, v5: ULongArray, v7: ULongArray): ULongArray =
            sha512ScheduleX2(v0, v1, sha512load(v4, v5), v7)

        // Rounds 0..20
        var w1 = ulongArrayOf(block[3], block[2])
        var w0 = ulongArrayOf(block[1], block[0])
        rounds4(add(k[0], w0), add(k[1], w1))
        var w3 = ulongArrayOf(block[7], block[6])
        var w2 = ulongArrayOf(block[5], block[4])
        rounds4(add(k[2], w2), add(k[3], w3))
        var w5 = ulongArrayOf(block[11], block[10])
        var w4 = ulongArrayOf(block[9], block[8])
        rounds4(add(k[4], w4), add(k[5], w5))
        var w7 = ulongArrayOf(block[15], block[14])
        var w6 = ulongArrayOf(block[13], block[12])
        rounds4(add(k[6], w6), add(k[7], w7))
        var w8 = schedule(w0, w1, w4, w5, w7)
        var w9 = schedule(w1, w2, w5, w6, w8)
        rounds4(add(k[8], w8), add(k[9], w9))

        // Rounds 20..40
        w0 = schedule(w2, w3, w6, w7, w9)
        w1 = schedule(w3, w4, w7, w8, w0)
        rounds4(add(k[10], w0), add(k[11], w1))
        w2 = schedule(w4, w5, w8, w9, w1)
        w3 = schedule(w5, w6, w9, w0, w2)
        rounds4(add(k[12], w2), add(k[13], w3))
        w4 = schedule(w6, w7, w0, w1, w3)
        w5 = schedule(w7, w8, w1, w2, w4)
        rounds4(add(k[14], w4), add(k[15], w5))
        w6 = schedule(w8, w9, w2, w3, w5)
        w7 = schedule(w9, w0, w3, w4, w6)
        rounds4(add(k[16], w6), add(k[17], w7))
        w8 = schedule(w0, w1, w4, w5, w7)
        w9 = schedule(w1, w2, w5, w6, w8)
        rounds4(add(k[18], w8), add(k[19], w9))

        // Rounds 40..60
        w0 = schedule(w2, w3, w6, w7, w9)
        w1 = schedule(w3, w4, w7, w8, w0)
        rounds4(add(k[20], w0), add(k[21], w1))
        w2 = schedule(w4, w5, w8, w9, w1)
        w3 = schedule(w5, w6, w9, w0, w2)
        rounds4(add(k[22], w2), add(k[23], w3))
        w4 = schedule(w6, w7, w0, w1, w3)
        w5 = schedule(w7, w8, w1, w2, w4)
        rounds4(add(k[24], w4), add(k[25], w5))
        w6 = schedule(w8, w9, w2, w3, w5)
        w7 = schedule(w9, w0, w3, w4, w6)
        rounds4(add(k[26], w6), add(k[27], w7))
        w8 = schedule(w0, w1, w4, w5, w7)
        w9 = schedule(w1, w2, w5, w6, w8)
        rounds4(add(k[28], w8), add(k[29], w9))

        // Rounds 60..80
        w0 = schedule(w2, w3, w6, w7, w9)
        w1 = schedule(w3, w4, w7, w8, w0)
        rounds4(add(k[30], w0), add(k[31], w1))
        w2 = schedule(w4, w5, w8, w9, w1)
        w3 = schedule(w5, w6, w9, w0, w2)
        rounds4(add(k[32], w2), add(k[33], w3))
        w4 = schedule(w6, w7, w0, w1, w3)
        w5 = schedule(w7, w8, w1, w2, w4)
        rounds4(add(k[34], w4), add(k[35], w5))
        w6 = schedule(w8, w9, w2, w3, w5)
        w7 = schedule(w9, w0, w3, w4, w6)
        rounds4(add(k[36], w6), add(k[37], w7))
        w8 = schedule(w0, w1, w4, w5, w7)
        w9 = schedule(w1, w2, w5, w6, w8)
        rounds4(add(k[38], w8), add(k[39], w9))

        val a = ae[0]
        val e = ae[1]
        val b = bf[0]
        val f = bf[1]
        val c = cg[0]
        val g = cg[1]
        val d = dh[0]
        val h = dh[1]

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
        val blockU64 = ULongArray(BLOCK_LEN)
        // since LLVM can't properly use aliasing yet it will make
        // unnecessary state stores without this copy
        val stateCpy = state.copyOf()
        for (block in blocks) {
            for (i in 0 until BLOCK_LEN) {
                val b0 = (block[8 * i].toLong() and 0xFFL) shl 56
                val b1 = (block[8 * i + 1].toLong() and 0xFFL) shl 48
                val b2 = (block[8 * i + 2].toLong() and 0xFFL) shl 40
                val b3 = (block[8 * i + 3].toLong() and 0xFFL) shl 32
                val b4 = (block[8 * i + 4].toLong() and 0xFFL) shl 24
                val b5 = (block[8 * i + 5].toLong() and 0xFFL) shl 16
                val b6 = (block[8 * i + 6].toLong() and 0xFFL) shl 8
                val b7 = block[8 * i + 7].toLong() and 0xFFL
                blockU64[i] = (b0 or b1 or b2 or b3 or b4 or b5 or b6 or b7).toULong()
            }
            sha512DigestBlockU64(stateCpy, blockU64)
        }
        stateCpy.copyInto(state)
    }
}
