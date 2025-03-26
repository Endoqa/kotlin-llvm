package llvm

import java.lang.foreign.MemorySegment
import lib.llvm.LLVMBasicBlockAsValue
import lib.llvm.LLVMBasicBlockRef
import lib.llvm.LLVMDeleteBasicBlock
import lib.llvm.LLVMGetBasicBlockName
import lib.llvm.LLVMGetBasicBlockParent
import lib.llvm.LLVMGetNextBasicBlock
import lib.llvm.LLVMGetPreviousBasicBlock
import lib.llvm.LLVMMoveBasicBlockAfter
import lib.llvm.LLVMMoveBasicBlockBefore
import lib.llvm.LLVMSetValueName
import lib.llvm.LLVMTypeOf

class BasicBlock(
    val B: LLVMBasicBlockRef
) {
    infix fun moveAfter(after: BasicBlock) {
        LLVMMoveBasicBlockAfter(B, after.B)
    }

    infix fun moveBefore(after: BasicBlock) {
        LLVMMoveBasicBlockBefore(B, after.B)
    }

    var name: String
        get() = LLVMGetBasicBlockName(B).getString(0)
        set(value) = confined { temp ->
            val valueRef = LLVMBasicBlockAsValue(B)
            LLVMSetValueName(valueRef, temp.allocateFrom(value))
        }


    val parent: FunctionValue get() {
        val parentRef = LLVMGetBasicBlockParent(B)
        return FunctionValue(FunctionType.from(LLVMTypeOf(parentRef)), parentRef)
    }

    val previous: BasicBlock? get() {
        val prevRef = LLVMGetPreviousBasicBlock(B)
        return if (prevRef == MemorySegment.NULL) null else BasicBlock(prevRef)
    }

    val next: BasicBlock? get() {
        val nextRef = LLVMGetNextBasicBlock(B)
        return if (nextRef == MemorySegment.NULL) null else BasicBlock(nextRef)
    }

    fun delete() {
        LLVMDeleteBasicBlock(B)
    }
}
