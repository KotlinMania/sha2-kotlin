# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 15/15 (100.0%)
- **Function parity:** 39/65 matched (target 106) — 60.0%
- **Class/type parity:** 8/16 matched (target 22) — 50.0%
- **Combined symbol parity:** 47/81 matched (target 128) — 58.0%
- **Average inline-code cosine:** 0.41 (function body across 15 matched files)
- **Average documentation cosine:** 0.54 (doc text across 15 matched files)
- **Cheat-zeroed Files:** 1
- **Critical Issues:** 9 files with <0.60 function similarity

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

### 2. core_api

- **Target:** `sha2.CoreApi`
- **Similarity:** 0.38
- **Dependents:** 0
- **Priority Score:** 51006.2
- **Functions:** 3/5 matched (target 8)
- **Missing functions:** `write_alg_name`, `fmt`
- **Types:** 2/5 matched (target 2)
- **Missing types:** `BlockSize`, `BufferKind`, `OutputSize`

### 3. sha256.aarch64

- **Target:** `sha256.Aarch64`
- **Similarity:** 0.14
- **Dependents:** 0
- **Priority Score:** 50608.6
- **Functions:** 1/6 matched (target 1)
- **Missing functions:** `sha256_compress`, `vsha256hq_u32`, `vsha256h2q_u32`, `vsha256su0q_u32`, `vsha256su1q_u32`
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 4. sha512.aarch64

- **Target:** `sha512.Aarch64`
- **Similarity:** 0.14
- **Dependents:** 0
- **Priority Score:** 50608.6
- **Functions:** 1/6 matched (target 1)
- **Missing functions:** `sha512_compress`, `vsha512hq_u64`, `vsha512h2q_u64`, `vsha512su0q_u64`, `vsha512su1q_u64`
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 5. lib

- **Target:** `sha2.Sha2 [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 20610.0
- **Functions:** 0/0 matched (target 52)
- **Missing functions:** _none_
- **Types:** 4/6 matched (target 8)
- **Missing types:** `Sha512_224`, `Sha512_256`

### 6. sha256.x86

- **Target:** `sha256.X86`
- **Similarity:** 0.27
- **Dependents:** 0
- **Priority Score:** 20307.3
- **Functions:** 1/3 matched (target 1)
- **Missing functions:** `schedule`, `digest_blocks`
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 7. sha256.soft

- **Target:** `sha256.Soft`
- **Similarity:** 0.61
- **Dependents:** 0
- **Priority Score:** 1403.9
- **Functions:** 14/14 matched (target 19)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 8. sha512.soft

- **Target:** `sha512.Soft`
- **Similarity:** 0.52
- **Dependents:** 0
- **Priority Score:** 804.8
- **Functions:** 8/8 matched (target 13)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 9. sha512.soft_compact

- **Target:** `sha512.SoftCompact`
- **Similarity:** 0.71
- **Dependents:** 0
- **Priority Score:** 302.9
- **Functions:** 3/3 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 10. sha256.soft_compact

- **Target:** `sha256.SoftCompact`
- **Similarity:** 0.73
- **Dependents:** 0
- **Priority Score:** 302.7
- **Functions:** 3/3 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 11. consts

- **Target:** `sha2.Consts`
- **Similarity:** 1.00
- **Dependents:** 0
- **Priority Score:** 200.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 2/2 matched
- **Missing types:** _none_

### 12. sha512.loongarch64_asm

- **Target:** `sha512.Loongarch64Asm`
- **Similarity:** 0.06
- **Dependents:** 0
- **Priority Score:** 109.4
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 13. sha256.loongarch64_asm

- **Target:** `sha256.Loongarch64Asm`
- **Similarity:** 0.07
- **Dependents:** 0
- **Priority Score:** 109.3
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 14. sha256

- **Target:** `sha256.Sha256`
- **Similarity:** 0.74
- **Dependents:** 0
- **Priority Score:** 102.6
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_

### 15. sha512

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

