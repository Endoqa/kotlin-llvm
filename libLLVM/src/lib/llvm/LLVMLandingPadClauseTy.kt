// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.llvm

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType

public enum class LLVMLandingPadClauseTy(
    public val `value`: Int,
) {
    /**
     * < A catch clause
     */
    Catch(0),

    /**
     * < A filter clause
     */
    Filter(1),
    ;

    public companion object {
        @JvmStatic
        public val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            LLVMLandingPadClauseTy::class.java,
            "fromInt",
            MethodType.methodType(LLVMLandingPadClauseTy::class.java, Int::class.java)
        )

        @JvmStatic
        public val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            LLVMLandingPadClauseTy::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        public fun fromInt(`value`: Int): LLVMLandingPadClauseTy = when (value) {
            Catch.value -> Catch
            Filter.value -> Filter
            else -> error("enum not found")
        }
    }
}
