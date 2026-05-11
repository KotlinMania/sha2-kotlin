// port-lint: source src/sha256/soft.rs
package io.github.kotlinmania.sha2.sha256

import io.github.kotlinmania.sha2.BLOCK_LEN
import io.github.kotlinmania.sha2.K32X4

internal object Soft {

    private fun shl(v: UIntArray, o: Int): UIntArray =
        uintArrayOf(v[0] shr o, v[1] shr o, v[2] shr o, v[3] shr o)

    private fun shr(v: UIntArray, o: Int): UIntArray =
        uintArrayOf(v[0] shl o, v[1] shl o, v[2] shl o, v[3] shl o)

    private fun or(a: UIntArray, b: UIntArray): UIntArray =
        uintArrayOf(a[0] or b[0], a[1] or b[1], a[2] or b[2], a[3] or b[3])

    private fun xor(a: UIntArray, b: UIntArray): UIntArray =
        uintArrayOf(a[0] xor b[0], a[1] xor b[1], a[2] xor b[2], a[3] xor b[3])

    private fun add(a: UIntArray, b: UIntArray): UIntArray =
        uintArrayOf(a[0] + b[0], a[1] + b[1], a[2] + b[2], a[3] + b[3])

    private fun sha256load(v2: UIntArray, v3: UIntArray): UIntArray =
        uintArrayOf(v3[3], v2[0], v2[1], v2[2])

    private fun sha256swap(v0: UIntArray): UIntArray =
        uintArrayOf(v0[2], v0[3], v0[0], v0[1])

    private fun sha256msg1(v0: UIntArray, v1: UIntArray): UIntArray {
        // sigma 0 on vectors
        fun sigma0x4(x: UIntArray): UIntArray {
            val t1 = or(shl(x, 7), shr(x, 25))
            val t2 = or(shl(x, 18), shr(x, 14))
            val t3 = shl(x, 3)
            return xor(xor(t1, t2), t3)
        }

        return add(v0, sigma0x4(sha256load(v0, v1)))
    }

    private fun sha256msg2(v4: UIntArray, v3: UIntArray): UIntArray {
        fun sigma1(a: UInt): UInt = a.rotateRight(17) xor a.rotateRight(19) xor (a shr 10)

        val x3 = v4[0]; val x2 = v4[1]; val x1 = v4[2]; val x0 = v4[3]
        val w15 = v3[0]; val w14 = v3[1]

        val w16 = x0 + sigma1(w14)
        val w17 = x1 + sigma1(w15)
        val w18 = x2 + sigma1(w16)
        val w19 = x3 + sigma1(w17)

        return uintArrayOf(w19, w18, w17, w16)
    }

    private fun sha256DigestRoundX2(cdgh: UIntArray, abef: UIntArray, wk: UIntArray): UIntArray {
        fun bigSigma0(a: UInt): UInt = a.rotateRight(2) xor a.rotateRight(13) xor a.rotateRight(22)
        fun bigSigma1(a: UInt): UInt = a.rotateRight(6) xor a.rotateRight(11) xor a.rotateRight(25)
        // Choose, MD5F, SHA1C
        fun bool3ary202(a: UInt, b: UInt, c: UInt): UInt = c xor (a and (b xor c))
        // Majority, SHA1M
        fun bool3ary232(a: UInt, b: UInt, c: UInt): UInt = (a and b) xor (a and c) xor (b and c)

        val wk1 = wk[2]; val wk0 = wk[3]
        val a0 = abef[0]; val b0 = abef[1]; val e0 = abef[2]; val f0 = abef[3]
        val c0 = cdgh[0]; val d0 = cdgh[1]; val g0 = cdgh[2]; val h0 = cdgh[3]

        // a round
        val x0 = bigSigma1(e0) + bool3ary202(e0, f0, g0) + wk0 + h0
        val y0 = bigSigma0(a0) + bool3ary232(a0, b0, c0)
        val a1 = x0 + y0
        val b1 = a0
        val c1 = b0
        val d1 = c0
        val e1 = x0 + d0
        val f1 = e0
        val g1 = f0
        val h1 = g0

        // a round
        val x1 = bigSigma1(e1) + bool3ary202(e1, f1, g1) + wk1 + h1
        val y1 = bigSigma0(a1) + bool3ary232(a1, b1, c1)
        val a2 = x1 + y1
        val b2 = a1
        val e2 = x1 + d1
        val f2 = e1

        return uintArrayOf(a2, b2, e2, f2)
    }

