package llvm.values

import lib.llvm.*
import java.lang.foreign.MemorySegment

class VectorValue(V: LLVMValueRef) : Value(V) {

    val isConst get() = LLVMIsConstant(V) == 1
    val isConstantVector get() = LLVMIsAConstantVector(V) != MemorySegment.NULL
    val isConstantDataVector get() = LLVMIsAConstantDataVector(V) != MemorySegment.NULL

    fun getElementAsConstant(index: UInt): Value {
        return from(LLVMGetElementAsConstant(V, index))
    }

}