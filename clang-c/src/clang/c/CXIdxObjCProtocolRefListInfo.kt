// this file is auto generated by endoqa kotlin ffi, modify it with caution
package clang.c

import java.lang.foreign.*
import java.lang.invoke.VarHandle

@JvmInline
value class CXIdxObjCProtocolRefListInfo(
    val `$mem`: MemorySegment,
) {
    var protocols: Pointer<Pointer<CXIdxObjCProtocolRefInfo>>
        get() = protocolsHandle.get(this.`$mem`, 0L) as MemorySegment
        set(`value`) {
            protocolsHandle.set(this.`$mem`, 0L, value)
        }

    var numProtocols: UInt
        get() = (numProtocolsHandle.get(this.`$mem`, 0L) as Int).toUInt()
        set(`value`) {
            numProtocolsHandle.set(this.`$mem`, 0L, value.toInt())
        }

    constructor(gc: Boolean) : this(kotlin.run {
        require(gc) { "Do not call this if gc is not want" }
        Arena.ofAuto().allocate(layout)
    })

    companion object {
        val layout: StructLayout = MemoryLayout.structLayout(
            `$RuntimeHelper`.POINTER.withName("protocols"),
            ValueLayout.JAVA_INT.withName("numProtocols"),
            MemoryLayout.paddingLayout(4),
        ).withName("CXIdxObjCProtocolRefListInfo")

        @JvmField
        val protocolsHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("protocols"))

        @JvmField
        val numProtocolsHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("numProtocols"))

        @JvmStatic
        fun allocate(alloc: SegmentAllocator): CXIdxObjCProtocolRefListInfo =
            CXIdxObjCProtocolRefListInfo(alloc.allocate(layout))
    }
}
