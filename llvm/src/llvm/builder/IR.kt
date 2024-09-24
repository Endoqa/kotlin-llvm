package llvm.builder

import llvm.IntValue
import llvm.PointerValue
import llvm.Type
import llvm.Value

sealed class IR<R : Value>() {
    abstract fun BuilderDSL.build(name: String): R
}

class AllocaIR(
    val type: Type
) : IR<PointerValue>() {
    override fun BuilderDSL.build(name: String): PointerValue {
        return builder.alloca(type, name)
    }
}

class IntAddIR(
    val lhs: IntValue,
    val rhs: IntValue
) : IR<IntValue>() {
    override fun BuilderDSL.build(name: String): IntValue {
        return builder.buildIntAdd(lhs, rhs, name)
    }
}

class GEPIR(
    val inbound: Type,
    val v: PointerValue,
    val indecies: List<Value>
) : IR<PointerValue>() {
    override fun BuilderDSL.build(name: String): PointerValue {
        return builder.gep(inbound, v, indecies, name)
    }

}