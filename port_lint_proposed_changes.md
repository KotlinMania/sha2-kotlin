# port-lint Proposed Changes

**Generated:** 2026-05-19
**Source:** tmp/sha2/src
**Target:** src/commonMain/kotlin/io/github/kotlinmania/sha2

These are review proposals only. They are emitted when a Rust -> Kotlin pair matches only after fallback normalization, so the existing `port-lint` header is not an exact provenance match.

| Target file | Current header | Proposed header | Source path | Reason |
|-------------|----------------|-----------------|-------------|--------|
| `src/commonMain/kotlin/io/github/kotlinmania/sha2/sha256/Aarch64.kt` | `// port-lint: source src/sha256/aarch64.rs` | `// port-lint: source sha256/aarch64.rs` | `sha256/aarch64.rs` | `port-lint provenance header matched only after fallback normalization: 'src/sha256/aarch64.rs' vs expected 'sha256/aarch64.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/sha2/sha512/Aarch64.kt` | `// port-lint: source src/sha512/aarch64.rs` | `// port-lint: source sha512/aarch64.rs` | `sha512/aarch64.rs` | `port-lint provenance header matched only after fallback normalization: 'src/sha512/aarch64.rs' vs expected 'sha512/aarch64.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/sha2/sha256/Soft.kt` | `// port-lint: source src/sha256/soft.rs` | `// port-lint: source sha256/soft.rs` | `sha256/soft.rs` | `port-lint provenance header matched only after fallback normalization: 'src/sha256/soft.rs' vs expected 'sha256/soft.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/sha2/sha512/Soft.kt` | `// port-lint: source src/sha512/soft.rs` | `// port-lint: source sha512/soft.rs` | `sha512/soft.rs` | `port-lint provenance header matched only after fallback normalization: 'src/sha512/soft.rs' vs expected 'sha512/soft.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/sha2/sha512/SoftCompact.kt` | `// port-lint: source src/sha512/soft_compact.rs` | `// port-lint: source sha512/soft_compact.rs` | `sha512/soft_compact.rs` | `port-lint provenance header matched only after fallback normalization: 'src/sha512/soft_compact.rs' vs expected 'sha512/soft_compact.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/sha2/sha256/SoftCompact.kt` | `// port-lint: source src/sha256/soft_compact.rs` | `// port-lint: source sha256/soft_compact.rs` | `sha256/soft_compact.rs` | `port-lint provenance header matched only after fallback normalization: 'src/sha256/soft_compact.rs' vs expected 'sha256/soft_compact.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/sha2/Consts.kt` | `// port-lint: source src/consts.rs` | `// port-lint: source consts.rs` | `consts.rs` | `port-lint provenance header matched only after fallback normalization: 'src/consts.rs' vs expected 'consts.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/sha2/sha512/Loongarch64Asm.kt` | `// port-lint: source src/sha512/loongarch64_asm.rs` | `// port-lint: source sha512/loongarch64_asm.rs` | `sha512/loongarch64_asm.rs` | `port-lint provenance header matched only after fallback normalization: 'src/sha512/loongarch64_asm.rs' vs expected 'sha512/loongarch64_asm.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/sha2/sha256/Loongarch64Asm.kt` | `// port-lint: source src/sha256/loongarch64_asm.rs` | `// port-lint: source sha256/loongarch64_asm.rs` | `sha256/loongarch64_asm.rs` | `port-lint provenance header matched only after fallback normalization: 'src/sha256/loongarch64_asm.rs' vs expected 'sha256/loongarch64_asm.rs'` |
