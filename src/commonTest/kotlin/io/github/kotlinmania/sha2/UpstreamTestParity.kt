// port-lint: tests tests/mod.rs
package io.github.kotlinmania.sha2

import kotlin.test.Test
import kotlin.test.assertNull
import kotlin.test.assertContentEquals

/**
 * Test parity with upstream Rust `tests/mod.rs`.
 *
 * The upstream test file uses `digest::dev::fixed_reset_test` against
 * binary .blb test vector files, plus `feed_rand_16mib` for the 16 MiB
 * random-data regression tests. This port reimplements those helpers
 * locally since sha2-kotlin is self-contained and does not depend on
 * digest-kotlin.
 */
class UpstreamTestParity {
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

    private interface HasherAdapter {
        fun update(data: ByteArray)
        fun finalize(): ByteArray
        fun reset()
    }

    private fun sha224Adapter(): HasherAdapter {
        val h = Sha224()
        return object : HasherAdapter {
            override fun update(data: ByteArray) = h.update(data)
            override fun finalize(): ByteArray = h.finalize()
            override fun reset() = h.reset()
        }
    }

    private fun sha256Adapter(): HasherAdapter {
        val h = Sha256()
        return object : HasherAdapter {
            override fun update(data: ByteArray) = h.update(data)
            override fun finalize(): ByteArray = h.finalize()
            override fun reset() = h.reset()
        }
    }

    private fun sha384Adapter(): HasherAdapter {
        val h = Sha384()
        return object : HasherAdapter {
            override fun update(data: ByteArray) = h.update(data)
            override fun finalize(): ByteArray = h.finalize()
            override fun reset() = h.reset()
        }
    }

    private fun sha512Adapter(): HasherAdapter {
        val h = Sha512()
        return object : HasherAdapter {
            override fun update(data: ByteArray) = h.update(data)
            override fun finalize(): ByteArray = h.finalize()
            override fun reset() = h.reset()
        }
    }

    private fun sha512b224Adapter(): HasherAdapter {
        val h = Sha512b224()
        return object : HasherAdapter {
            override fun update(data: ByteArray) = h.update(data)
            override fun finalize(): ByteArray = h.finalize()
            override fun reset() = h.reset()
        }
    }

    private fun sha512b256Adapter(): HasherAdapter {
        val h = Sha512b256()
        return object : HasherAdapter {
            override fun update(data: ByteArray) = h.update(data)
            override fun finalize(): ByteArray = h.finalize()
            override fun reset() = h.reset()
        }
    }

    /**
     * Test a fixed-output resettable digest implementation.
     *
     * Returns a failure description on the first failing check, or `null` on success.
     * Tests whole-message, reset, and chunked ingestion — matching the
     * `fixed_reset_test` pattern from the upstream `digest::dev` module.
     */
    private fun fixedResetTest(
        input: ByteArray,
        output: ByteArray,
        create: () -> HasherAdapter,
    ): String? {
        val hasher = create()
        hasher.update(input)
        if (!hasher.finalize().contentEquals(output)) return "whole message"

        hasher.reset()
        hasher.update(input)
        if (!hasher.finalize().contentEquals(output)) return "whole message after reset"

        for (n in 1 until minOf(17, input.size + 1)) {
            hasher.reset()
            var offset = 0
            while (offset < input.size) {
                val end = minOf(offset + n, input.size)
                hasher.update(input.copyOfRange(offset, end))
                offset = end
            }
            if (!hasher.finalize().contentEquals(output)) return "message in chunks of $n"
        }
        return null
    }

    private data class TestVector(val input: ByteArray, val output: ByteArray) {
        override fun equals(other: Any?): Boolean =
            this === other ||
                (other is TestVector && input.contentEquals(other.input) && output.contentEquals(other.output))

        override fun hashCode(): Int = input.contentHashCode() xor output.contentHashCode()
    }

