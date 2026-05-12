// port-lint: source src/sha256/x86.rs
package io.github.kotlinmania.sha2.sha256

/** SHA-256 `x86`/`x86_64` backend.
 *
 *  Upstream this dispatches between a SHA-NI implementation using SSE2,
 *  SSSE3, SSE4.1 and the `_mm_sha256rnds2_epu32`, `_mm_sha256msg1_epu32`,
 *  `_mm_sha256msg2_epu32` intrinsics, and the soft fallback, based on a
 *  runtime `sha` CPU-feature probe. Kotlin Multiplatform common code does
 *  not expose x86 SIMD intrinsics, so the only reachable path is the soft
 *  fallback.
 */
internal object X86 {
    fun compress(state: UIntArray, blocks: Array<ByteArray>) {
        Soft.compress(state, blocks)
    }
}
