// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.clang

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.Int

public enum class CXGlobalOptFlags(
    public val `value`: Int,
) {
    /**
     *
     * Used to indicate that no special CXIndex options are needed.
     */
    None(0x0),

    /**
     *
     * Used to indicate that threads that libclang creates for indexing
     * purposes should use background priority.
     *
     * Affects #clang_indexSourceFile, #clang_indexTranslationUnit,
     * #clang_parseTranslationUnit, #clang_saveTranslationUnit.
     */
    ThreadBackgroundPriorityForIndexing(0x1),

    /**
     *
     * Used to indicate that threads that libclang creates for editing
     * purposes should use background priority.
     *
     * Affects #clang_reparseTranslationUnit, #clang_codeCompleteAt,
     * #clang_annotateTokens
     */
    ThreadBackgroundPriorityForEditing(0x2),

    /**
     *
     * Used to indicate that all threads that libclang creates should use
     * background priority.
     */
    ThreadBackgroundPriorityForAll(ThreadBackgroundPriorityForIndexing.value or ThreadBackgroundPriorityForEditing.value),
    ;

    public companion object {
        @JvmStatic
        public val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            CXGlobalOptFlags::class.java,
            "fromInt",
            MethodType.methodType(CXGlobalOptFlags::class.java, Int::class.java)
        )

        @JvmStatic
        public val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            CXGlobalOptFlags::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        public fun fromInt(`value`: Int): CXGlobalOptFlags = when (value) {
            None.value -> None
            ThreadBackgroundPriorityForIndexing.value -> ThreadBackgroundPriorityForIndexing
            ThreadBackgroundPriorityForEditing.value -> ThreadBackgroundPriorityForEditing
            ThreadBackgroundPriorityForAll.value -> ThreadBackgroundPriorityForAll
            else -> error("enum not found")
        }
    }
}
