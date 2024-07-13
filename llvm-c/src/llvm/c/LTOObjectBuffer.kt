// this file is auto generated by endoqa kotlin ffi, modify it with caution
package llvm.c

import java.lang.foreign.*
import java.lang.invoke.VarHandle

@JvmInline
public value class LTOObjectBuffer(
    public val `$mem`: MemorySegment,
) {
    public var Buffer: Pointer<Byte>
        get() = LTOObjectBuffer.BufferHandle.get(this.`$mem`, 0L) as MemorySegment
        set(`value`) {
            LTOObjectBuffer.BufferHandle.set(this.`$mem`, 0L, value)
        }

    public var Size: ULong
        get() = (LTOObjectBuffer.SizeHandle.get(this.`$mem`, 0L) as Long).toULong()
        set(`value`) {
            LTOObjectBuffer.SizeHandle.set(this.`$mem`, 0L, value.toLong())
        }

    public constructor(gc: Boolean) : this(kotlin.run {
        require(gc) { "Do not call this if gc is not want" }
        Arena.ofAuto().allocate(LTOObjectBuffer.layout)
    })

    public companion object {
        public val layout: StructLayout = MemoryLayout.structLayout(
            `$RuntimeHelper`.POINTER.withName("Buffer"),
            ValueLayout.JAVA_LONG.withName("Size"),
        ).withName("LTOObjectBuffer")

        @JvmField
        public val BufferHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("Buffer"))

        @JvmField
        public val SizeHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("Size"))

        @JvmStatic
        public fun allocate(alloc: SegmentAllocator): LTOObjectBuffer =
            LTOObjectBuffer(alloc.allocate(LTOObjectBuffer.layout))
    }
}
