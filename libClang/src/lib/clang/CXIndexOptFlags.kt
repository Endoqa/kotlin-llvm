// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.clang

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.Int

public enum class CXIndexOptFlags(
    public val `value`: Int,
) {
    /**
     *
     * Used to indicate that no special indexing options are needed.
     */
    None(0x0),

    /**
     *
     * Used to indicate that IndexerCallbacks#indexEntityReference should
     * be invoked for only one reference of an entity per source file that does
     * not also include a declaration/definition of the entity.
     */
    SuppressRedundantRefs(0x1),

    /**
     *
     * Function-local symbols should be indexed. If this is not set
     * function-local symbols will be ignored.
     */
    IndexFunctionLocalSymbols(0x2),

    /**
     *
     * Implicit function/class template instantiations should be indexed.
     * If this is not set, implicit instantiations will be ignored.
     */
    IndexImplicitTemplateInstantiations(0x4),

    /**
     *
     * Suppress all compiler warnings when parsing for indexing.
     */
    SuppressWarnings(0x8),

    /**
     *
     * Skip a function/method body that was already parsed during an
     * indexing session associated with a \c CXIndexAction object.
     * Bodies in system headers are always skipped.
     */
    SkipParsedBodiesInSession(0x10),
    ;

    public companion object {
        @JvmStatic
        public val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            CXIndexOptFlags::class.java,
            "fromInt",
            MethodType.methodType(CXIndexOptFlags::class.java, Int::class.java)
        )

        @JvmStatic
        public val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            CXIndexOptFlags::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        public fun fromInt(`value`: Int): CXIndexOptFlags = when (value) {
            None.value -> None
            SuppressRedundantRefs.value -> SuppressRedundantRefs
            IndexFunctionLocalSymbols.value -> IndexFunctionLocalSymbols
            IndexImplicitTemplateInstantiations.value -> IndexImplicitTemplateInstantiations
            SuppressWarnings.value -> SuppressWarnings
            SkipParsedBodiesInSession.value -> SkipParsedBodiesInSession
            else -> error("enum not found")
        }
    }
}
