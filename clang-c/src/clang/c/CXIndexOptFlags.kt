// this file is auto generated by endoqa kotlin ffi, modify it with caution
package clang.c

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.Int

enum class CXIndexOptFlags(
    val `value`: Int,
) {
    None(0x00000000),
    SuppressRedundantRefs(0x00000001),
    IndexFunctionLocalSymbols(0x00000002),
    IndexImplicitTemplateInstantiations(0x00000004),
    SuppressWarnings(0x00000008),
    SkipParsedBodiesInSession(0x00000010),
    ;

    companion object {
        @JvmStatic
        val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            CXIndexOptFlags::class.java,
            "fromInt",
            MethodType.methodType(CXIndexOptFlags::class.java, Int::class.java)
        )

        @JvmStatic
        val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            CXIndexOptFlags::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        fun fromInt(`value`: Int): CXIndexOptFlags = when (value) {
            0x00000000 -> None
            0x00000001 -> SuppressRedundantRefs
            0x00000002 -> IndexFunctionLocalSymbols
            0x00000004 -> IndexImplicitTemplateInstantiations
            0x00000008 -> SuppressWarnings
            0x00000010 -> SkipParsedBodiesInSession
            else -> error("enum not found")
        }
    }
}
