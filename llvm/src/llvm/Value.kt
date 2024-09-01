package llvm

import lib.llvm.*

sealed class Value(
    val V: LLVMValueRef
) {
    companion object {
        fun from(V: LLVMValueRef): Value = when (LLVMGetTypeKind(LLVMTypeOf(V))) {
            LLVMTypeKind.VoidTypeKind -> VoidValue(V)
            LLVMTypeKind.HalfTypeKind -> TODO()
            LLVMTypeKind.FloatTypeKind -> TODO()
            LLVMTypeKind.DoubleTypeKind -> TODO()
            LLVMTypeKind.X86_FP80TypeKind -> TODO()
            LLVMTypeKind.FP128TypeKind -> TODO()
            LLVMTypeKind.PPC_FP128TypeKind -> TODO()
            LLVMTypeKind.LabelTypeKind -> TODO()
            LLVMTypeKind.IntegerTypeKind -> IntValue(V)
            LLVMTypeKind.FunctionTypeKind -> FunctionValue(FunctionType.from(LLVMTypeOf(V)), V)
            LLVMTypeKind.StructTypeKind -> TODO()
            LLVMTypeKind.ArrayTypeKind -> TODO()
            LLVMTypeKind.PointerTypeKind -> TODO()
            LLVMTypeKind.VectorTypeKind -> TODO()
            LLVMTypeKind.MetadataTypeKind -> TODO()
            LLVMTypeKind.X86_MMXTypeKind -> TODO()
            LLVMTypeKind.TokenTypeKind -> TODO()
            LLVMTypeKind.ScalableVectorTypeKind -> TODO()
            LLVMTypeKind.BFloatTypeKind -> TODO()
            LLVMTypeKind.X86_AMXTypeKind -> TODO()
            LLVMTypeKind.TargetExtTypeKind -> TODO()
        }
    }
}

class VoidValue(V: LLVMValueRef) : Value(V)

class IntValue(V: LLVMValueRef) : Value(V)

class FunctionValue(val functionType: FunctionType, V: LLVMValueRef) : Value(V) {
    fun getParam(index: UInt) = Value.from(LLVMGetParam(V, index))
}