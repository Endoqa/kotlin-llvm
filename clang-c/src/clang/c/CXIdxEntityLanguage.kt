// this file is auto generated by endoqa kotlin ffi, modify it with caution
package clang.c

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.Int

enum class CXIdxEntityLanguage(
    val `value`: Int,
) {
    None(0x00000000),
    C(0x00000001),
    ObjC(0x00000002),
    CXX(0x00000003),
    Swift(0x00000004),
    ;

    companion object {
        @JvmStatic
        val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            CXIdxEntityLanguage::class.java,
            "fromInt",
            MethodType.methodType(CXIdxEntityLanguage::class.java, Int::class.java)
        )

        @JvmStatic
        val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            CXIdxEntityLanguage::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        fun fromInt(`value`: Int): CXIdxEntityLanguage = when (value) {
            0x00000000 -> None
            0x00000001 -> C
            0x00000002 -> ObjC
            0x00000003 -> CXX
            0x00000004 -> Swift
            else -> error("enum not found")
        }
    }
}
