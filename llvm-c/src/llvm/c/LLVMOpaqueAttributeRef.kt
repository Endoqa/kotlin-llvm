// this file is auto generated by endoqa kotlin ffi, modify it with caution
package llvm.c

import java.lang.foreign.*

@JvmInline
public value class LLVMOpaqueAttributeRef(
    public val `$mem`: MemorySegment,
) {
    public constructor(gc: Boolean) : this(kotlin.run {
        require(gc) { "Do not call this if gc is not want" }
        Arena.ofAuto().allocate(LLVMOpaqueAttributeRef.layout)
    })

    public companion object {
        public val layout: StructLayout = MemoryLayout.structLayout(
        ).withName("LLVMOpaqueAttributeRef")

        @JvmStatic
        public fun allocate(alloc: SegmentAllocator): LLVMOpaqueAttributeRef =
            LLVMOpaqueAttributeRef(alloc.allocate(LLVMOpaqueAttributeRef.layout))
    }
}
