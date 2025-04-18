// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.clang

import java.lang.foreign.*
import java.lang.invoke.MethodHandle
import java.lang.invoke.VarHandle

/**
 *
 * Identifies a specific source location within a translation
 * unit.
 *
 * Use clang_getExpansionLocation() or clang_getSpellingLocation()
 * to map a source location to a particular file, line, and column.
 */
@JvmInline
public value class CXSourceLocation(
    public val `$mem`: MemorySegment,
) {
    public val ptr_data: NativeArray<Pointer<Unit>>
        get() = ptr_dataHandle.invokeExact(this.`$mem`, 0L) as MemorySegment

    public var int_data: UInt
        get() = (int_dataHandle.get(this.`$mem`, 0L) as Int).toUInt()
        set(`value`) {
            int_dataHandle.set(this.`$mem`, 0L, value.toInt())
        }

    public constructor(gc: Boolean) : this(kotlin.run {
        require(gc) { "Do not call this if gc is not want" }
        Arena.ofAuto().allocate(layout)
    })

    public companion object {
        public val layout: StructLayout = MemoryLayout.structLayout(
            MemoryLayout.sequenceLayout(2L, `$RuntimeHelper`.POINTER).withName("ptr_data"),
            ValueLayout.JAVA_INT.withName("int_data"),
            MemoryLayout.paddingLayout(4),
        ).withName("CXSourceLocation")

        @JvmField
        public val ptr_dataHandle: MethodHandle =
            layout.sliceHandle(MemoryLayout.PathElement.groupElement("ptr_data"))

        @JvmField
        public val int_dataHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("int_data"))

        @JvmStatic
        public fun allocate(alloc: SegmentAllocator): CXSourceLocation =
            CXSourceLocation(alloc.allocate(layout))
    }
}
