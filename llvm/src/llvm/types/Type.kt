package llvm.types

import lib.llvm.*
import llvm.values.IntValue
import llvm.values.Value
import java.lang.foreign.ValueLayout
import llvm.types.VoidType
import llvm.types.IntType
import llvm.types.FloatType
import llvm.types.FunctionType

sealed class Type(
    val T: LLVMTypeRef
) {

    val isSized: Boolean get() = LLVMTypeIsSized(T) == 1

    /**
     * Check isSized before use
     *
     * A property that calculates the size of the LLVM type referenced by `T` in bytes.
     * This returns an `IntValue` representing the size.
     */
    val sizeOf: IntValue get() = Value.from(LLVMSizeOf(T)) as IntValue

    /**
     * Returns this type as an AnyTypeEnum, which is just the Type itself in this implementation.
     * This method exists for API compatibility with the Rust implementation.
     */
    fun asAnyTypeEnum(): Type = this

    companion object {
        fun from(T: LLVMTypeRef): Type = when (LLVMGetTypeKind(T)) {
            LLVMTypeKind.VoidTypeKind -> VoidType(T)
            LLVMTypeKind.HalfTypeKind -> TODO()

            LLVMTypeKind.FloatTypeKind,
            LLVMTypeKind.DoubleTypeKind -> FloatType(T)

            LLVMTypeKind.X86_FP80TypeKind -> TODO()
            LLVMTypeKind.FP128TypeKind -> TODO()
            LLVMTypeKind.PPC_FP128TypeKind -> TODO()
            LLVMTypeKind.LabelTypeKind -> TODO()
            LLVMTypeKind.IntegerTypeKind -> IntType(T)
            LLVMTypeKind.FunctionTypeKind -> FunctionType.from(T)
            LLVMTypeKind.StructTypeKind -> TODO()
            LLVMTypeKind.ArrayTypeKind -> TODO()
            LLVMTypeKind.PointerTypeKind -> TODO()
            LLVMTypeKind.VectorTypeKind -> TODO()
            LLVMTypeKind.MetadataTypeKind -> TODO()
            LLVMTypeKind.TokenTypeKind -> TODO()
            LLVMTypeKind.ScalableVectorTypeKind -> TODO()
            LLVMTypeKind.BFloatTypeKind -> TODO()
            LLVMTypeKind.X86_AMXTypeKind -> TODO()
            LLVMTypeKind.TargetExtTypeKind -> TODO()
        }
    }
}
