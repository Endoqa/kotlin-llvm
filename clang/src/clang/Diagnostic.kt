package clang

import lib.clang.*
import java.lang.foreign.Arena


data class Diagnostic(
    private val ptr: CXDiagnostic,
) {

    val severity get() = clang_getDiagnosticSeverity(ptr)
    val text by lazy {
        tempAllocate { clang_getDiagnosticSpelling(ptr).unwrap() }
    }

    val sourceLocation by lazy {
        val allocator = Arena.ofAuto()
        val sl = with(allocator) {
            SourceLocation(clang_getDiagnosticLocation(ptr), allocator)
        }
        sl
    }


    val ranges by lazy {
        val numRanges = clang_getDiagnosticNumRanges(ptr)
        List(numRanges.toInt()) {
            isolateOwner {
                SourceRange(clang_getDiagnosticRange(ptr, it.toUInt()), this)
            }
        }
    }


}