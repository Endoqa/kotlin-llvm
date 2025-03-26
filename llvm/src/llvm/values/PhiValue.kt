package llvm.values

import lib.llvm.LLVMAddIncoming
import lib.llvm.LLVMValueRef
import llvm.BasicBlock
import llvm.confined
import java.lang.foreign.ValueLayout

class PhiValue(V: LLVMValueRef) : Value(V) {

    fun asTyped() = Value.from(V)


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