    private fun schedule(v0: UIntArray, v1: UIntArray, v2: UIntArray, v3: UIntArray): UIntArray {
        val t1 = sha256msg1(v0, v1)
        val t2 = sha256load(v2, v3)
        val t3 = add(t1, t2)
        return sha256msg2(t3, v3)
    }

    /** Process a block with the SHA-256 algorithm. */
    private fun sha256DigestBlockU32(state: UIntArray, block: UIntArray) {
        var abef = uintArrayOf(state[0], state[1], state[4], state[5])
        var cdgh = uintArrayOf(state[2], state[3], state[6], state[7])

        val rounds4 = { rest: UIntArray, i: Int ->
            val t1 = add(rest, K32X4[i])
            cdgh = sha256DigestRoundX2(cdgh, abef, t1)
            val t2 = sha256swap(t1)
            abef = sha256DigestRoundX2(abef, cdgh, t2)
        }

        // Rounds 0..64
        var w0 = uintArrayOf(block[3], block[2], block[1], block[0])
        var w1 = uintArrayOf(block[7], block[6], block[5], block[4])
        var w2 = uintArrayOf(block[11], block[10], block[9], block[8])
        var w3 = uintArrayOf(block[15], block[14], block[13], block[12])
        var w4 = UIntArray(4)

        rounds4(w0, 0)
        rounds4(w1, 1)
        rounds4(w2, 2)
        rounds4(w3, 3)
        w4 = schedule(w0, w1, w2, w3); rounds4(w4, 4)
        w0 = schedule(w1, w2, w3, w4); rounds4(w0, 5)
        w1 = schedule(w2, w3, w4, w0); rounds4(w1, 6)
        w2 = schedule(w3, w4, w0, w1); rounds4(w2, 7)
        w3 = schedule(w4, w0, w1, w2); rounds4(w3, 8)
        w4 = schedule(w0, w1, w2, w3); rounds4(w4, 9)
        w0 = schedule(w1, w2, w3, w4); rounds4(w0, 10)
        w1 = schedule(w2, w3, w4, w0); rounds4(w1, 11)
        w2 = schedule(w3, w4, w0, w1); rounds4(w2, 12)
        w3 = schedule(w4, w0, w1, w2); rounds4(w3, 13)
        w4 = schedule(w0, w1, w2, w3); rounds4(w4, 14)
        w0 = schedule(w1, w2, w3, w4); rounds4(w0, 15)

        val a = abef[0]; val b = abef[1]; val e = abef[2]; val f = abef[3]
        val c = cdgh[0]; val d = cdgh[1]; val g = cdgh[2]; val h = cdgh[3]

        state[0] = state[0] + a
        state[1] = state[1] + b
        state[2] = state[2] + c
        state[3] = state[3] + d
        state[4] = state[4] + e
        state[5] = state[5] + f
        state[6] = state[6] + g
        state[7] = state[7] + h
    }

    fun compress(state: UIntArray, blocks: Array<ByteArray>) {
        val blockU32 = UIntArray(BLOCK_LEN)
        // since LLVM can't properly use aliasing yet it will make
        // unnecessary state stores without this copy
        val stateCpy = state.copyOf()
        for (block in blocks) {
            for (i in 0 until BLOCK_LEN) {
                val b0 = (block[4 * i].toInt() and 0xFF) shl 24
                val b1 = (block[4 * i + 1].toInt() and 0xFF) shl 16
                val b2 = (block[4 * i + 2].toInt() and 0xFF) shl 8
                val b3 = block[4 * i + 3].toInt() and 0xFF
                blockU32[i] = (b0 or b1 or b2 or b3).toUInt()
            }
            sha256DigestBlockU32(stateCpy, blockU32)
        }
        stateCpy.copyInto(state)
    }
}
