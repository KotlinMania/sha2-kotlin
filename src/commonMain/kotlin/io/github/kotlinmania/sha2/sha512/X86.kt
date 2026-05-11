// port-lint: source src/sha512/x86.rs
package io.github.kotlinmania.sha2.sha512

/** SHA-512 `x86`/`x86_64` backend.
 *
 *  Upstream this dispatches between an AVX2 implementation built on the
 *  `__m128i`/`__m256i` intrinsics and the soft fallback, based on a runtime
 *  `avx2` CPU-feature probe. Kotlin Multiplatform common code does not
 *  expose x86 SIMD intrinsics, so the only reachable path is the soft
 *  fallback.
 */
internal object X86 {
    fun compress(state: ULongArray, blocks: Array<ByteArray>) {
        Soft.compress(state, blocks)
    }
}
