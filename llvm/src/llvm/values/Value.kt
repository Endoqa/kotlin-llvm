package llvm.values

import lib.llvm.LLVMGetTypeKind
import lib.llvm.LLVMTypeKind
import lib.llvm.LLVMTypeOf
import lib.llvm.LLVMValueRef
import llvm.types.FunctionType
import java.lang.foreign.MemorySegment

sealed class Value(
    val V: LLVMValueRef
) {
    companion object {
        fun from(V: LLVMValueRef): Value {
            require(V != MemorySegment.NULL) { "Cannot create value from NULL pointer" }
            return when (LLVMGetTypeKind(LLVMTypeOf(V))) {
                LLVMTypeKind.VoidTypeKind -> VoidValue(V)
                LLVMTypeKind.HalfTypeKind -> TODO()
                LLVMTypeKind.FloatTypeKind,
                LLVMTypeKind.DoubleTypeKind -> FloatValue(V)

                LLVMTypeKind.X86_FP80TypeKind -> TODO()
                LLVMTypeKind.FP128TypeKind -> TODO()
                LLVMTypeKind.PPC_FP128TypeKind -> TODO()
                LLVMTypeKind.LabelTypeKind -> TODO()
                LLVMTypeKind.IntegerTypeKind -> IntValue(V)
                LLVMTypeKind.FunctionTypeKind -> FunctionValue(FunctionType.from(LLVMTypeOf(V)), V)
                LLVMTypeKind.StructTypeKind -> TODO()
                LLVMTypeKind.ArrayTypeKind -> TODO("123")
                LLVMTypeKind.PointerTypeKind -> PointerValue(V)
                LLVMTypeKind.VectorTypeKind -> VectorValue(V)
                LLVMTypeKind.MetadataTypeKind -> TODO()
                LLVMTypeKind.TokenTypeKind -> TODO()
                LLVMTypeKind.ScalableVectorTypeKind -> TODO()
                LLVMTypeKind.BFloatTypeKind -> TODO()
                LLVMTypeKind.X86_AMXTypeKind -> TODO()
                LLVMTypeKind.TargetExtTypeKind -> TODO()
            }
        }
    }
}
