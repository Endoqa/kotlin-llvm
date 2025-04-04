// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.clang

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.Int

/**
 *
 * Property attributes for a \c CXCursor_ObjCPropertyDecl.
 */
public enum class CXObjCPropertyAttrKind(
    public val `value`: Int,
) {
    noattr(0x00),
    readonly(0x01),
    getter(0x02),
    assign(0x04),
    readwrite(0x08),
    retain(0x10),
    copy(0x20),
    nonatomic(0x40),
    setter(0x80),
    atomic(0x100),
    weak(0x200),
    strong(0x400),
    unsafeunretained(0x800),
    `class`(0x1000),
    ;

    public companion object {
        @JvmStatic
        public val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            CXObjCPropertyAttrKind::class.java,
            "fromInt",
            MethodType.methodType(CXObjCPropertyAttrKind::class.java, Int::class.java)
        )

        @JvmStatic
        public val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            CXObjCPropertyAttrKind::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        public fun fromInt(`value`: Int): CXObjCPropertyAttrKind = when (value) {
            noattr.value -> noattr
            readonly.value -> readonly
            getter.value -> getter
            assign.value -> assign
            readwrite.value -> readwrite
            retain.value -> retain
            copy.value -> copy
            nonatomic.value -> nonatomic
            setter.value -> setter
            atomic.value -> atomic
            weak.value -> weak
            strong.value -> strong
            unsafeunretained.value -> unsafeunretained
            `class`.value -> `class`
            else -> error("enum not found")
        }
    }
}
