// this file is auto generated by endoqa kotlin ffi, modify it with caution
package llvm.c

import java.lang.foreign.*

@JvmInline
public value class LLVMOrcOpaqueDumpObjects(
    public val `$mem`: MemorySegment,
) {
    public constructor(gc: Boolean) : this(kotlin.run {
        require(gc) { "Do not call this if gc is not want" }
        Arena.ofAuto().allocate(LLVMOrcOpaqueDumpObjects.layout)
    })

    public companion object {
        public val layout: StructLayout = MemoryLayout.structLayout(
        ).withName("LLVMOrcOpaqueDumpObjects")

        @JvmStatic
        public fun allocate(alloc: SegmentAllocator): LLVMOrcOpaqueDumpObjects =
            LLVMOrcOpaqueDumpObjects(alloc.allocate(LLVMOrcOpaqueDumpObjects.layout))
    }
}
