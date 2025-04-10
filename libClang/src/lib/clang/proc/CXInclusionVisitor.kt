// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.clang.proc

import lib.clang.*
import java.lang.foreign.*
import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles

/**
 *
 * Visitor invoked for each file in a translation unit
 * (used with clang_getInclusions()).
 *
 * This visitor function will be invoked by clang_getInclusions() for each
 * file included (either at the top-level or by \#include directives) within
 * a translation unit.  The first argument is the file being included, and
 * the second and third arguments provide the inclusion stack.  The
 * array is sorted in order of immediate inclusion.  For example,
 * the first element refers to the location that included 'included_file'.
 */
public fun interface CXInclusionVisitor {
    @CFunctionInvoke
    public fun invoke(
        included_file: CXFile,
        inclusion_stack: Pointer<CXSourceLocation>,
        include_len: UInt,
        client_data: CXClientData,
    )

    public fun allocate(arena: Arena): MemorySegment =
        Linker.nativeLinker().upcallStub(invokeHandle.bindTo(this), fd, arena)

    public companion object {
        @JvmStatic
        public val invokeHandle: MethodHandle =
            MethodHandles.lookup().unreflect(CXInclusionVisitor::class.java.methods.find {
                it.getAnnotation(CFunctionInvoke::class.java) != null
            }
            )

        @JvmStatic
        public val fd: FunctionDescriptor = FunctionDescriptor.ofVoid(
            `$RuntimeHelper`.POINTER,
            `$RuntimeHelper`.POINTER,
            ValueLayout.JAVA_INT,
            `$RuntimeHelper`.POINTER,
        )
    }
}
