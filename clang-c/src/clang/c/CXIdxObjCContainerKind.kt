// this file is auto generated by endoqa kotlin ffi, modify it with caution
package clang.c

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.Int

enum class CXIdxObjCContainerKind(
    val `value`: Int,
) {
    ForwardRef(0x00000000),
    Interface(0x00000001),
    Implementation(0x00000002),
    ;

    companion object {
        @JvmStatic
        val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            CXIdxObjCContainerKind::class.java,
            "fromInt",
            MethodType.methodType(CXIdxObjCContainerKind::class.java, Int::class.java)
        )

        @JvmStatic
        val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            CXIdxObjCContainerKind::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        fun fromInt(`value`: Int): CXIdxObjCContainerKind = when (value) {
            0x00000000 -> ForwardRef
            0x00000001 -> Interface
            0x00000002 -> Implementation
            else -> error("enum not found")
        }
    }
}
