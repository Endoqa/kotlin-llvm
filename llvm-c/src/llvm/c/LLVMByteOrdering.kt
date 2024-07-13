// this file is auto generated by endoqa kotlin ffi, modify it with caution
package llvm.c

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.Int

public enum class LLVMByteOrdering(
    public val `value`: Int,
) {
    BigEndian(0),
    LittleEndian(1),
    ;

    public companion object {
        @JvmStatic
        public val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            LLVMByteOrdering::class.java,
            "fromInt",
            MethodType.methodType(LLVMByteOrdering::class.java, Int::class.java)
        )

        @JvmStatic
        public val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            LLVMByteOrdering::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        public fun fromInt(`value`: Int): LLVMByteOrdering = when (value) {
            BigEndian.value -> BigEndian
            LittleEndian.value -> LittleEndian
            else -> error("enum not found")
        }
    }
}
