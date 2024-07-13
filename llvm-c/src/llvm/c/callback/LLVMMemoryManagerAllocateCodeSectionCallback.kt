// this file is auto generated by endoqa kotlin ffi, modify it with caution
package llvm.c.callback

import llvm.c.`$RuntimeHelper`
import llvm.c.Pointer
import llvm.c.uint8_t
import llvm.c.uintptr_t
import java.lang.foreign.*
import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles

public fun interface LLVMMemoryManagerAllocateCodeSectionCallback {
    public fun invoke(
        Opaque: Pointer<Unit>,
        Size: uintptr_t,
        Alignment: UInt,
        SectionID: UInt,
        SectionName: Pointer<Byte>,
    ): Pointer<uint8_t>

    public fun allocate(arena: Arena): MemorySegment =
        Linker.nativeLinker().upcallStub(invokeHandle.bindTo(this), fd, arena)

    public companion object {
        @JvmStatic
        public val invokeHandle: MethodHandle =
            MethodHandles.lookup().unreflect(LLVMMemoryManagerAllocateCodeSectionCallback::class.java.methods.find {
                it.name == "invoke"
            }
            )

        @JvmStatic
        public val fd: FunctionDescriptor = FunctionDescriptor.of(
            `$RuntimeHelper`.POINTER,
            `$RuntimeHelper`.POINTER,
            ValueLayout.JAVA_LONG,
            ValueLayout.JAVA_INT,
            ValueLayout.JAVA_INT,
            `$RuntimeHelper`.POINTER,
        )
    }
}
