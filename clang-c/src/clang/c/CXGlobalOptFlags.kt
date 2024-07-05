// this file is auto generated by endoqa kotlin ffi, modify it with caution
package clang.c

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.Int

enum class CXGlobalOptFlags(
    val `value`: Int,
) {
    None(0x00000000),
    ThreadBackgroundPriorityForIndexing(0x00000001),
    ThreadBackgroundPriorityForEditing(0x00000002),
    ThreadBackgroundPriorityForAll(0x00000003),
    ;

    companion object {
        @JvmStatic
        val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            CXGlobalOptFlags::class.java,
            "fromInt",
            MethodType.methodType(CXGlobalOptFlags::class.java, Int::class.java)
        )

        @JvmStatic
        val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            CXGlobalOptFlags::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        fun fromInt(`value`: Int): CXGlobalOptFlags = when (value) {
            0x00000000 -> None
            0x00000001 -> ThreadBackgroundPriorityForIndexing
            0x00000002 -> ThreadBackgroundPriorityForEditing
            0x00000003 -> ThreadBackgroundPriorityForAll
            else -> error("enum not found")
        }
    }
}
