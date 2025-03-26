// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.llvm

import java.lang.foreign.*
import java.lang.invoke.VarHandle

@JvmInline
public value class LLVMMCJITCompilerOptions(
    public val `$mem`: MemorySegment,
) {
    public var OptLevel: UInt
        get() = (OptLevelHandle.get(this.`$mem`, 0L) as Int).toUInt()
        set(`value`) {
            OptLevelHandle.set(this.`$mem`, 0L, value.toInt())
        }

    public var CodeModel: LLVMCodeModel
        get() = LLVMCodeModel.fromInt(CodeModelHandle.get(this.`$mem`, 0L) as Int)
        set(`value`) {
            CodeModelHandle.set(this.`$mem`, 0L, value.value)
        }

    public var NoFramePointerElim: LLVMBool
        get() = NoFramePointerElimHandle.get(this.`$mem`, 0L) as Int
        set(`value`) {
            NoFramePointerElimHandle.set(this.`$mem`, 0L, value)
        }

    public var EnableFastISel: LLVMBool
        get() = EnableFastISelHandle.get(this.`$mem`, 0L) as Int
        set(`value`) {
            EnableFastISelHandle.set(this.`$mem`, 0L, value)
        }

    public var MCJMM: LLVMMCJITMemoryManagerRef
        get() = MCJMMHandle.get(this.`$mem`, 0L) as MemorySegment
        set(`value`) {
            MCJMMHandle.set(this.`$mem`, 0L, value)
        }

    public constructor(gc: Boolean) : this(kotlin.run {
        require(gc) { "Do not call this if gc is not want" }
        Arena.ofAuto().allocate(layout)
    })

    public companion object {
        public val layout: StructLayout = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("OptLevel"),
            ValueLayout.JAVA_INT.withName("CodeModel"),
            ValueLayout.JAVA_INT.withName("NoFramePointerElim"),
            ValueLayout.JAVA_INT.withName("EnableFastISel"),
            `$RuntimeHelper`.POINTER.withName("MCJMM"),
        ).withName("LLVMMCJITCompilerOptions")

        @JvmField
        public val OptLevelHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("OptLevel"))

        @JvmField
        public val CodeModelHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("CodeModel"))

        @JvmField
        public val NoFramePointerElimHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("NoFramePointerElim"))

        @JvmField
        public val EnableFastISelHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("EnableFastISel"))

        @JvmField
        public val MCJMMHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("MCJMM"))

        @JvmStatic
        public fun allocate(alloc: SegmentAllocator): LLVMMCJITCompilerOptions =
            LLVMMCJITCompilerOptions(alloc.allocate(layout))
    }
}
