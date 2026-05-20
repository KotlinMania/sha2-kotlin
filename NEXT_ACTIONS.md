# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 13/15 (86.7%)
- **Function parity:** 36/70 matched (target 46) — 51.4%
- **Class/type parity:** 2/10 matched (target 12) — 20.0%
- **Combined symbol parity:** 38/80 matched (target 58) — 47.5%
- **Average inline-code cosine:** 0.45 (function body across 13 matched files)
- **Average documentation cosine:** 0.57 (doc text across 13 matched files)
- **Cheat-zeroed Files:** 0
- **Critical Issues:** 7 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. sha512.x86

- **Target:** `sha512.X86`
- **Similarity:** 0.06
- **Dependents:** 0
- **Priority Score:** 151609.4
- **Functions:** 1/13 matched (target 1)
- **Missing functions:** `sha512_compress_x86_64_avx2`, `sha512_compress_x86_64_avx`, `load_data_avx`, `load_data_avx2`, `rounds_0_63_avx`, `rounds_0_63_avx2`, `rounds_64_79`, `process_second_block`, `sha_round`, `accumulate_state`, `cast_ms`, `cast_rs`
- **Types:** 0/3 matched (target 1)
- **Missing types:** `State`, `MsgSchedule`, `RoundStates`

### 2. sha256.aarch64

- **Target:** `sha256.Aarch64 [PROVENANCE-FALLBACK]`
- **Similarity:** 0.14
- **Dependents:** 0
- **Priority Score:** 50608.6
- **Functions:** 1/6 matched (target 1)
- **Missing functions:** `sha256_compress`, `vsha256hq_u32`, `vsha256h2q_u32`, `vsha256su0q_u32`, `vsha256su1q_u32`
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/sha256/aarch64.rs` vs expected `sha256/aarch64.rs`
- **Proposed provenance header:** `// port-lint: source sha256/aarch64.rs` (current: `// port-lint: source src/sha256/aarch64.rs`)
- **Lint issues:** 1

### 3. sha512.aarch64

- **Target:** `sha512.Aarch64 [PROVENANCE-FALLBACK]`
- **Similarity:** 0.14
- **Dependents:** 0
- **Priority Score:** 50608.6
- **Functions:** 1/6 matched (target 1)
- **Missing functions:** `sha512_compress`, `vsha512hq_u64`, `vsha512h2q_u64`, `vsha512su0q_u64`, `vsha512su1q_u64`
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/sha512/aarch64.rs` vs expected `sha512/aarch64.rs`
- **Proposed provenance header:** `// port-lint: source sha512/aarch64.rs` (current: `// port-lint: source src/sha512/aarch64.rs`)
- **Lint issues:** 1

### 4. sha256.x86

- **Target:** `sha256.X86`
- **Similarity:** 0.27
- **Dependents:** 0
- **Priority Score:** 20307.3
- **Functions:** 1/3 matched (target 1)
- **Missing functions:** `schedule`, `digest_blocks`
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 5. sha256.soft

- **Target:** `sha256.Soft [PROVENANCE-FALLBACK]`
- **Similarity:** 0.61
- **Dependents:** 0
- **Priority Score:** 1403.9
- **Functions:** 14/14 matched (target 19)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/sha256/soft.rs` vs expected `sha256/soft.rs`
- **Proposed provenance header:** `// port-lint: source sha256/soft.rs` (current: `// port-lint: source src/sha256/soft.rs`)
- **Lint issues:** 1

### 6. sha512.soft

- **Target:** `sha512.Soft [PROVENANCE-FALLBACK]`
- **Similarity:** 0.52
- **Dependents:** 0
- **Priority Score:** 804.8
- **Functions:** 8/8 matched (target 13)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/sha512/soft.rs` vs expected `sha512/soft.rs`
- **Proposed provenance header:** `// port-lint: source sha512/soft.rs` (current: `// port-lint: source src/sha512/soft.rs`)
- **Lint issues:** 1

### 7. sha512.soft_compact

- **Target:** `sha512.SoftCompact [PROVENANCE-FALLBACK]`
- **Similarity:** 0.71
- **Dependents:** 0
- **Priority Score:** 302.9
- **Functions:** 3/3 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/sha512/soft_compact.rs` vs expected `sha512/soft_compact.rs`
- **Proposed provenance header:** `// port-lint: source sha512/soft_compact.rs` (current: `// port-lint: source src/sha512/soft_compact.rs`)
- **Lint issues:** 1

### 8. sha256.soft_compact

- **Target:** `sha256.SoftCompact [PROVENANCE-FALLBACK]`
- **Similarity:** 0.73
- **Dependents:** 0
- **Priority Score:** 302.7
- **Functions:** 3/3 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/sha256/soft_compact.rs` vs expected `sha256/soft_compact.rs`
- **Proposed provenance header:** `// port-lint: source sha256/soft_compact.rs` (current: `// port-lint: source src/sha256/soft_compact.rs`)
- **Lint issues:** 1

### 9. consts

- **Target:** `sha2.Consts [PROVENANCE-FALLBACK]`
- **Similarity:** 1.00
- **Dependents:** 0
- **Priority Score:** 200.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 2/2 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/consts.rs` vs expected `consts.rs`
- **Proposed provenance header:** `// port-lint: source consts.rs` (current: `// port-lint: source src/consts.rs`)
- **Lint issues:** 1

### 10. sha512.loongarch64_asm

- **Target:** `sha512.Loongarch64Asm [PROVENANCE-FALLBACK]`
- **Similarity:** 0.06
- **Dependents:** 0
- **Priority Score:** 109.4
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/sha512/loongarch64_asm.rs` vs expected `sha512/loongarch64_asm.rs`
- **Proposed provenance header:** `// port-lint: source sha512/loongarch64_asm.rs` (current: `// port-lint: source src/sha512/loongarch64_asm.rs`)
- **Lint issues:** 1

### 11. sha256.loongarch64_asm

- **Target:** `sha256.Loongarch64Asm [PROVENANCE-FALLBACK]`
- **Similarity:** 0.07
- **Dependents:** 0
- **Priority Score:** 109.3
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/sha256/loongarch64_asm.rs` vs expected `sha256/loongarch64_asm.rs`
- **Proposed provenance header:** `// port-lint: source sha256/loongarch64_asm.rs` (current: `// port-lint: source src/sha256/loongarch64_asm.rs`)
- **Lint issues:** 1

### 12. sha256

- **Target:** `sha256.Sha256`
- **Similarity:** 0.74
- **Dependents:** 0
- **Priority Score:** 102.6
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_

### 13. sha512

- **Target:** `sha512.Sha512`
- **Similarity:** 0.74
- **Dependents:** 0
- **Priority Score:** 102.6
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

## Next Commands

```bash
# Initialize task queue for systematic porting
cd tools/ast_distance
./ast_distance --init-tasks ../../tmp/sha2/src rust ../../src/commonMain/kotlin/io/github/kotlinmania/sha2 kotlin tasks.json ../../AGENTS.md

# Get next high-priority task
./ast_distance --assign tasks.json <agent-id>
```
## Reexport / Wiring Modules

These files match `reexport_modules` patterns in `.ast_distance_config.json`. They are filtered out of
normal priority and missing-file ladders because they are wiring
modules, not direct logic ports. Consult them for call-site routing;
do not treat them as the next implementation target by default.

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `lib` | `Lib` | 0 | `lib.rs` | `Lib.kt` |