    // Upstream .blb test vectors for each algorithm (3 vectors per algorithm)
    private val sha224Vectors = listOf(
        TestVector(ByteArray(0), hex("d14a028c2a3a2bc9476102bb288234c415a2b01f828ea62ac5b3e42f")),
        TestVector("The quick brown fox jumps over the lazy dog".encodeToByteArray(), hex("730e109bd7a8a32b1cb9d9a09aa2325d2430587ddbc0c38bad911525")),
        TestVector("The quick brown fox jumps over the lazy dog.".encodeToByteArray(), hex("619cba8e8e05826e9b8c519c0a5c68f4fb653e8a3d8aa04bb2c8cd4c")),
    )

    private val sha256Vectors = listOf(
        TestVector(ByteArray(0), hex("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855")),
        TestVector("The quick brown fox jumps over the lazy dog".encodeToByteArray(), hex("d7a8fbb307d7809469ca9abcb0082e4f8d5651e46d3cdb762d02d0bf37c9e592")),
        TestVector("The quick brown fox jumps over the lazy dog.".encodeToByteArray(), hex("ef537f25c895bfa782526529a9b63d97aa631564d5d789c2b765448c8635fb6c")),
    )

    private val sha384Vectors = listOf(
        TestVector(ByteArray(0), hex("38b060a751ac96384cd9327eb1b1e36a21fdb71114be07434c0cc7bf63f6e1da274edebfe76f65fbd51ad2f14898b95b")),
        TestVector("The quick brown fox jumps over the lazy dog".encodeToByteArray(), hex("ca737f1014a48f4c0b6dd43cb177b0afd9e5169367544c494011e3317dbf9a509cb1e5dc1e85a941bbee3d7f2afbc9b1")),
        TestVector("The quick brown fox jumps over the lazy dog.".encodeToByteArray(), hex("ed892481d8272ca6df370bf706e4d7bc1b5739fa2177aae6c50e946678718fc67a7af2819a021c2fc34e91bdb63409d7")),
    )

    private val sha512Vectors = listOf(
        TestVector(ByteArray(0), hex("cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e")),
        TestVector("The quick brown fox jumps over the lazy dog".encodeToByteArray(), hex("07e547d9586f6a73f73fbac0435ed76951218fb7d0c8d788a309d785436bbb642e93a252a954f23912547d1e8a3b5ed6e1bfd7097821233fa0538f3db854fee6")),
        TestVector("The quick brown fox jumps over the lazy dog.".encodeToByteArray(), hex("91ea1245f20d46ae9a037a989f54f1f790f0a47607eeb8a14d12890cea77a1bbc6c7ed9cf205e67b7f2b8fd4c7dfd3a7a8617e45f3c463d481c7e586c39ac1ed")),
    )

    private val sha512_224Vectors = listOf(
        TestVector(ByteArray(0), hex("6ed0dd02806fa89e25de060c19d3ac86cabb87d6a0ddd05c333b84f4")),
        TestVector("The quick brown fox jumps over the lazy dog".encodeToByteArray(), hex("944cd2847fb54558d4775db0485a50003111c8e5daa63fe722c6aa37")),
        TestVector("The quick brown fox jumps over the lazy dog.".encodeToByteArray(), hex("6d6a9279495ec4061769752e7ff9c68b6b0b3c5a281b7917ce0572de")),
    )

    private val sha512_256Vectors = listOf(
        TestVector(ByteArray(0), hex("c672b8d1ef56ed28ab87c3622c5114069bdd3ad7b8f9737498d0c01ecef0967a")),
        TestVector("The quick brown fox jumps over the lazy dog".encodeToByteArray(), hex("dd9d67b371519c339ed8dbd25af90e976a1eeefd4ad3d889005e532fc5bef04d")),
        TestVector("The quick brown fox jumps over the lazy dog.".encodeToByteArray(), hex("1546741840f8a492b959d9b8b2344b9b0eb51b004bba35c0aebaac86d45264c3")),
    )

