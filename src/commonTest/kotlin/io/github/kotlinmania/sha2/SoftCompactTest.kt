package io.github.kotlinmania.sha2

import kotlin.test.Test
import kotlin.test.assertContentEquals
import io.github.kotlinmania.sha2.sha256.SoftCompact as Sha256SoftCompact
import io.github.kotlinmania.sha2.sha512.SoftCompact as Sha512SoftCompact

class SoftCompactTest {
    @Test
    fun sha256AbcMatchesFipsVector() {
        val state = H256_256.copyOf()
        val block = ByteArray(64)
        block[0] = 0x61
        block[1] = 0x62
        block[2] = 0x63
        block[3] = 0x80.toByte()
        block[63] = 0x18 // length in bits = 24

        Sha256SoftCompact.compress(state, arrayOf(block))

        val expected =
            uintArrayOf(
                0xba7816bfu,
                0x8f01cfeau,
                0x414140deu,
                0x5dae2223u,
                0xb00361a3u,
                0x96177a9cu,
                0xb410ff61u,
                0xf20015adu,
            )
        assertContentEquals(expected, state)
    }

    @Test
    fun sha256EmptyMatchesFipsVector() {
        val state = H256_256.copyOf()
        val block = ByteArray(64)
        block[0] = 0x80.toByte()

        Sha256SoftCompact.compress(state, arrayOf(block))

        val expected =
            uintArrayOf(
                0xe3b0c442u,
                0x98fc1c14u,
                0x9afbf4c8u,
                0x996fb924u,
                0x27ae41e4u,
                0x649b934cu,
                0xa495991bu,
                0x7852b855u,
            )
        assertContentEquals(expected, state)
    }

    @Test
    fun sha512AbcMatchesFipsVector() {
        val state = H512_512.copyOf()
        val block = ByteArray(128)
        block[0] = 0x61
        block[1] = 0x62
        block[2] = 0x63
        block[3] = 0x80.toByte()
        block[127] = 0x18 // length in bits = 24

        Sha512SoftCompact.compress(state, arrayOf(block))

        val expected =
            ulongArrayOf(
                0xddaf35a193617abauL,
                0xcc417349ae204131uL,
                0x12e6fa4e89a97ea2uL,
                0x0a9eeee64b55d39auL,
                0x2192992a274fc1a8uL,
                0x36ba3c23a3feebbduL,
                0x454d4423643ce80euL,
                0x2a9ac94fa54ca49fuL,
            )
        assertContentEquals(expected, state)
    }

    @Test
    fun sha512EmptyMatchesFipsVector() {
        val state = H512_512.copyOf()
        val block = ByteArray(128)
        block[0] = 0x80.toByte()

        Sha512SoftCompact.compress(state, arrayOf(block))

        val expected =
            ulongArrayOf(
                0xcf83e1357eefb8bduL,
                0xf1542850d66d8007uL,
                0xd620e4050b5715dcuL,
                0x83f4a921d36ce9ceuL,
                0x47d0d13c5d85f2b0uL,
                0xff8318d2877eec2fuL,
                0x63b931bd47417a81uL,
                0xa538327af927da3euL,
            )
        assertContentEquals(expected, state)
    }
}
