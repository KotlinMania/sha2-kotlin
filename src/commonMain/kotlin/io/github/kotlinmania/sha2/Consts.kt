// port-lint: source src/consts.rs
package io.github.kotlinmania.sha2

internal const val STATE_LEN: Int = 8
internal const val BLOCK_LEN: Int = 16

internal typealias State256 = UIntArray
internal typealias State512 = ULongArray

/** Constants necessary for SHA-256 family of digests. */
internal val K32: UIntArray = uintArrayOf(
    0x428a2f98u, 0x71374491u, 0xb5c0fbcfu, 0xe9b5dba5u, 0x3956c25bu, 0x59f111f1u, 0x923f82a4u, 0xab1c5ed5u,
    0xd807aa98u, 0x12835b01u, 0x243185beu, 0x550c7dc3u, 0x72be5d74u, 0x80deb1feu, 0x9bdc06a7u, 0xc19bf174u,
    0xe49b69c1u, 0xefbe4786u, 0x0fc19dc6u, 0x240ca1ccu, 0x2de92c6fu, 0x4a7484aau, 0x5cb0a9dcu, 0x76f988dau,
    0x983e5152u, 0xa831c66du, 0xb00327c8u, 0xbf597fc7u, 0xc6e00bf3u, 0xd5a79147u, 0x06ca6351u, 0x14292967u,
    0x27b70a85u, 0x2e1b2138u, 0x4d2c6dfcu, 0x53380d13u, 0x650a7354u, 0x766a0abbu, 0x81c2c92eu, 0x92722c85u,
    0xa2bfe8a1u, 0xa81a664bu, 0xc24b8b70u, 0xc76c51a3u, 0xd192e819u, 0xd6990624u, 0xf40e3585u, 0x106aa070u,
    0x19a4c116u, 0x1e376c08u, 0x2748774cu, 0x34b0bcb5u, 0x391c0cb3u, 0x4ed8aa4au, 0x5b9cca4fu, 0x682e6ff3u,
    0x748f82eeu, 0x78a5636fu, 0x84c87814u, 0x8cc70208u, 0x90befffau, 0xa4506cebu, 0xbef9a3f7u, 0xc67178f2u,
)

/** Constants necessary for SHA-256 family of digests. */
internal val K32X4: Array<UIntArray> = arrayOf(
    uintArrayOf(K32[3], K32[2], K32[1], K32[0]),
    uintArrayOf(K32[7], K32[6], K32[5], K32[4]),
    uintArrayOf(K32[11], K32[10], K32[9], K32[8]),
    uintArrayOf(K32[15], K32[14], K32[13], K32[12]),
    uintArrayOf(K32[19], K32[18], K32[17], K32[16]),
    uintArrayOf(K32[23], K32[22], K32[21], K32[20]),
    uintArrayOf(K32[27], K32[26], K32[25], K32[24]),
    uintArrayOf(K32[31], K32[30], K32[29], K32[28]),
    uintArrayOf(K32[35], K32[34], K32[33], K32[32]),
    uintArrayOf(K32[39], K32[38], K32[37], K32[36]),
    uintArrayOf(K32[43], K32[42], K32[41], K32[40]),
    uintArrayOf(K32[47], K32[46], K32[45], K32[44]),
    uintArrayOf(K32[51], K32[50], K32[49], K32[48]),
    uintArrayOf(K32[55], K32[54], K32[53], K32[52]),
    uintArrayOf(K32[59], K32[58], K32[57], K32[56]),
    uintArrayOf(K32[63], K32[62], K32[61], K32[60]),
)

/** Constants necessary for SHA-512 family of digests. */
internal val K64: ULongArray = ulongArrayOf(
    0x428a2f98d728ae22uL, 0x7137449123ef65cduL, 0xb5c0fbcfec4d3b2fuL, 0xe9b5dba58189dbbcuL,
    0x3956c25bf348b538uL, 0x59f111f1b605d019uL, 0x923f82a4af194f9buL, 0xab1c5ed5da6d8118uL,
    0xd807aa98a3030242uL, 0x12835b0145706fbeuL, 0x243185be4ee4b28cuL, 0x550c7dc3d5ffb4e2uL,
    0x72be5d74f27b896fuL, 0x80deb1fe3b1696b1uL, 0x9bdc06a725c71235uL, 0xc19bf174cf692694uL,
    0xe49b69c19ef14ad2uL, 0xefbe4786384f25e3uL, 0x0fc19dc68b8cd5b5uL, 0x240ca1cc77ac9c65uL,
    0x2de92c6f592b0275uL, 0x4a7484aa6ea6e483uL, 0x5cb0a9dcbd41fbd4uL, 0x76f988da831153b5uL,
    0x983e5152ee66dfabuL, 0xa831c66d2db43210uL, 0xb00327c898fb213fuL, 0xbf597fc7beef0ee4uL,
    0xc6e00bf33da88fc2uL, 0xd5a79147930aa725uL, 0x06ca6351e003826fuL, 0x142929670a0e6e70uL,
    0x27b70a8546d22ffcuL, 0x2e1b21385c26c926uL, 0x4d2c6dfc5ac42aeduL, 0x53380d139d95b3dfuL,
    0x650a73548baf63deuL, 0x766a0abb3c77b2a8uL, 0x81c2c92e47edaee6uL, 0x92722c851482353buL,
    0xa2bfe8a14cf10364uL, 0xa81a664bbc423001uL, 0xc24b8b70d0f89791uL, 0xc76c51a30654be30uL,
    0xd192e819d6ef5218uL, 0xd69906245565a910uL, 0xf40e35855771202auL, 0x106aa07032bbd1b8uL,
    0x19a4c116b8d2d0c8uL, 0x1e376c085141ab53uL, 0x2748774cdf8eeb99uL, 0x34b0bcb5e19b48a8uL,
    0x391c0cb3c5c95a63uL, 0x4ed8aa4ae3418acbuL, 0x5b9cca4f7763e373uL, 0x682e6ff3d6b2b8a3uL,
    0x748f82ee5defb2fcuL, 0x78a5636f43172f60uL, 0x84c87814a1f0ab72uL, 0x8cc702081a6439ecuL,
    0x90befffa23631e28uL, 0xa4506cebde82bde9uL, 0xbef9a3f7b2c67915uL, 0xc67178f2e372532buL,
    0xca273eceea26619cuL, 0xd186b8c721c0c207uL, 0xeada7dd6cde0eb1euL, 0xf57d4f7fee6ed178uL,
    0x06f067aa72176fbauL, 0x0a637dc5a2c898a6uL, 0x113f9804bef90daeuL, 0x1b710b35131c471buL,
    0x28db77f523047d84uL, 0x32caab7b40c72493uL, 0x3c9ebe0a15c9bebcuL, 0x431d67c49c100d4cuL,
    0x4cc5d4becb3e42b6uL, 0x597f299cfc657e2auL, 0x5fcb6fab3ad6faecuL, 0x6c44198c4a475817uL,
)

