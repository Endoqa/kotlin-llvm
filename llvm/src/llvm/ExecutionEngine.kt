package llvm

import lib.llvm.LLVMExecutionEngineRef
import lib.llvm.LLVMGetFunctionAddress
import java.lang.foreign.MemorySegment

class ExecutionEngine(
    val EE: LLVMExecutionEngineRef
) {


    fun getFunction(name: String): MemorySegment {
        val addr = confined { temp ->
            LLVMGetFunctionAddress(EE, temp.allocateFrom(name))
        }

        return MemorySegment.ofAddress(addr.toLong())
    }

}