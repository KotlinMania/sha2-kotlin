package io.github.kotlinmania.sha2

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class Sha2DigestTest {
    private fun hex(value: String): ByteArray {
        require(value.length % 2 == 0)
        val output = ByteArray(value.length / 2)
        for (byteIndex in output.indices) {
            val highNibble = value[byteIndex * 2].digitToInt(16)
            val lowNibble = value[byteIndex * 2 + 1].digitToInt(16)
            output[byteIndex] = ((highNibble shl 4) or lowNibble).toByte()
        }
        return output
    }

    @Test
    fun sha224MatchesKnownVectors() {
        assertContentEquals(
            hex("d14a028c2a3a2bc9476102bb288234c415a2b01f828ea62ac5b3e42f"),
            Sha224.digest(ByteArray(0)),
        )
        assertContentEquals(
            hex("23097d223405d8228642a477bda255b32aadbce4bda0b3f7e36c9da7"),
            Sha224.digest("abc".encodeToByteArray()),
        )
    }

    @Test
    fun sha256MatchesKnownVectors() {
        assertContentEquals(
            hex("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"),
            Sha256.digest(ByteArray(0)),
        )
        assertContentEquals(
            hex("ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad"),
            Sha256.digest("abc".encodeToByteArray()),
        )
        assertContentEquals(
            hex("248d6a61d20638b8e5c026930c3e6039a33ce45964ff2167f6ecedd419db06c1"),
            Sha256.digest("abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq".encodeToByteArray()),
        )
    }

    @Test
    fun sha384MatchesKnownVectors() {
        assertContentEquals(
            hex(
                "38b060a751ac96384cd9327eb1b1e36a21fdb71114be0743" +
                    "4c0cc7bf63f6e1da274edebfe76f65fbd51ad2f14898b95b",
            ),
            Sha384.digest(ByteArray(0)),
        )
        assertContentEquals(
            hex(
                "cb00753f45a35e8bb5a03d699ac65007272c32ab0eded163" +
                    "1a8b605a43ff5bed8086072ba1e7cc2358baeca134c825a7",
            ),
            Sha384.digest("abc".encodeToByteArray()),
        )
    }

    @Test
    fun sha512MatchesKnownVectors() {
        assertContentEquals(
            hex(
                "cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc" +
                    "83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f" +
                    "63b931bd47417a81a538327af927da3e",
            ),
            Sha512.digest(ByteArray(0)),
        )
        assertContentEquals(
            hex(
                "ddaf35a193617abacc417349ae20413112e6fa4e89a97ea2" +
                    "0a9eeee64b55d39a2192992a274fc1a836ba3c23a3feebbd" +
                    "454d4423643ce80e2a9ac94fa54ca49f",
            ),
            Sha512.digest("abc".encodeToByteArray()),
        )
    }

    @Test
    fun sha512TruncatedMatchesKnownVectors() {
        assertContentEquals(
            hex("6ed0dd02806fa89e25de060c19d3ac86cabb87d6a0ddd05c333b84f4"),
            Sha512b224.digest(ByteArray(0)),
        )
        assertContentEquals(
            hex("4634270f707b6a54daae7530460842e20e37ed265ceee9a43e8924aa"),
            Sha512b224.digest("abc".encodeToByteArray()),
        )
        assertContentEquals(
            hex("c672b8d1ef56ed28ab87c3622c5114069bdd3ad7b8f9737498d0c01ecef0967a"),
            Sha512b256.digest(ByteArray(0)),
        )
        assertContentEquals(
            hex("53048e2681941ef99b2e29b76b4c7dabe4c2d0c634fc6d46e0e2f13107e7af23"),
            Sha512b256.digest("abc".encodeToByteArray()),
        )
    }

    @Test
    fun streamingUpdatesMatchOneShot() {
        val message = "the quick brown fox jumps over the lazy dog".encodeToByteArray()
        val sha256 = Sha256()
        val sha512 = Sha512()

        for (byte in message) {
            sha256.update(byteArrayOf(byte))
            sha512.update(byteArrayOf(byte))
        }

        assertContentEquals(Sha256.digest(message), sha256.finalize())
        assertContentEquals(Sha512.digest(message), sha512.finalize())
    }

    @Test
    fun finalizeResetReusesHasher() {
        val sha256 = Sha256()
        sha256.update("abc".encodeToByteArray())
        val sha256First = sha256.finalizeReset()
        sha256.update(ByteArray(0))
        assertContentEquals(Sha256.digest("abc".encodeToByteArray()), sha256First)
        assertContentEquals(Sha256.digest(ByteArray(0)), sha256.finalize())

        val sha512 = Sha512()
        sha512.update("abc".encodeToByteArray())
        val sha512First = sha512.finalizeReset()
        sha512.update(ByteArray(0))
        assertContentEquals(Sha512.digest("abc".encodeToByteArray()), sha512First)
        assertContentEquals(Sha512.digest(ByteArray(0)), sha512.finalize())
    }

    @Test
    fun metadataMatchesAlgorithmShape() {
        assertEquals(64, Sha224().blockSize)
        assertEquals(28, Sha224().outputSize)
        assertEquals(64, Sha256().blockSize)
        assertEquals(32, Sha256().outputSize)
        assertEquals(128, Sha384().blockSize)
        assertEquals(48, Sha384().outputSize)
        assertEquals(128, Sha512().blockSize)
        assertEquals(64, Sha512().outputSize)
        assertEquals(128, Sha512b224().blockSize)
        assertEquals(28, Sha512b224().outputSize)
        assertEquals(128, Sha512b256().blockSize)
        assertEquals(32, Sha512b256().outputSize)
    }
}
