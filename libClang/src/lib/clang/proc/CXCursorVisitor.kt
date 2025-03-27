// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.clang.proc

import lib.clang.*
import java.lang.foreign.*
import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles

/**
 *
 * Visitor invoked for each cursor found by a traversal.
 *
 * This visitor function will be invoked for each cursor found by
 * clang_visitCursorChildren(). Its first argument is the cursor being
 * visited, its second argument is the parent visitor for that cursor,
 * and its third argument is the client data provided to
 * clang_visitCursorChildren().
 *
 * The visitor should return one of the \c CXChildVisitResult values
 * to direct clang_visitCursorChildren().
 */
public fun interface CXCursorVisitor {
    @CFunctionInvoke
    public fun invoke(
        cursor: CXCursor,
        parent: CXCursor,
        client_data: CXClientData,
    ): CXChildVisitResult

    public fun allocate(arena: Arena): MemorySegment =
        Linker.nativeLinker().upcallStub(invokeHandle.bindTo(this), fd, arena)

    public companion object {
        @JvmStatic
        public val invokeHandle: MethodHandle =
            MethodHandles.filterReturnValue(MethodHandles.lookup().unreflect(CXCursorVisitor::class.java.methods.find {
                it.getAnnotation(CFunctionInvoke::class.java) != null
            }
            ), CXChildVisitResult.toInt)

        @JvmStatic
        public val fd: FunctionDescriptor = FunctionDescriptor.of(
            ValueLayout.JAVA_INT,
            CXCursor.layout,
            CXCursor.layout,
            `$RuntimeHelper`.POINTER,
        )
    }
}