/** Constants necessary for SHA-512 family of digests. */
internal val K64X2: Array<ULongArray> = arrayOf(
    ulongArrayOf(K64[1],  K64[0]),  ulongArrayOf(K64[3],  K64[2]),  ulongArrayOf(K64[5],  K64[4]),  ulongArrayOf(K64[7],  K64[6]),
    ulongArrayOf(K64[9],  K64[8]),  ulongArrayOf(K64[11], K64[10]), ulongArrayOf(K64[13], K64[12]), ulongArrayOf(K64[15], K64[14]),
    ulongArrayOf(K64[17], K64[16]), ulongArrayOf(K64[19], K64[18]), ulongArrayOf(K64[21], K64[20]), ulongArrayOf(K64[23], K64[22]),
    ulongArrayOf(K64[25], K64[24]), ulongArrayOf(K64[27], K64[26]), ulongArrayOf(K64[29], K64[28]), ulongArrayOf(K64[31], K64[30]),
    ulongArrayOf(K64[33], K64[32]), ulongArrayOf(K64[35], K64[34]), ulongArrayOf(K64[37], K64[36]), ulongArrayOf(K64[39], K64[38]),
    ulongArrayOf(K64[41], K64[40]), ulongArrayOf(K64[43], K64[42]), ulongArrayOf(K64[45], K64[44]), ulongArrayOf(K64[47], K64[46]),
    ulongArrayOf(K64[49], K64[48]), ulongArrayOf(K64[51], K64[50]), ulongArrayOf(K64[53], K64[52]), ulongArrayOf(K64[55], K64[54]),
    ulongArrayOf(K64[57], K64[56]), ulongArrayOf(K64[59], K64[58]), ulongArrayOf(K64[61], K64[60]), ulongArrayOf(K64[63], K64[62]),
    ulongArrayOf(K64[65], K64[64]), ulongArrayOf(K64[67], K64[66]), ulongArrayOf(K64[69], K64[68]), ulongArrayOf(K64[71], K64[70]),
    ulongArrayOf(K64[73], K64[72]), ulongArrayOf(K64[75], K64[74]), ulongArrayOf(K64[77], K64[76]), ulongArrayOf(K64[79], K64[78]),
)

internal val H256_224: State256 = uintArrayOf(
    0xc1059ed8u, 0x367cd507u, 0x3070dd17u, 0xf70e5939u,
    0xffc00b31u, 0x68581511u, 0x64f98fa7u, 0xbefa4fa4u,
)

internal val H256_256: State256 = uintArrayOf(
    0x6a09e667u, 0xbb67ae85u, 0x3c6ef372u, 0xa54ff53au,
    0x510e527fu, 0x9b05688cu, 0x1f83d9abu, 0x5be0cd19u,
)

internal val H512_224: State512 = ulongArrayOf(
    0x8c3d37c819544da2uL, 0x73e1996689dcd4d6uL, 0x1dfab7ae32ff9c82uL, 0x679dd514582f9fcfuL,
    0x0f6d2b697bd44da8uL, 0x77e36f7304c48942uL, 0x3f9d85a86a1d36c8uL, 0x1112e6ad91d692a1uL,
)

internal val H512_256: State512 = ulongArrayOf(
    0x22312194fc2bf72cuL, 0x9f555fa3c84c64c2uL, 0x2393b86b6f53b151uL, 0x963877195940eabduL,
    0x96283ee2a88effe3uL, 0xbe5e1e2553863992uL, 0x2b0199fc2c85b8aauL, 0x0eb72ddc81c52ca2uL,
)

internal val H512_384: State512 = ulongArrayOf(
    0xcbbb9d5dc1059ed8uL, 0x629a292a367cd507uL, 0x9159015a3070dd17uL, 0x152fecd8f70e5939uL,
    0x67332667ffc00b31uL, 0x8eb44a8768581511uL, 0xdb0c2e0d64f98fa7uL, 0x47b5481dbefa4fa4uL,
)

internal val H512_512: State512 = ulongArrayOf(
    0x6a09e667f3bcc908uL, 0xbb67ae8584caa73buL, 0x3c6ef372fe94f82buL, 0xa54ff53a5f1d36f1uL,
    0x510e527fade682d1uL, 0x9b05688c2b3e6c1fuL, 0x1f83d9abfb41bd6buL, 0x5be0cd19137e2179uL,
)
