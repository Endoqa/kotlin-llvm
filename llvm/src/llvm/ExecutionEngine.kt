package llvm

import lib.llvm.*
import llvm.values.FunctionValue
import llvm.values.Value
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

class ExecutionEngine(
    val EE: LLVMExecutionEngineRef
) {


    fun getFunctionAddress(name: String): MemorySegment {
        val addr = confined { temp ->
            LLVMGetFunctionAddress(EE, temp.allocateFrom(name))
        }

        require(addr != 0uL) { "Could not find function $name" }

        return MemorySegment.ofAddress(addr.toLong())
    }

    fun getFunctionValue(name: String): FunctionValue {
        val code: LLVMValueRef = confined { temp ->
            val funcPtr = temp.allocate(ValueLayout.ADDRESS)
            LLVMFindFunction(EE, temp.allocateFrom(name), funcPtr)

            require(funcPtr != MemorySegment.NULL) { "Could not find function $name" }
            funcPtr.getAtIndex(ValueLayout.ADDRESS, 0)
        }

        val func = Value.from(code)
        require(func is FunctionValue)
        return func
    }


    fun addGlobalMapping(value: Value, addr: MemorySegment) {
        LLVMAddGlobalMapping(EE, value.V, addr)
    }

    fun addModule(module: Module) {
        LLVMAddModule(EE, module.M)
    }

    fun freeFunctionMachineCode(function: FunctionValue) {
        LLVMFreeMachineCodeForFunction(EE, function.V)
    }

    fun runStaticConstructors() {
        LLVMRunStaticConstructors(EE)
    }

    fun runStaticDestructors() {
        LLVMRunStaticDestructors(EE)
    }


}