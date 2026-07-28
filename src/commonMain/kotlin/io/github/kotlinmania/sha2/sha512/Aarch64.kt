// port-lint: source sha512/aarch64.rs
package io.github.kotlinmania.sha2.sha512

/** SHA-512 `aarch64` backend.
 *
 *  Implementation adapted from mbedtls.
 *
 *  Upstream this dispatches between an ARMv8 SHA-3 extension implementation
 *  (using inline assembly that issues the `sha3` instructions) and the soft
 *  fallback, based on a runtime `sha3` CPU-feature probe. Kotlin Multiplatform
 *  common code does not expose inline assembly or the ARMv8 SHA-3 extension
 *  intrinsics, so the only reachable path is the soft fallback.
 */
internal object Aarch64 {
    fun compress(state: ULongArray, blocks: Array<ByteArray>) {
        Soft.compress(state, blocks)
    }
}
