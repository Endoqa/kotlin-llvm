package llvm.builder

import lib.llvm.LLVMBuilderRef
import lib.llvm.LLVMValueRef
import llvm.*
import kotlin.reflect.KProperty


class BuilderDSL(
    val builder: Builder,
    val B: LLVMBuilderRef
) {


    // invoke
    operator fun <T : Value> IR<T>.getValue(thisRef: Any?, property: KProperty<*>): T {
        with(this@BuilderDSL) {
            return this.build(property.name)
        }
    }


    operator fun FunctionValue.get(idx: UInt): Value {
        return this.getParam(idx)
    }


    operator fun IntValue.plus(rhs: IntValue): IntAddIR {
        return IntAddIR(this, rhs)
    }

    fun alloca(type: Type): AllocaIR {
        return AllocaIR(type)
    }

    fun gep(inbound: Type, value: PointerValue, indecies: List<Value> = emptyList()): GEPIR {
        return GEPIR(inbound, value, indecies)
    }


    fun gep(inbound: Type, value: PointerValue, vararg indecies: Value = emptyArray()): GEPIR {
        return GEPIR(inbound, value, indecies.toList())
    }


    fun store(value: Value, ptr: PointerValue): Value {
        return builder.store(value, ptr)
    }

    infix fun <T : Value> T.align(bytes: UInt): T {
        return builder.setAlignment(this, bytes)
    }

    infix fun <T : Value> T.named(name: String): T {
        return builder.setName(this, name)
    }


    fun returns(value: Value) = builder.buildReturn(value)
}