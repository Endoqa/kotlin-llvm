// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.llvm

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.Int

public enum class LLVMGlobalISelAbortMode(
    public val `value`: Int,
) {
    Enable(0),
    Disable(1),
    DisableWithDiag(2),
    ;

    public companion object {
        @JvmStatic
        public val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            LLVMGlobalISelAbortMode::class.java,
            "fromInt",
            MethodType.methodType(LLVMGlobalISelAbortMode::class.java, Int::class.java)
        )

        @JvmStatic
        public val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            LLVMGlobalISelAbortMode::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        public fun fromInt(`value`: Int): LLVMGlobalISelAbortMode = when (value) {
            Enable.value -> Enable
            Disable.value -> Disable
            DisableWithDiag.value -> DisableWithDiag
            else -> error("enum not found")
        }
    }
}
