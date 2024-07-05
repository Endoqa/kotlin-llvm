// this file is auto generated by endoqa kotlin ffi, modify it with caution
package clang.c

import java.lang.foreign.*
import java.lang.invoke.MethodHandle
import java.lang.invoke.VarHandle

@JvmInline
value class CXIdxLoc(
    val `$mem`: MemorySegment,
) {
    val ptr_data: NativeArray<Pointer<Unit>>
        get() = ptr_dataHandle.invokeExact(this.`$mem`, 0L) as MemorySegment

    var int_data: UInt
        get() = (int_dataHandle.get(this.`$mem`, 0L) as Int).toUInt()
        set(`value`) {
            int_dataHandle.set(this.`$mem`, 0L, value.toInt())
        }

    constructor(gc: Boolean) : this(kotlin.run {
        require(gc) { "Do not call this if gc is not want" }
        Arena.ofAuto().allocate(layout)
    })

    companion object {
        val layout: StructLayout = MemoryLayout.structLayout(
            MemoryLayout.sequenceLayout(2L, `$RuntimeHelper`.POINTER).withName("ptr_data"),
            ValueLayout.JAVA_INT.withName("int_data"),
            MemoryLayout.paddingLayout(4),
        ).withName("CXIdxLoc")

        @JvmField
        val ptr_dataHandle: MethodHandle =
            layout.sliceHandle(MemoryLayout.PathElement.groupElement("ptr_data"))

        @JvmField
        val int_dataHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("int_data"))

        @JvmStatic
        fun allocate(alloc: SegmentAllocator): CXIdxLoc =
            CXIdxLoc(alloc.allocate(layout))
    }
}
