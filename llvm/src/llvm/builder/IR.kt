package llvm.builder

import llvm.BasicBlock
import llvm.FunctionType
import llvm.FunctionValue
import llvm.IntValue
import llvm.PointerValue
import llvm.Type
import llvm.Value

sealed class IR<R : Value>() {

    var name: String = ""
    var alignment: UInt = 0u

    context(BuilderDSL)
    fun buildIR(): R {
        val v = build()
        v align alignment
        return v
    }


    context(BuilderDSL)
    abstract fun build(): R
}

class AllocaIR(
    val type: Type
) : IR<PointerValue>() {

    context(BuilderDSL)
    override fun build(): PointerValue {
        return builder.alloca(type, name)
    }
}

class IntAddIR(
    val lhs: IntValue,
    val rhs: IntValue
) : IR<IntValue>() {

    context(BuilderDSL)
    override fun build(): IntValue {
        return builder.buildIntAdd(lhs, rhs, name)
    }
}

class OrIR(
    val lhs: IntValue,
    val rhs: IntValue
) : IR<IntValue>() {

    context(BuilderDSL)
    override fun build(): IntValue {
        return builder.or(lhs, rhs, name)
    }
}


class GEPIR(
    val inbound: Type,
    val v: PointerValue,
    val indecies: List<Value>
) : IR<PointerValue>() {

    context(BuilderDSL)
    override fun build(): PointerValue {
        return builder.gep(inbound, v, indecies, name)
    }

}

class LoadIR(
    val value: PointerValue,
    val type: Type
) : IR<Value>() {
    context(BuilderDSL)
    override fun build(): Value {
        return builder.load(type, value, name)
    }

}

class CallIR(
    val type: FunctionType,
    val fn: Value,
    val params: List<Value>
) : IR<Value>() {
    context(BuilderDSL)
    override fun build(): Value {
        return when (fn) {
            is PointerValue -> builder.call(type, fn, params, name)
            is FunctionValue -> builder.call(type, fn, params, name)
            else -> error("Unsupport value for call")
        }
    }

}

data class CondBrDestPair(
    val then: BasicBlock,
    val not: BasicBlock
)
