package llvm.values

import lib.llvm.LLVMAddCase
import lib.llvm.LLVMValueRef
import llvm.BasicBlock

class SwitchValue(V: LLVMValueRef, val numCases: UInt) : Value(V) {
    fun addCase(condition: Value, block: BasicBlock) {
        LLVMAddCase(V, condition.V, block.B)
    }

    fun addCase(case: SwitchCase) {
        addCase(case.condition, case.block)
    }
}