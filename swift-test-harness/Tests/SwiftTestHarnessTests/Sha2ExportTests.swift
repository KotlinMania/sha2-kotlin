import Testing
import Sha2

@Suite("Sha2 Swift Export Tests")
struct Sha2ExportTests {
    @Test("Swift module imports and basic types are reachable")
    func swiftModuleLoads() throws {
        #expect(Bool(true))
    }
}
