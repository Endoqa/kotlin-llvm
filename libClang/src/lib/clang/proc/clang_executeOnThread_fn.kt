// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.clang.proc

import lib.clang.`$RuntimeHelper`
import lib.clang.CFunctionInvoke
import lib.clang.Pointer
import java.lang.foreign.Arena
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.Linker
import java.lang.foreign.MemorySegment
import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles

public fun interface clang_executeOnThread_fn {
    @CFunctionInvoke
    public fun invoke(`$p0`: Pointer<Unit>)

    public fun allocate(arena: Arena): MemorySegment =
        Linker.nativeLinker().upcallStub(invokeHandle.bindTo(this), fd, arena)

    public companion object {
        @JvmStatic
        public val invokeHandle: MethodHandle =
            MethodHandles.lookup().unreflect(clang_executeOnThread_fn::class.java.methods.find {
                it.getAnnotation(CFunctionInvoke::class.java) != null
            }
            )

        @JvmStatic
        public val fd: FunctionDescriptor = FunctionDescriptor.ofVoid(
            `$RuntimeHelper`.POINTER,
        )
    }
}
