package io.github.kotlinmania.sha2

import io.github.kotlinmania.sha2.sha256.compress256
import io.github.kotlinmania.sha2.sha512.compress512
import kotlin.test.Test
import kotlin.test.assertContentEquals
import io.github.kotlinmania.sha2.sha256.Soft as Sha256Soft
import io.github.kotlinmania.sha2.sha512.Soft as Sha512Soft

class SoftTest {
    @Test
    fun sha256SoftAbcMatchesFipsVector() {
        val state = H256_256.copyOf()
        val block = ByteArray(64)
        block[0] = 0x61
        block[1] = 0x62
        block[2] = 0x63
        block[3] = 0x80.toByte()
        block[63] = 0x18

        Sha256Soft.compress(state, arrayOf(block))

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
    fun sha256SoftTwoBlockFipsVector() {
        // "abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq" -- 56 bytes.
        // Padding requires a 2-block message: 56 + 1 (0x80) > 64 - 8, so the
        // length-suffix lands in the second block. Bit length = 56 * 8 = 448.
        val msg = "abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq".encodeToByteArray()
        val b0 = ByteArray(64)
        msg.copyInto(b0, 0, 0, 56)
        b0[56] = 0x80.toByte()
        val b1 = ByteArray(64)
        b1[62] = 0x01 // (448 >> 8) & 0xff
        b1[63] = 0xc0.toByte() // 448 & 0xff

        val state = H256_256.copyOf()
        Sha256Soft.compress(state, arrayOf(b0, b1))

        val expected =
            uintArrayOf(
                0x248d6a61u,
                0xd20638b8u,
                0xe5c02693u,
                0x0c3e6039u,
                0xa33ce459u,
                0x64ff2167u,
                0xf6ecedd4u,
                0x19db06c1u,
            )
        assertContentEquals(expected, state)
    }

    @Test
    fun sha256SoftEmptyMatchesFipsVector() {
        val state = H256_256.copyOf()
        val block = ByteArray(64)
        block[0] = 0x80.toByte()

        Sha256Soft.compress(state, arrayOf(block))

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
    fun sha512SoftAbcMatchesFipsVector() {
        val state = H512_512.copyOf()
        val block = ByteArray(128)
        block[0] = 0x61
        block[1] = 0x62
        block[2] = 0x63
        block[3] = 0x80.toByte()
        block[127] = 0x18

        Sha512Soft.compress(state, arrayOf(block))

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
    fun sha512SoftTwoBlockFipsVector() {
        // "abcdefghbcdefghicdefghijdefghijkefghijklfghijklmghijklmnhijklmnoijklmnopjklmnopqklmnopqrlmnopqrsmnopqrstnopqrstu" -- 112 bytes.
        // Bit length = 112 * 8 = 896.
        val msg =
            (
                "abcdefghbcdefghicdefghijdefghijkefghijklfghijklm" +
                    "ghijklmnhijklmnoijklmnopjklmnopqklmnopqrlmnopqrs" +
                    "mnopqrstnopqrstu"
            ).encodeToByteArray()
        val b0 = ByteArray(128)
        msg.copyInto(b0, 0, 0, 112)
        b0[112] = 0x80.toByte()
        val b1 = ByteArray(128)
        b1[126] = 0x03 // (896 >> 8) & 0xff
        b1[127] = 0x80.toByte() // 896 & 0xff

        val state = H512_512.copyOf()
        Sha512Soft.compress(state, arrayOf(b0, b1))

        val expected =
            ulongArrayOf(
                0x8e959b75dae313dauL,
                0x8cf4f72814fc143fuL,
                0x8f7779c6eb9f7fa1uL,
                0x7299aeadb6889018uL,
                0x501d289e4900f7e4uL,
                0x331b99dec4b5433auL,
                0xc7d329eeb6dd2654uL,
                0x5e96e55b874be909uL,
            )
        assertContentEquals(expected, state)
    }

    @Test
    fun compress256DispatcherMatchesSoft() {
        val msg = "abc".encodeToByteArray()
        val block = ByteArray(64)
        msg.copyInto(block, 0, 0, 3)
        block[3] = 0x80.toByte()
        block[63] = 0x18

        val state = H256_256.copyOf()
        compress256(state, arrayOf(block))

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
    fun compress512DispatcherMatchesSoft() {
        val block = ByteArray(128)
        block[0] = 0x61
        block[1] = 0x62
        block[2] = 0x63
        block[3] = 0x80.toByte()
        block[127] = 0x18

        val state = H512_512.copyOf()
        compress512(state, arrayOf(block))

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
}
