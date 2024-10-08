package llvm

import lib.llvm.*
import java.lang.foreign.ValueLayout

sealed class Value(
    val V: LLVMValueRef
) {
    companion object {
        fun from(V: LLVMValueRef): Value = when (LLVMGetTypeKind(LLVMTypeOf(V))) {
            LLVMTypeKind.VoidTypeKind -> VoidValue(V)
            LLVMTypeKind.HalfTypeKind -> TODO()
            LLVMTypeKind.FloatTypeKind -> FloatValue(V)
            LLVMTypeKind.DoubleTypeKind -> TODO()
            LLVMTypeKind.X86_FP80TypeKind -> TODO()
            LLVMTypeKind.FP128TypeKind -> TODO()
            LLVMTypeKind.PPC_FP128TypeKind -> TODO()
            LLVMTypeKind.LabelTypeKind -> TODO()
            LLVMTypeKind.IntegerTypeKind -> IntValue(V)
            LLVMTypeKind.FunctionTypeKind -> FunctionValue(FunctionType.from(LLVMTypeOf(V)), V)
            LLVMTypeKind.StructTypeKind -> TODO()
            LLVMTypeKind.ArrayTypeKind -> TODO("123")
            LLVMTypeKind.PointerTypeKind -> PointerValue(V)
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

class PhiValue(V: LLVMValueRef) : Value(V) {


    fun addIncoming(phis: Map<BasicBlock, Value>) {
        confined { temp ->
            val size = phis.size.toLong()
            val values = temp.allocate(ValueLayout.ADDRESS, size)
            val blocks = temp.allocate(ValueLayout.ADDRESS, size)


            phis.entries.forEachIndexed { index, it ->
                val (block, value) = it

                values.setAtIndex(ValueLayout.ADDRESS, index.toLong(), value.V)
                blocks.setAtIndex(ValueLayout.ADDRESS, index.toLong(), block.B)
            }



            LLVMAddIncoming(V, values, blocks, size.toUInt())
        }
    }

}

class VoidValue(V: LLVMValueRef) : Value(V)

class PointerValue(V: LLVMValueRef) : Value(V)

// numbers
class IntValue(V: LLVMValueRef) : Value(V)

class FloatValue(V: LLVMValueRef) : Value(V)

class FunctionValue(val functionType: FunctionType, V: LLVMValueRef) : Value(V) {
    fun getParam(index: UInt) = Value.from(LLVMGetParam(V, index))


    fun appendBasicBlock(basicBlock: BasicBlock) {
        LLVMAppendExistingBasicBlock(V, basicBlock.B)
    }

}