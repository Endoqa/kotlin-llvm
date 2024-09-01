package llvm

import lib.llvm.*

class Context(
    val C: LLVMContextRef = LLVMContextCreate()
) {

    val types = ContextTypes(C)

    fun appendBasicBlock(function: FunctionValue, name: String): BasicBlock {

        val ref = confined { temp ->
            LLVMAppendBasicBlockInContext(C, function.V, temp.allocateFrom(name))
        }

        return BasicBlock(ref)
    }


    fun createModule(name: String): Module {
        return Module(
            M = confined { temp -> LLVMModuleCreateWithNameInContext(temp.allocateFrom(name), C) }
        )
    }
    
    fun createBuilder() = Builder(LLVMCreateBuilderInContext(C))
}


class ContextTypes internal constructor(val C: LLVMContextRef) {

    val void = VoidType(LLVMVoidTypeInContext(C))
    val bool = IntType(LLVMInt1TypeInContext(C))

    val i8 = IntType(LLVMInt8TypeInContext(C))
    val i16 = IntType(LLVMInt16TypeInContext(C))
    val i32 = IntType(LLVMInt32TypeInContext(C))
    val i64 = IntType(LLVMInt64TypeInContext(C))
    val i128 = IntType(LLVMInt128TypeInContext(C))

    val f16 = FloatType(LLVMHalfTypeInContext(C))
    val f32 = FloatType(LLVMFloatTypeInContext(C))
    val f64 = FloatType(LLVMDoubleTypeInContext(C))

    val ptr = PointerType(LLVMPointerTypeInContext(C, 0u))


}