// this file is auto generated by endoqa kotlin ffi, modify it with caution
package clang.c

import java.lang.foreign.*

@JvmInline
value class _CXCursorAndRangeVisitorBlock(
    val `$mem`: MemorySegment,
) {
    constructor(gc: Boolean) : this(kotlin.run {
        require(gc) { "Do not call this if gc is not want" }
        Arena.ofAuto().allocate(layout)
    })

    companion object {
        val layout: StructLayout = MemoryLayout.structLayout(
        ).withName("_CXCursorAndRangeVisitorBlock")

        @JvmStatic
        fun allocate(alloc: SegmentAllocator): _CXCursorAndRangeVisitorBlock =
            _CXCursorAndRangeVisitorBlock(alloc.allocate(layout))
    }
}
