package llvm.builder

import llvm.*

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

    internal var irVal: R? = null
}

class GlobalStrIR(
    val str: String
) : IR<PointerValue>() {
    context(BuilderDSL)
    override fun build(): PointerValue {
        return builder.globalStr(str, name)
    }

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

class SubIR(
    private val lhs: Value,
    private val rhs: Value
) : IR<Value>() {

    context(BuilderDSL)
    override fun build(): Value {
        return when {
            lhs is IntValue && rhs is IntValue -> {
                builder.sub(lhs, rhs, name)
            }

            lhs is FloatValue && rhs is FloatValue -> {
                builder.fsub(lhs, rhs, name)
            }

            else -> throw IllegalArgumentException("Values are expected to be an Int/Float")
        }
    }
}

class MulIR(
    val lhs: Value,
    val rhs: Value
) : IR<Value>() {

    context(BuilderDSL)
    override fun build(): Value {
        return when {
            lhs is IntValue && rhs is IntValue -> {
                builder.mul(lhs, rhs, name)
            }

            lhs is FloatValue && rhs is FloatValue -> {
                builder.fmul(lhs, rhs, name)
            }

            else -> throw IllegalArgumentException("Values are expected to be an Int/Float")
        }
    }
}

class UDivIR(
    val lhs: IntValue,
    val rhs: IntValue
) : IR<Value>() {

    context(BuilderDSL)
    override fun build(): IntValue {
        return builder.udiv(lhs, rhs, name)
    }
}

class SDivIR(
    val lhs: IntValue,
    val rhs: IntValue
) : IR<Value>() {

    context(BuilderDSL)
    override fun build(): IntValue {
        return builder.sdiv(lhs, rhs, name)
    }
}

class FDivIR(
    val lhs: FloatValue,
    val rhs: FloatValue
) : IR<Value>() {

    context(BuilderDSL)
    override fun build(): FloatValue {
        return builder.fdiv(lhs, rhs, name)
    }
}

class URemIR(
    val lhs: IntValue,
    val rhs: IntValue
) : IR<Value>() {

    context(BuilderDSL)
    override fun build(): IntValue {
        return builder.urem(lhs, rhs, name)
    }
}

class SRemIR(
    val lhs: IntValue,
    val rhs: IntValue
) : IR<Value>() {

    context(BuilderDSL)
    override fun build(): IntValue {
        return builder.srem(lhs, rhs, name)
    }
}

class FRemIR(
    val lhs: FloatValue,
    val rhs: FloatValue
) : IR<Value>() {

    context(BuilderDSL)
    override fun build(): FloatValue {
        return builder.frem(lhs, rhs, name)
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

class IsNullIR(
    val value: Value
) : IR<IntValue>() {
    context(BuilderDSL)
    override fun build(): IntValue {
        return builder.isNull(value, name)
    }
}

class IsNotNullIR(
    val value: Value
) : IR<IntValue>() {
    context(BuilderDSL)
    override fun build(): IntValue {
        return builder.isNotNull(value, name)
    }
}

data class CondBrDestPair(
    val then: BasicBlock,
    val not: BasicBlock
)