    @Test
    fun sha224Main() {
        for ((i, v) in sha224Vectors.withIndex()) {
            val desc = fixedResetTest(v.input, v.output) { sha224Adapter() }
            assertNull(desc, "sha224 vector $i failed: $desc")
        }
    }

    @Test
    fun sha256Main() {
        for ((i, v) in sha256Vectors.withIndex()) {
            val desc = fixedResetTest(v.input, v.output) { sha256Adapter() }
            assertNull(desc, "sha256 vector $i failed: $desc")
        }
    }

    @Test
    fun sha512_224Main() {
        for ((i, v) in sha512_224Vectors.withIndex()) {
            val desc = fixedResetTest(v.input, v.output) { sha512b224Adapter() }
            assertNull(desc, "sha512_224 vector $i failed: $desc")
        }
    }

    @Test
    fun sha512_256Main() {
        for ((i, v) in sha512_256Vectors.withIndex()) {
            val desc = fixedResetTest(v.input, v.output) { sha512b256Adapter() }
            assertNull(desc, "sha512_256 vector $i failed: $desc")
        }
    }

    @Test
    fun sha384Main() {
        for ((i, v) in sha384Vectors.withIndex()) {
            val desc = fixedResetTest(v.input, v.output) { sha384Adapter() }
            assertNull(desc, "sha384 vector $i failed: $desc")
        }
    }

    @Test
    fun sha512Main() {
        for ((i, v) in sha512Vectors.withIndex()) {
            val desc = fixedResetTest(v.input, v.output) { sha512Adapter() }
            assertNull(desc, "sha512 vector $i failed: $desc")
        }
    }

    // --- feed_rand_16mib tests ---
    // Upstream uses digest::dev::feed_rand_16mib with an XorShiftRng.
    // The RNG and feed pattern are reimplemented here to match exactly.

    private class XorShiftRng(
        private var x: UInt,
        private var y: UInt,
        private var z: UInt,
        private var w: UInt,
    ) {
        fun fill(buf: ByteArray) {
            var index = 0
            while (index + 3 < buf.size) {
                val next = nextU32()
                buf[index] = (next and 0xFFu).toByte()
                buf[index + 1] = ((next shr 8) and 0xFFu).toByte()
                buf[index + 2] = ((next shr 16) and 0xFFu).toByte()
                buf[index + 3] = ((next shr 24) and 0xFFu).toByte()
                index += 4
            }
        }

        fun nextU32(): UInt {
            val t = x xor (x shl 11)
            x = y
            y = z
            z = w
            w = w xor (w shr 19) xor (t xor (t shr 8))
            return w
        }
    }

    private fun feedRand16mib(hasher: HasherAdapter) {
        val buf = ByteArray(1024)
        val rng = XorShiftRng(
            x = 0x07873B4Au,
            y = 0xFAAB8FFEu,
            z = 0x1745980Fu,
            w = 0xB0ADB4F3u,
        )
        val n = 16 * (1 shl 20) / buf.size
        repeat(n) {
            rng.fill(buf)
            hasher.update(buf)
            hasher.update(byteArrayOf(42))
        }
    }

    @Test
    fun sha256Rand() {
        if (!isHeavyTestSupported()) return
        val hasher = sha256Adapter()
        feedRand16mib(hasher)
        assertContentEquals(
            hex("45f51fead87328fe837a86f4f1ac0eb15116ab1473adc0423ef86c62eb2320c7"),
            hasher.finalize(),
        )
    }

    @Test
    fun sha512Rand() {
        if (!isHeavyTestSupported()) return
        val hasher = sha512Adapter()
        feedRand16mib(hasher)
        assertContentEquals(
            hex(
                "9084d75a7c0721541d737b6171eb465dc9ba08a119a182a8508484aa27a176cd" +
                    "e7c2103b108393eb024493ced4aac56be6f57222cac41b801f11494886264997",
            ),
            hasher.finalize(),
        )
    }
}