package llvm


import lib.llvm.*
import llvm.values.FunctionValue
import llvm.values.Value
import java.lang.foreign.Arena
import java.lang.foreign.ValueLayout


class Module(
    val M: LLVMModuleRef
) {

    fun setDataLayout(str: String) {
        confined { temp -> LLVMSetDataLayout(M, temp.allocateFrom(str)) }
    }

    fun addGlobal(type: Type, name: String): Value {
        return Value.from(confined { temp -> LLVMAddGlobal(M, type.T, temp.allocateFrom(name)) })
    }

    fun addFunction(name: String, type: FunctionType): FunctionValue {
        val f = confined { temp ->
            LLVMAddFunction(M, temp.allocateFrom(name), type.T)
        }

        return FunctionValue(type, f)
    }

    fun createJITExecutionEngine(optLvl: LLVMCodeGenOptLevel = LLVMCodeGenOptLevel.Default): ExecutionEngine {

        Target.initialize()


        val e = confined { temp ->
            val out = Arena.global().allocate(ValueLayout.ADDRESS) // todo
            val outErr = temp.allocate(ValueLayout.ADDRESS)

            val rst = LLVMCreateJITCompilerForModule(
                out,
                M,
                optLvl.value.toUInt(),
                outErr
            )
            ExecutionEngine(out.getAtIndex(ValueLayout.ADDRESS, 0))
        }

        return e
    }


    fun printToString(): String? {
        val s = LLVMPrintModuleToString(M)

        val str = s.getString(0)

        LLVMDisposeMessage(s)

        return str
    }

}
