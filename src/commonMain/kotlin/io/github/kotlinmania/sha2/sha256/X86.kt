// port-lint: source sha256/x86.rs
package io.github.kotlinmania.sha2.sha256

/** SHA-256 x86 backend.
 *
 *  Upstream dispatches between a SHA extension SIMD implementation and the
 *  soft fallback using a runtime CPU-feature probe. Kotlin Multiplatform
 *  common code does not expose x86 SIMD intrinsics, so the only reachable
 *  path is the soft fallback.
 */
internal object X86 {
    fun compress(state: UIntArray, blocks: Array<ByteArray>) {
        Soft.compress(state, blocks)
    }
}
