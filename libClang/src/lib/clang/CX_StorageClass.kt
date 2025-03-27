// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.clang

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.Int

/**
 *
 * Represents the storage classes as declared in the source. CX_SC_Invalid
 * was added for the case that the passed cursor in not a declaration.
 */
public enum class CX_StorageClass(
    public val `value`: Int,
) {
    SCInvalid(0),
    SCNone(1),
    SCExtern(2),
    SCStatic(3),
    SCPrivateExtern(4),
    SCOpenCLWorkGroupLocal(5),
    SCAuto(6),
    SCRegister(7),
    ;

    public companion object {
        @JvmStatic
        public val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            CX_StorageClass::class.java,
            "fromInt",
            MethodType.methodType(CX_StorageClass::class.java, Int::class.java)
        )

        @JvmStatic
        public val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            CX_StorageClass::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        public fun fromInt(`value`: Int): CX_StorageClass = when (value) {
            SCInvalid.value -> SCInvalid
            SCNone.value -> SCNone
            SCExtern.value -> SCExtern
            SCStatic.value -> SCStatic
            SCPrivateExtern.value -> SCPrivateExtern
            SCOpenCLWorkGroupLocal.value -> SCOpenCLWorkGroupLocal
            SCAuto.value -> SCAuto
            SCRegister.value -> SCRegister
            else -> error("enum not found")
        }
    }
}
