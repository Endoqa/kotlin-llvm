package clang

import clang.c.CXDiagnostic
import clang.c.clang_getDiagnosticLocation
import clang.c.clang_getDiagnosticSeverity
import clang.c.clang_getDiagnosticSpelling
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.SegmentAllocator


data class Diagnostic(
    val severity: DiagnosticSeverity,
    val location: SourceLocation,
    val spelling: String
) : SegmentAllocator {

    private val owner = Arena.ofAuto()


    constructor(diagnostic: CXDiagnostic) : this(
        clang_getDiagnosticSeverity(diagnostic),
        SourceLocation(clang_getDiagnosticLocation(diagnostic)),
        clang_getDiagnosticSpelling(diagnostic).unwrap()
    )

    override fun allocate(byteSize: Long, byteAlignment: Long): MemorySegment {
        return owner.allocate(byteSize, byteAlignment)
    }
}