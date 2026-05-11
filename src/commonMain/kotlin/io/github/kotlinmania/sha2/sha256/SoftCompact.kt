// port-lint: source src/sha256/soft_compact.rs
package io.github.kotlinmania.sha2.sha256

import io.github.kotlinmania.sha2.K32

private fun toU32s(block: ByteArray): UIntArray {
    val res = UIntArray(16)
    for (i in 0 until 16) {
        val b0 = (block[4 * i].toInt() and 0xFF) shl 24
        val b1 = (block[4 * i + 1].toInt() and 0xFF) shl 16
        val b2 = (block[4 * i + 2].toInt() and 0xFF) shl 8
        val b3 = block[4 * i + 3].toInt() and 0xFF
        res[i] = (b0 or b1 or b2 or b3).toUInt()
    }
    return res
}

private fun compressU32(state: UIntArray, block: UIntArray) {
    var a = state[0]
    var b = state[1]
    var c = state[2]
    var d = state[3]
    var e = state[4]
    var f = state[5]
    var g = state[6]
    var h = state[7]

    val w = UIntArray(64)
    block.copyInto(w, 0, 0, 16)

    for (i in 16 until 64) {
        val w15 = w[i - 15]
        val s0 = w15.rotateRight(7) xor w15.rotateRight(18) xor (w15 shr 3)
        val w2 = w[i - 2]
        val s1 = w2.rotateRight(17) xor w2.rotateRight(19) xor (w2 shr 10)
        w[i] = w[i - 16] + s0 + w[i - 7] + s1
    }

    for (i in 0 until 64) {
        val s1 = e.rotateRight(6) xor e.rotateRight(11) xor e.rotateRight(25)
        val ch = (e and f) xor (e.inv() and g)
        val t1 = s1 + ch + K32[i] + w[i] + h
        val s0 = a.rotateRight(2) xor a.rotateRight(13) xor a.rotateRight(22)
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

internal fun compress(state: UIntArray, blocks: Array<ByteArray>) {
    for (block in blocks) {
        compressU32(state, toU32s(block))
    }
}
