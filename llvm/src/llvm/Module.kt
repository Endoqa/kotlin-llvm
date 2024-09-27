package llvm


import lib.llvm.*
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout


class Module(
    val M: LLVMModuleRef
) {

    val intrinsics = Intrinsics(this)

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

data class IntrinsicDeclaration(
    val id: UInt,
    val params: List<Type>,
)

@JvmInline
value class Intrinsics(val module: Module) {
    private val M get() = module.M


    operator fun get(id: UInt, params: List<Type>): Value {
        val V = confined { temp ->
            val types = temp.allocate(ValueLayout.ADDRESS, params.size.toLong())

            params.forEachIndexed { i, it ->
                types.setAtIndex(ValueLayout.ADDRESS, i.toLong(), it.T)
            }


            LLVMGetIntrinsicDeclaration(M, id, types, params.size.toULong())
        }

        return Value.from(V)
    }

    operator fun get(decl: IntrinsicDeclaration): Value {
        return this[decl.id, decl.params]
    }

    fun lookup(name: String): UInt {
        return confined { temp ->
            val str = temp.allocateFrom(name)
            LLVMLookupIntrinsicID(str, str.byteSize().toULong())
        }
    }

}