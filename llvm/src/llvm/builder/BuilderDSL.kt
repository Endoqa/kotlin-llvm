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

        if (this.name != "") {
            this.name = property.name
        }

        with(this@BuilderDSL) {
            return buildIR()
        }
    }


    operator fun FunctionValue.get(idx: UInt): Value {
        return this.getParam(idx)
    }


    operator fun IntValue.plus(rhs: IntValue): IntAddIR {
        return IntAddIR(this, rhs)
    }


    infix fun IntValue.or(rhs: IntValue): OrIR {
        return OrIR(this, rhs)
    }

    fun load(type: Type, value: PointerValue): LoadIR {
        return LoadIR(value, type)
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

    fun call(fn: FunctionValue, type: FunctionType, vararg params: Value): CallIR {
        return callIR(type, fn, params.toList())
    }

    fun call(fn: PointerValue, type: FunctionType, vararg params: Value): CallIR {
        return callIR(type, fn, params.toList())
    }

    fun br(dest: BasicBlock): Value {
        return builder.br(dest)
    }


    fun dest(then: BasicBlock, not: BasicBlock): CondBrDestPair {
        return CondBrDestPair(then, not)
    }

    infix fun IntValue.br(destPair: CondBrDestPair): Value {
        return builder.condBr(this, destPair.then, destPair.not)
    }


    private fun callIR(type: FunctionType, value: Value, params: List<Value>): CallIR {
        return CallIR(type, value, params)
    }

    infix fun <T : Value> T.align(bytes: UInt): T {
        return builder.setAlignment(this, bytes)
    }

    infix fun <T : Value> T.named(name: String): T {
        return builder.setName(this, name)
    }


    fun returns(value: Value) = builder.buildReturn(value)
}