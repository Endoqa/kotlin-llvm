package clang

import lib.clang.CXSourceRange
import lib.clang.clang_getRangeEnd
import lib.clang.clang_getRangeStart
import java.lang.foreign.Arena

class SourceRange(
    val sr: CXSourceRange,
    private val owner: Arena? = null
) {


    val start: SourceLocation by lazy {
        isolateOwner { SourceLocation(clang_getRangeStart(sr), this) }
    }

    val end: SourceLocation by lazy {
        isolateOwner { SourceLocation(clang_getRangeEnd(sr), this) }
    }

}
