package llvm

import lib.llvm.*
import llvm.builder.BuilderDSL
import llvm.types.VoidType
import llvm.values.FloatValue
import llvm.values.FunctionValue
import llvm.values.IntValue
import llvm.values.*
import llvm.types.*
import java.lang.foreign.MemorySegment

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

    fun insertBasicBlockAfter(basicBlock: BasicBlock, name: String): BasicBlock {
        val nextBasicBlock = basicBlock.next
        return if (nextBasicBlock != null) {
            prependBasicBlock(nextBasicBlock, name)
        } else {
            val parentFn = basicBlock.parent
            appendBasicBlock(parentFn, name)
        }
    }

    fun prependBasicBlock(basicBlock: BasicBlock, name: String): BasicBlock {
        val ref = confined { temp ->
            LLVMInsertBasicBlockInContext(C, basicBlock.B, temp.allocateFrom(name))
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

    /**
     * Creates an enum attribute.
     *
     * Example:
     * ```
     * val context = Context()
     * val enumAttribute = context.createEnumAttribute(0u, 10u)
     *
     * assert(enumAttribute.isEnum())
     * ```
     */
    fun createEnumAttribute(kindId: UInt, value: ULong): Attribute {
        return Attribute(LLVMCreateEnumAttribute(C, kindId, value))
    }

    /**
     * Creates a string attribute.
     *
     * Example:
     * ```
     * val context = Context()
     * val stringAttribute = context.createStringAttribute("my_key", "my_val")
     *
     * assert(stringAttribute.isString())
     * ```
     */
    fun createStringAttribute(key: String, value: String): Attribute {
        return confined { temp ->
            val keyPtr = temp.allocateFrom(key)
            val valuePtr = temp.allocateFrom(value)

            Attribute(
                LLVMCreateStringAttribute(
                    C,
                    keyPtr,
                    key.length.toUInt(),
                    valuePtr,
                    value.length.toUInt()
                )
            )
        }
    }

    /**
     * Gets a named StructType from this Context.
     */
    fun getStructType(name: String): StructType? {
        val typeRef = confined { temp ->
            LLVMGetTypeByName2(C, temp.allocateFrom(name))
        }
        return if (typeRef == MemorySegment.NULL) null else StructType(typeRef)
    }

    /**
     * Creates a type attribute.
     *
     * Example:
     * ```
     * val context = Context()
     * val kindId = Attribute.getNamedEnumKindId("sret")
     * val typeAttribute = context.createTypeAttribute(
     *     kindId,
     *     context.types.i32
     * )
     *
     * assert(typeAttribute.isType())
     * ```
     */
    fun createTypeAttribute(kindId: UInt, typeRef: Type): Attribute {
        return Attribute(LLVMCreateTypeAttribute(C, kindId, typeRef.T))
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
