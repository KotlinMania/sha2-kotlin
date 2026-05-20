// port-lint: source sha512/x86.rs
package io.github.kotlinmania.sha2.sha512

/** SHA-512 x86 backend.
 *
 *  Upstream dispatches between an AVX2 SIMD implementation and the soft
 *  fallback using a runtime CPU-feature probe. Kotlin Multiplatform common
 *  code does not expose x86 SIMD intrinsics, so the only reachable path is
 *  the soft fallback.
 */
internal object X86 {
    fun compress(state: ULongArray, blocks: Array<ByteArray>) {
        Soft.compress(state, blocks)
    }
}
