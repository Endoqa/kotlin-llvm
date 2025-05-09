// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.clang

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.Int

/**
 *
 * Describes the kind of binary operators.
 */
public enum class CXBinaryOperatorKind(
    public val `value`: Int,
) {
    /**
     * This value describes cursors which are not binary operators.
     */
    Invalid(0),

    /**
     * C++ Pointer - to - member operator.
     */
    PtrMemD(1),

    /**
     * C++ Pointer - to - member operator.
     */
    PtrMemI(2),

    /**
     * Multiplication operator.
     */
    Mul(3),

    /**
     * Division operator.
     */
    Div(4),

    /**
     * Remainder operator.
     */
    Rem(5),

    /**
     * Addition operator.
     */
    Add(6),

    /**
     * Subtraction operator.
     */
    Sub(7),

    /**
     * Bitwise shift left operator.
     */
    Shl(8),

    /**
     * Bitwise shift right operator.
     */
    Shr(9),

    /**
     * C++ three-way comparison (spaceship) operator.
     */
    Cmp(10),

    /**
     * Less than operator.
     */
    LT(11),

    /**
     * Greater than operator.
     */
    GT(12),

    /**
     * Less or equal operator.
     */
    LE(13),

    /**
     * Greater or equal operator.
     */
    GE(14),

    /**
     * Equal operator.
     */
    EQ(15),

    /**
     * Not equal operator.
     */
    NE(16),

    /**
     * Bitwise AND operator.
     */
    And(17),

    /**
     * Bitwise XOR operator.
     */
    Xor(18),

    /**
     * Bitwise OR operator.
     */
    Or(19),

    /**
     * Logical AND operator.
     */
    LAnd(20),

    /**
     * Logical OR operator.
     */
    LOr(21),

    /**
     * Assignment operator.
     */
    Assign(22),

    /**
     * Multiplication assignment operator.
     */
    MulAssign(23),

    /**
     * Division assignment operator.
     */
    DivAssign(24),

    /**
     * Remainder assignment operator.
     */
    RemAssign(25),

    /**
     * Addition assignment operator.
     */
    AddAssign(26),

    /**
     * Subtraction assignment operator.
     */
    SubAssign(27),

    /**
     * Bitwise shift left assignment operator.
     */
    ShlAssign(28),

    /**
     * Bitwise shift right assignment operator.
     */
    ShrAssign(29),

    /**
     * Bitwise AND assignment operator.
     */
    AndAssign(30),

    /**
     * Bitwise XOR assignment operator.
     */
    XorAssign(31),

    /**
     * Bitwise OR assignment operator.
     */
    OrAssign(32),

    /**
     * Comma operator.
     */
    Comma(33),
    ;

    public companion object {
        @JvmStatic
        public val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            CXBinaryOperatorKind::class.java,
            "fromInt",
            MethodType.methodType(CXBinaryOperatorKind::class.java, Int::class.java)
        )

        @JvmStatic
        public val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            CXBinaryOperatorKind::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        public fun fromInt(`value`: Int): CXBinaryOperatorKind = when (value) {
            Invalid.value -> Invalid
            PtrMemD.value -> PtrMemD
            PtrMemI.value -> PtrMemI
            Mul.value -> Mul
            Div.value -> Div
            Rem.value -> Rem
            Add.value -> Add
            Sub.value -> Sub
            Shl.value -> Shl
            Shr.value -> Shr
            Cmp.value -> Cmp
            LT.value -> LT
            GT.value -> GT
            LE.value -> LE
            GE.value -> GE
            EQ.value -> EQ
            NE.value -> NE
            And.value -> And
            Xor.value -> Xor
            Or.value -> Or
            LAnd.value -> LAnd
            LOr.value -> LOr
            Assign.value -> Assign
            MulAssign.value -> MulAssign
            DivAssign.value -> DivAssign
            RemAssign.value -> RemAssign
            AddAssign.value -> AddAssign
            SubAssign.value -> SubAssign
            ShlAssign.value -> ShlAssign
            ShrAssign.value -> ShrAssign
            AndAssign.value -> AndAssign
            XorAssign.value -> XorAssign
            OrAssign.value -> OrAssign
            Comma.value -> Comma
            else -> error("enum not found")
        }
    }
}
