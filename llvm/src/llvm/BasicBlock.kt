package llvm

import lib.llvm.LLVMBasicBlockRef
import lib.llvm.LLVMMoveBasicBlockAfter
import lib.llvm.LLVMMoveBasicBlockBefore

class BasicBlock(
    val B: LLVMBasicBlockRef
) {
    infix fun moveAfter(after: BasicBlock) {
        LLVMMoveBasicBlockAfter(B, after.B)
    }

    infix fun moveBefore(after: BasicBlock) {
        LLVMMoveBasicBlockBefore(B, after.B)
    }
}