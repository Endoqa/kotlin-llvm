// this file is auto generated by endoqa kotlin ffi, modify it with caution
package llvm.c

import java.lang.foreign.*

@JvmInline
public value class LLVMOrcOpaqueMaterializationResponsibility(
    public val `$mem`: MemorySegment,
) {
    public constructor(gc: Boolean) : this(kotlin.run {
        require(gc) { "Do not call this if gc is not want" }
        Arena.ofAuto().allocate(LLVMOrcOpaqueMaterializationResponsibility.layout)
    })

    public companion object {
        public val layout: StructLayout = MemoryLayout.structLayout(
        ).withName("LLVMOrcOpaqueMaterializationResponsibility")

        @JvmStatic
        public fun allocate(alloc: SegmentAllocator): LLVMOrcOpaqueMaterializationResponsibility =
            LLVMOrcOpaqueMaterializationResponsibility(alloc.allocate(LLVMOrcOpaqueMaterializationResponsibility.layout))
    }
}
