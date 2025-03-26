// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.llvm.proc

import lib.llvm.`$RuntimeHelper`
import lib.llvm.CFunctionInvoke
import lib.llvm.Pointer
import java.lang.foreign.Arena
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.Linker
import java.lang.foreign.MemorySegment
import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles

/**
 *
 * @addtogroup LLVMCError
 *
 * @{
 */
public fun interface LLVMFatalErrorHandler {
    @CFunctionInvoke
    public fun invoke(Reason: Pointer<Byte>)

    public fun allocate(arena: Arena): MemorySegment =
        Linker.nativeLinker().upcallStub(invokeHandle.bindTo(this), fd, arena)

    public companion object {
        @JvmStatic
        public val invokeHandle: MethodHandle =
            MethodHandles.lookup().unreflect(LLVMFatalErrorHandler::class.java.methods.find {
                it.getAnnotation(CFunctionInvoke::class.java) != null
            }
            )

        @JvmStatic
        public val fd: FunctionDescriptor = FunctionDescriptor.ofVoid(
            `$RuntimeHelper`.POINTER,
        )
    }
}
