package llvm

import lib.llvm.*
import llvm.builder.BuilderDSL

class Context(
    val C: LLVMContextRef = LLVMContextCreate()
) {

    companion object {
        val Global by lazy { Context(LLVMGetGlobalContext()) }
    }


    val types = ContextTypes(this)
    val values = ContextValues(this)

    fun appendBasicBlock(function: FunctionValue, name: String): BasicBlock {

        val ref = confined { temp ->
            LLVMAppendBasicBlockInContext(C, function.V, temp.allocateFrom(name))
        }

        return BasicBlock(ref)
    }

    fun createBasicBlock(name: String): BasicBlock {
        val ref = confined { temp ->
            LLVMCreateBasicBlockInContext(C, temp.allocateFrom(name))
        }

        return BasicBlock(ref)
    }


    fun createModule(name: String): Module {
        return Module(
            M = confined { temp -> LLVMModuleCreateWithNameInContext(temp.allocateFrom(name), C) }
        )
    }


    fun createBuilder() = Builder(LLVMCreateBuilderInContext(C))

    fun builder(block: BasicBlock, action: BuilderDSL.() -> Unit) {
        createBuilder().use {
            it.positionAtEnd(block)
            it.invoke(action)
        }
    }
}


class ContextTypes internal constructor(val c: Context) {

    private val C = c.C

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


    fun struct(elements: List<Type>): StructType {
        return StructType(C, elements)
    }

    fun struct(name: String, elements: List<Type>): StructType {
        return StructType(C, name, elements)
    }


}

class ContextValues internal constructor(val c: Context) {
    val C = c.C


    val nullptr get() = PointerValue(LLVMConstNull(c.types.ptr.T))

    fun bool(value: Boolean) = IntValue(LLVMConstInt(c.types.bool.T, if (value) 1u else 0u, 1))

    fun i8(value: Byte) = IntValue(LLVMConstInt(c.types.i8.T, value.toULong(), 1))
    fun i8(value: UByte) = IntValue(LLVMConstInt(c.types.i8.T, value.toULong(), 0))

    fun i16(value: Short) = IntValue(LLVMConstInt(c.types.i16.T, value.toULong(), 1))
    fun i16(value: UShort) = IntValue(LLVMConstInt(c.types.i16.T, value.toULong(), 0))

    fun i32(value: Int) = IntValue(LLVMConstInt(c.types.i32.T, value.toULong(), 1))
    fun i32(value: UInt) = IntValue(LLVMConstInt(c.types.i32.T, value.toULong(), 0))

    fun i64(value: Long) = IntValue(LLVMConstInt(c.types.i64.T, value.toULong(), 1))
    fun i64(value: ULong) = IntValue(LLVMConstInt(c.types.i64.T, value.toULong(), 0))

    fun i128(value: Long) = IntValue(LLVMConstInt(c.types.i128.T, value.toULong(), 1))
    fun i128(value: ULong) = IntValue(LLVMConstInt(c.types.i128.T, value.toULong(), 0))

    fun f16(value: Float) = FloatValue(LLVMConstReal(c.types.f16.T, value.toDouble()))
    fun f32(value: Float) = FloatValue(LLVMConstReal(c.types.f32.T, value.toDouble()))
    fun f64(value: Double) = FloatValue(LLVMConstReal(c.types.f64.T, value))

}