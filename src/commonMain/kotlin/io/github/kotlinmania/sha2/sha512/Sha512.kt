// port-lint: source sha512.rs
package io.github.kotlinmania.sha2.sha512

/* Upstream this file selects a compression implementation at compile time from
 * the soft compact, soft, x86, AArch64, LoongArch64, and default soft paths.
 * Kotlin Multiplatform common code resolves one implementation that runs
 * across every target; the soft implementation is the only backend whose
 * primitives are reachable from common code, so it is the choice here. The
 * other backend files remain ported as delegates so this file's dispatch table
 * maps onto its Kotlin counterpart.
 */

/** Raw SHA-512 compression function.
 *
 *  This is a low-level "hazmat" API which provides direct access to the core
 *  functionality of SHA-512.
 */
fun compress512(state: ULongArray, blocks: Array<ByteArray>) {
    // SAFETY note from upstream: `GenericArray<u8, U128>` and `[u8; 128]` have
    // exactly the same memory layout. The Kotlin port takes `ByteArray`
    // blocks directly and therefore needs no reinterpretation.
    Soft.compress(state, blocks)
}
