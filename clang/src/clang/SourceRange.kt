package clang

import clang.c.CXSourceRange
import clang.c.clang_getRangeEnd
import clang.c.clang_getRangeStart
import java.lang.foreign.Arena
import java.lang.foreign.SegmentAllocator

class SourceRange(
    val sr: CXSourceRange,
    private val owner: Arena = Arena.ofAuto()
) : SegmentAllocator by owner {


    val begin: SourceLocation by lazy {
        SourceLocation(clang_getRangeStart(sr))
    }

    val end: SourceLocation by lazy {
        SourceLocation(clang_getRangeEnd(sr))
    }

}
