// this file is auto generated by endoqa kotlin ffi, modify it with caution
package llvm.c

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.Int

public enum class lto_debug_model(
    public val `value`: Int,
) {
    DEBUGMODELNONE(0),
    DEBUGMODELDWARF(1),
    ;

    public companion object {
        @JvmStatic
        public val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            lto_debug_model::class.java,
            "fromInt",
            MethodType.methodType(lto_debug_model::class.java, Int::class.java)
        )

        @JvmStatic
        public val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            lto_debug_model::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        public fun fromInt(`value`: Int): lto_debug_model = when (value) {
            DEBUGMODELNONE.value -> DEBUGMODELNONE
            DEBUGMODELDWARF.value -> DEBUGMODELDWARF
            else -> error("enum not found")
        }
    }
}
