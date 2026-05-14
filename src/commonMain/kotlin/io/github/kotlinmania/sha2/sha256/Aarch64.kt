// port-lint: source src/sha256/aarch64.rs
package io.github.kotlinmania.sha2.sha256

/** SHA-256 `aarch64` backend.
 *
 *  Implementation adapted from mbedtls.
 *
 *  Upstream this dispatches between an ARMv8 SHA-2 extension implementation
 *  (`SHA256H`, `SHA256H2`, `SHA256SU0`, `SHA256SU1` via inline assembly)
 *  and the soft fallback, based on a runtime `sha2` CPU-feature probe.
 *  Kotlin Multiplatform common code does not expose inline assembly or the
 *  ARMv8 SHA-2 extension intrinsics, so the only reachable path is the
 *  soft fallback.
 */
internal object Aarch64 {
    fun compress(state: UIntArray, blocks: Array<ByteArray>) {
        Soft.compress(state, blocks)
    }
}
