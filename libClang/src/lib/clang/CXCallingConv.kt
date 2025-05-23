// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.clang

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.Int

/**
 *
 * Describes the calling convention of a function type
 */
public enum class CXCallingConv(
    public val `value`: Int,
) {
    Default(0),
    C(1),
    X86StdCall(2),
    X86FastCall(3),
    X86ThisCall(4),
    X86Pascal(5),
    AAPCS(6),
    AAPCS_VFP(7),
    X86RegCall(8),
    IntelOclBicc(9),
    Win64(10),

    /**
     * Alias for compatibility with older versions of API.
     */
    X86_64Win64(Win64.value),
    X86_64SysV(11),
    X86VectorCall(12),
    Swift(13),
    PreserveMost(14),
    PreserveAll(15),
    AArch64VectorCall(16),
    SwiftAsync(17),
    AArch64SVEPCS(18),
    M68kRTD(19),
    PreserveNone(20),
    RISCVVectorCall(21),
    Invalid(100),
    Unexposed(200),
    ;

    public companion object {
        @JvmStatic
        public val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            CXCallingConv::class.java,
            "fromInt",
            MethodType.methodType(CXCallingConv::class.java, Int::class.java)
        )

        @JvmStatic
        public val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            CXCallingConv::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        public fun fromInt(`value`: Int): CXCallingConv = when (value) {
            Default.value -> Default
            C.value -> C
            X86StdCall.value -> X86StdCall
            X86FastCall.value -> X86FastCall
            X86ThisCall.value -> X86ThisCall
            X86Pascal.value -> X86Pascal
            AAPCS.value -> AAPCS
            AAPCS_VFP.value -> AAPCS_VFP
            X86RegCall.value -> X86RegCall
            IntelOclBicc.value -> IntelOclBicc
            Win64.value -> Win64
            X86_64Win64.value -> X86_64Win64
            X86_64SysV.value -> X86_64SysV
            X86VectorCall.value -> X86VectorCall
            Swift.value -> Swift
            PreserveMost.value -> PreserveMost
            PreserveAll.value -> PreserveAll
            AArch64VectorCall.value -> AArch64VectorCall
            SwiftAsync.value -> SwiftAsync
            AArch64SVEPCS.value -> AArch64SVEPCS
            M68kRTD.value -> M68kRTD
            PreserveNone.value -> PreserveNone
            RISCVVectorCall.value -> RISCVVectorCall
            Invalid.value -> Invalid
            Unexposed.value -> Unexposed
            else -> error("enum not found")
        }
    }
}
