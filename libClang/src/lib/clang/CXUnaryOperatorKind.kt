// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.clang

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.Int

/**
 *
 * Describes the kind of unary operators.
 */
public enum class CXUnaryOperatorKind(
    public val `value`: Int,
) {
    /**
     * This value describes cursors which are not unary operators.
     */
    Invalid(0),

    /**
     * Postfix increment operator.
     */
    PostInc(1),

    /**
     * Postfix decrement operator.
     */
    PostDec(2),

    /**
     * Prefix increment operator.
     */
    PreInc(3),

    /**
     * Prefix decrement operator.
     */
    PreDec(4),

    /**
     * Address of operator.
     */
    AddrOf(5),

    /**
     * Dereference operator.
     */
    Deref(6),

    /**
     * Plus operator.
     */
    Plus(7),

    /**
     * Minus operator.
     */
    Minus(8),

    /**
     * Not operator.
     */
    Not(9),

    /**
     * LNot operator.
     */
    LNot(10),

    /**
     * "__real expr" operator.
     */
    Real(11),

    /**
     * "__imag expr" operator.
     */
    Imag(12),

    /**
     * __extension__ marker operator.
     */
    Extension(13),

    /**
     * C++ co_await operator.
     */
    Coawait(14),
    ;

    public companion object {
        @JvmStatic
        public val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            CXUnaryOperatorKind::class.java,
            "fromInt",
            MethodType.methodType(CXUnaryOperatorKind::class.java, Int::class.java)
        )

        @JvmStatic
        public val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            CXUnaryOperatorKind::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        public fun fromInt(`value`: Int): CXUnaryOperatorKind = when (value) {
            Invalid.value -> Invalid
            PostInc.value -> PostInc
            PostDec.value -> PostDec
            PreInc.value -> PreInc
            PreDec.value -> PreDec
            AddrOf.value -> AddrOf
            Deref.value -> Deref
            Plus.value -> Plus
            Minus.value -> Minus
            Not.value -> Not
            LNot.value -> LNot
            Real.value -> Real
            Imag.value -> Imag
            Extension.value -> Extension
            Coawait.value -> Coawait
            else -> error("enum not found")
        }
    }
}
