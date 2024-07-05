// this file is auto generated by endoqa kotlin ffi, modify it with caution
package clang.c

import java.lang.foreign.*
import java.lang.invoke.MethodHandle
import java.lang.invoke.VarHandle

@JvmInline
value class CXIdxObjCCategoryDeclInfo(
    val `$mem`: MemorySegment,
) {
    var containerInfo: Pointer<CXIdxObjCContainerDeclInfo>
        get() = containerInfoHandle.get(this.`$mem`, 0L) as MemorySegment
        set(`value`) {
            containerInfoHandle.set(this.`$mem`, 0L, value)
        }

    var objcClass: Pointer<CXIdxEntityInfo>
        get() = objcClassHandle.get(this.`$mem`, 0L) as MemorySegment
        set(`value`) {
            objcClassHandle.set(this.`$mem`, 0L, value)
        }

    var classCursor: CXCursor
        get() = CXCursor(
            classCursorHandle.invokeExact(this.`$mem`, 0L) as
                    MemorySegment
        )
        set(`value`) {
            MemorySegment.copy(value.`$mem`, 0L, this.classCursor.`$mem`, 0L, CXCursor.layout.byteSize())
        }

    var classLoc: CXIdxLoc
        get() = CXIdxLoc(
            classLocHandle.invokeExact(this.`$mem`, 0L) as
                    MemorySegment
        )
        set(`value`) {
            MemorySegment.copy(value.`$mem`, 0L, this.classLoc.`$mem`, 0L, CXIdxLoc.layout.byteSize())
        }

    var protocols: Pointer<CXIdxObjCProtocolRefListInfo>
        get() = protocolsHandle.get(this.`$mem`, 0L) as MemorySegment
        set(`value`) {
            protocolsHandle.set(this.`$mem`, 0L, value)
        }

    constructor(gc: Boolean) : this(kotlin.run {
        require(gc) { "Do not call this if gc is not want" }
        Arena.ofAuto().allocate(layout)
    })

    companion object {
        val layout: StructLayout = MemoryLayout.structLayout(
            `$RuntimeHelper`.POINTER.withName("containerInfo"),
            `$RuntimeHelper`.POINTER.withName("objcClass"),
            CXCursor.layout.withName("classCursor"),
            CXIdxLoc.layout.withName("classLoc"),
            `$RuntimeHelper`.POINTER.withName("protocols"),
        ).withName("CXIdxObjCCategoryDeclInfo")

        @JvmField
        val containerInfoHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("containerInfo"))

        @JvmField
        val objcClassHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("objcClass"))

        @JvmField
        val classCursorHandle: MethodHandle =
            layout.sliceHandle(MemoryLayout.PathElement.groupElement("classCursor"))

        @JvmField
        val classLocHandle: MethodHandle =
            layout.sliceHandle(MemoryLayout.PathElement.groupElement("classLoc"))

        @JvmField
        val protocolsHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("protocols"))

        @JvmStatic
        fun allocate(alloc: SegmentAllocator): CXIdxObjCCategoryDeclInfo =
            CXIdxObjCCategoryDeclInfo(alloc.allocate(layout))
    }
}
