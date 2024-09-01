package llvm


import lib.llvm.LLVMAddFunction
import lib.llvm.LLVMCodeGenOptLevel
import lib.llvm.LLVMCreateJITCompilerForModule
import lib.llvm.LLVMModuleRef
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout


class Module(
    val M: LLVMModuleRef
) {


    fun addFunction(name: String, type: FunctionType): FunctionValue {
        val f = confined { temp ->
            LLVMAddFunction(M, temp.allocateFrom(name), type.T)
        }

        return FunctionValue(type, f)
    }

    fun createJITExecutionEngine(optLvl: LLVMCodeGenOptLevel = LLVMCodeGenOptLevel.Default): ExecutionEngine {
        val e = confined { temp ->
            val out = Arena.global().allocate(ValueLayout.ADDRESS) // todo
            val outErr = temp.allocate(ValueLayout.ADDRESS)

            LLVMCreateJITCompilerForModule(
                out,
                M,
                optLvl.value.toUInt(),
                outErr
            )
            ExecutionEngine(out.getAtIndex(ValueLayout.ADDRESS, 0)) //todo, deref or not?
        }

        return e
    }

}