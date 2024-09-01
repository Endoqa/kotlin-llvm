package llvm

import lib.llvm.LLVMBuildAdd
import lib.llvm.LLVMBuilderRef
import kotlin.reflect.KProperty

sealed class IR<R: Value>() {
    abstract fun BuilderDSL.build(name: String): R
}

class IntAddIR(
    val lhs: IntValue,
    val rhs: IntValue
) : IR<IntValue>() {
    override fun BuilderDSL.build(name: String): IntValue {
        return builder.buildIntAdd(lhs, rhs, name)
    }
}


class BuilderDSL(
    val builder: Builder,
    val B: LLVMBuilderRef
) {


    operator fun FunctionValue.get(idx: UInt): Value {
        return this.getParam(idx)
    }


    operator fun IntValue.plus(rhs: IntValue): IntAddIR {
        return IntAddIR(this, rhs)
    }


    operator fun <T: Value> IR<T>.getValue(thisRef: Any?, property: KProperty<*>): T {
        with(this@BuilderDSL) {
            return this.build(property.name)
        }
    }
    
    
    fun returns(value: Value) = builder.buildReturn(value)
}