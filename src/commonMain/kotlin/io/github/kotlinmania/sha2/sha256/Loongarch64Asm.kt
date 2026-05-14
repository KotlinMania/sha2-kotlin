// port-lint: source src/sha256/loongarch64_asm.rs
package io.github.kotlinmania.sha2.sha256

/** LoongArch64 assembly backend.
 *
 *  Upstream this is a hand-written LoongArch64 inline-assembly compression
 *  loop. Kotlin Multiplatform common code does not emit inline assembly,
 *  so the only reachable path is the soft fallback.
 */
internal object Loongarch64Asm {
    fun compress(state: UIntArray, blocks: Array<ByteArray>) {
        if (blocks.isEmpty()) {
            return
        }
        Soft.compress(state, blocks)
    }
}
