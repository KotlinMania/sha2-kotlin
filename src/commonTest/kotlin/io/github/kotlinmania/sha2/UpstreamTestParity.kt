// port-lint: tests tests/mod.rs
@file:OptIn(ExperimentalStdlibApi::class)

package io.github.kotlinmania.sha2

import io.github.kotlinmania.digest.dev.feedRand16mib
import io.github.kotlinmania.digest.dev.fixedResetTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertNull

/**
 * Test parity with upstream Rust `tests/mod.rs`.
 *
 * Uses `digest::dev::fixed_reset_test` and `feed_rand_16mib` from `digest-kotlin`.
 */
class UpstreamTestParity {
    private data class TestVector(
        val input: ByteArray,
        val output: ByteArray,
    ) {
        override fun equals(other: Any?): Boolean =
            this === other ||
                (other is TestVector && input.contentEquals(other.input) && output.contentEquals(other.output))

        override fun hashCode(): Int = input.contentHashCode() xor output.contentHashCode()
    }

    // Upstream .blb test vectors for each algorithm (3 vectors per algorithm)
    private val sha224Vectors =
        listOf(
            TestVector(ByteArray(0), "d14a028c2a3a2bc9476102bb288234c415a2b01f828ea62ac5b3e42f".hexToByteArray()),
            TestVector("The quick brown fox jumps over the lazy dog".encodeToByteArray(), "730e109bd7a8a32b1cb9d9a09aa2325d2430587ddbc0c38bad911525".hexToByteArray()),
            TestVector("The quick brown fox jumps over the lazy dog.".encodeToByteArray(), "619cba8e8e05826e9b8c519c0a5c68f4fb653e8a3d8aa04bb2c8cd4c".hexToByteArray()),
        )

    private val sha256Vectors =
        listOf(
            TestVector(ByteArray(0), "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855".hexToByteArray()),
            TestVector("The quick brown fox jumps over the lazy dog".encodeToByteArray(), "d7a8fbb307d7809469ca9abcb0082e4f8d5651e46d3cdb762d02d0bf37c9e592".hexToByteArray()),
            TestVector("The quick brown fox jumps over the lazy dog.".encodeToByteArray(), "ef537f25c895bfa782526529a9b63d97aa631564d5d789c2b765448c8635fb6c".hexToByteArray()),
        )

    private val sha384Vectors =
        listOf(
            TestVector(ByteArray(0), "38b060a751ac96384cd9327eb1b1e36a21fdb71114be07434c0cc7bf63f6e1da274edebfe76f65fbd51ad2f14898b95b".hexToByteArray()),
            TestVector("The quick brown fox jumps over the lazy dog".encodeToByteArray(), "ca737f1014a48f4c0b6dd43cb177b0afd9e5169367544c494011e3317dbf9a509cb1e5dc1e85a941bbee3d7f2afbc9b1".hexToByteArray()),
            TestVector("The quick brown fox jumps over the lazy dog.".encodeToByteArray(), "ed892481d8272ca6df370bf706e4d7bc1b5739fa2177aae6c50e946678718fc67a7af2819a021c2fc34e91bdb63409d7".hexToByteArray()),
        )

    private val sha512Vectors =
        listOf(
            TestVector(ByteArray(0), "cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e".hexToByteArray()),
            TestVector("The quick brown fox jumps over the lazy dog".encodeToByteArray(), "07e547d9586f6a73f73fbac0435ed76951218fb7d0c8d788a309d785436bbb642e93a252a954f23912547d1e8a3b5ed6e1bfd7097821233fa0538f3db854fee6".hexToByteArray()),
            TestVector("The quick brown fox jumps over the lazy dog.".encodeToByteArray(), "91ea1245f20d46ae9a037a989f54f1f790f0a47607eeb8a14d12890cea77a1bbc6c7ed9cf205e67b7f2b8fd4c7dfd3a7a8617e45f3c463d481c7e586c39ac1ed".hexToByteArray()),
        )

