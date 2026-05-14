// port-lint: source src/sha256.rs
package io.github.kotlinmania.sha2.sha256

/* Upstream this file selects a `compress` implementation at compile time via
 * cfg_if! over `feature = "force-soft-compact"`, `feature = "force-soft"`,
 * `target_arch = "x86"`/`"x86_64"` (optionally paired with `feature = "asm"`),
 * `target_arch = "aarch64"` (paired with `feature = "asm"`),
 * `target_arch = "loongarch64"` (paired with `feature = "loongarch64_asm"`),
 * and a default arm that uses the soft implementation. Kotlin Multiplatform
 * common code resolves one implementation that runs across every target;
 * the soft implementation is the only one whose primitives are reachable
 * from common code, so it is the choice here. The other backends remain
 * ported as a `Soft.compress` delegate so that this file's compile-time
 * dispatch table maps 1:1 onto its Kotlin counterpart.
 */

/** Raw SHA-256 compression function.
 *
 *  This is a low-level "hazmat" API which provides direct access to the core
 *  functionality of SHA-256.
 */
fun compress256(state: UIntArray, blocks: Array<ByteArray>) {
    // SAFETY note from upstream: `GenericArray<u8, U64>` and `[u8; 64]` have
    // exactly the same memory layout. The Kotlin port takes `ByteArray`
    // blocks directly and therefore needs no reinterpretation.
    Soft.compress(state, blocks)
}
