// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.llvm

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.Int

public enum class LLVMTypeKind(
    public val `value`: Int,
) {
    VoidTypeKind(0),

    /**
     * < type with no size
     */
    HalfTypeKind(1),

    /**
     * < 16 bit floating point type
     */
    FloatTypeKind(2),

    /**
     * < 32 bit floating point type
     */
    DoubleTypeKind(3),

    /**
     * < 64 bit floating point type
     */
    X86_FP80TypeKind(4),

    /**
     * < 80 bit floating point type (X87)
     */
    FP128TypeKind(5),

    /**
     * < 128 bit floating point type (112-bit mantissa)
     */
    PPC_FP128TypeKind(6),

    /**
     * < 128 bit floating point type (two 64-bits)
     */
    LabelTypeKind(7),

    /**
     * < Labels
     */
    IntegerTypeKind(8),

    /**
     * < Arbitrary bit width integers
     */
    FunctionTypeKind(9),

    /**
     * < Functions
     */
    StructTypeKind(10),

    /**
     * < Structures
     */
    ArrayTypeKind(11),

    /**
     * < Arrays
     */
    PointerTypeKind(12),

    /**
     * < Pointers
     */
    VectorTypeKind(13),

    /**
     * < Fixed width SIMD vector type
     */
    MetadataTypeKind(14),

    /**
     * 15 previously used by LLVMX86_MMXTypeKind
     */
    TokenTypeKind(16),

    /**
     * < Tokens
     */
    ScalableVectorTypeKind(17),

    /**
     * < Scalable SIMD vector type
     */
    BFloatTypeKind(18),

    /**
     * < 16 bit brain floating point type
     */
    X86_AMXTypeKind(19),

    /**
     * < X86 AMX
     */
    TargetExtTypeKind(20),
    ;

    public companion object {
        @JvmStatic
        public val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            LLVMTypeKind::class.java,
            "fromInt",
            MethodType.methodType(LLVMTypeKind::class.java, Int::class.java)
        )

        @JvmStatic
        public val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            LLVMTypeKind::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        public fun fromInt(`value`: Int): LLVMTypeKind = when (value) {
            VoidTypeKind.value -> VoidTypeKind
            HalfTypeKind.value -> HalfTypeKind
            FloatTypeKind.value -> FloatTypeKind
            DoubleTypeKind.value -> DoubleTypeKind
            X86_FP80TypeKind.value -> X86_FP80TypeKind
            FP128TypeKind.value -> FP128TypeKind
            PPC_FP128TypeKind.value -> PPC_FP128TypeKind
            LabelTypeKind.value -> LabelTypeKind
            IntegerTypeKind.value -> IntegerTypeKind
            FunctionTypeKind.value -> FunctionTypeKind
            StructTypeKind.value -> StructTypeKind
            ArrayTypeKind.value -> ArrayTypeKind
            PointerTypeKind.value -> PointerTypeKind
            VectorTypeKind.value -> VectorTypeKind
            MetadataTypeKind.value -> MetadataTypeKind
            TokenTypeKind.value -> TokenTypeKind
            ScalableVectorTypeKind.value -> ScalableVectorTypeKind
            BFloatTypeKind.value -> BFloatTypeKind
            X86_AMXTypeKind.value -> X86_AMXTypeKind
            TargetExtTypeKind.value -> TargetExtTypeKind
            else -> error("enum not found")
        }
    }
}