    private val sha512_224Vectors =
        listOf(
            TestVector(ByteArray(0), "6ed0dd02806fa89e25de060c19d3ac86cabb87d6a0ddd05c333b84f4".hexToByteArray()),
            TestVector("The quick brown fox jumps over the lazy dog".encodeToByteArray(), "944cd2847fb54558d4775db0485a50003111c8e5daa63fe722c6aa37".hexToByteArray()),
            TestVector("The quick brown fox jumps over the lazy dog.".encodeToByteArray(), "6d6a9279495ec4061769752e7ff9c68b6b0b3c5a281b7917ce0572de".hexToByteArray()),
        )

    private val sha512_256Vectors =
        listOf(
            TestVector(ByteArray(0), "c672b8d1ef56ed28ab87c3622c5114069bdd3ad7b8f9737498d0c01ecef0967a".hexToByteArray()),
            TestVector("The quick brown fox jumps over the lazy dog".encodeToByteArray(), "dd9d67b371519c339ed8dbd25af90e976a1eeefd4ad3d889005e532fc5bef04d".hexToByteArray()),
            TestVector("The quick brown fox jumps over the lazy dog.".encodeToByteArray(), "1546741840f8a492b959d9b8b2344b9b0eb51b004bba35c0aebaac86d45264c3".hexToByteArray()),
        )

    @Test
    fun sha224Main() {
        for ((i, v) in sha224Vectors.withIndex()) {
            val desc = fixedResetTest(v.input, v.output, ::Sha224, Sha224::copy)
            assertNull(desc, "sha224 vector $i failed: $desc")
        }
    }

    @Test
    fun sha256Main() {
        for ((i, v) in sha256Vectors.withIndex()) {
            val desc = fixedResetTest(v.input, v.output, ::Sha256, Sha256::copy)
            assertNull(desc, "sha256 vector $i failed: $desc")
        }
    }

    @Test
    fun sha512_224Main() {
        for ((i, v) in sha512_224Vectors.withIndex()) {
            val desc = fixedResetTest(v.input, v.output, ::Sha512_224, Sha512_224::copy)
            assertNull(desc, "sha512_224 vector $i failed: $desc")
        }
    }

    @Test
    fun sha512_256Main() {
        for ((i, v) in sha512_256Vectors.withIndex()) {
            val desc = fixedResetTest(v.input, v.output, ::Sha512_256, Sha512_256::copy)
            assertNull(desc, "sha512_256 vector $i failed: $desc")
        }
    }

    @Test
    fun sha384Main() {
        for ((i, v) in sha384Vectors.withIndex()) {
            val desc = fixedResetTest(v.input, v.output, ::Sha384, Sha384::copy)
            assertNull(desc, "sha384 vector $i failed: $desc")
        }
    }

    @Test
    fun sha512Main() {
        for ((i, v) in sha512Vectors.withIndex()) {
            val desc = fixedResetTest(v.input, v.output, ::Sha512, Sha512::copy)
            assertNull(desc, "sha512 vector $i failed: $desc")
        }
    }

    @Test
    fun sha256Rand() {
        if (!isHeavyTestSupported()) return
        val hasher = Sha256.new()
        feedRand16mib(hasher)
        assertContentEquals(
            "45f51fead87328fe837a86f4f1ac0eb15116ab1473adc0423ef86c62eb2320c7".hexToByteArray(),
            hasher.finalize(),
        )
    }

    @Test
    fun sha512Rand() {
        if (!isHeavyTestSupported()) return
        val hasher = Sha512.new()
        feedRand16mib(hasher)
        assertContentEquals(
            (
                "9084d75a7c0721541d737b6171eb465dc9ba08a119a182a8508484aa27a176cd" +
                    "e7c2103b108393eb024493ced4aac56be6f57222cac41b801f11494886264997"
            ).hexToByteArray(),
            hasher.finalize(),
        )
    }
}
