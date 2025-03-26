package llvm.values

import lib.llvm.LLVMAppendExistingBasicBlock
import lib.llvm.LLVMGetParam
import lib.llvm.LLVMValueRef
import llvm.BasicBlock
import llvm.FunctionType

class FunctionValue(val functionType: FunctionType, V: LLVMValueRef) : Value(V) {
    fun getParam(index: UInt) = Value.from(LLVMGetParam(V, index))


    fun appendBasicBlock(basicBlock: BasicBlock) {
        LLVMAppendExistingBasicBlock(V, basicBlock.B)
    }
}
