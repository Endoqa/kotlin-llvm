// this file is auto generated by endoqa kotlin ffi, modify it with caution
package llvm.c

import java.lang.foreign.*
import java.lang.invoke.MethodHandle
import java.lang.invoke.VarHandle

@JvmInline
public value class LLVMOrcCSymbolMapPair(
    public val `$mem`: MemorySegment,
) {
    public var Name: LLVMOrcSymbolStringPoolEntryRef
        get() = LLVMOrcCSymbolMapPair.NameHandle.get(this.`$mem`, 0L) as MemorySegment
        set(`value`) {
            LLVMOrcCSymbolMapPair.NameHandle.set(this.`$mem`, 0L, value)
        }

    public var Sym: LLVMJITEvaluatedSymbol
        get() = LLVMJITEvaluatedSymbol(
            LLVMOrcCSymbolMapPair.SymHandle.invokeExact(this.`$mem`, 0L) as
                MemorySegment
        )
        set(`value`) {
            MemorySegment.copy(
                value.`$mem`, 0L, this.Sym.`$mem`, 0L,
                LLVMJITEvaluatedSymbol.layout.byteSize()
            )
        }

    public constructor(gc: Boolean) : this(kotlin.run {
        require(gc) { "Do not call this if gc is not want" }
        Arena.ofAuto().allocate(LLVMOrcCSymbolMapPair.layout)
    })

    public companion object {
        public val layout: StructLayout = MemoryLayout.structLayout(
            `$RuntimeHelper`.POINTER.withName("Name"),
            LLVMJITEvaluatedSymbol.layout.withName("Sym"),
        ).withName("LLVMOrcCSymbolMapPair")

        @JvmField
        public val NameHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("Name"))

        @JvmField
        public val SymHandle: MethodHandle =
            layout.sliceHandle(MemoryLayout.PathElement.groupElement("Sym"))

        @JvmStatic
        public fun allocate(alloc: SegmentAllocator): LLVMOrcCSymbolMapPair =
            LLVMOrcCSymbolMapPair(alloc.allocate(LLVMOrcCSymbolMapPair.layout))
    }
}
