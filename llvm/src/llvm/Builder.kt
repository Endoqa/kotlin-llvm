package llvm

import lib.llvm.*

class Builder(
    val B: LLVMBuilderRef
) {

    fun positionAtEnd(block: BasicBlock) {
        LLVMPositionBuilderAtEnd(B, block.B)
    }


    fun buildIntAdd(lhs: IntValue, rhs: IntValue, name: String): IntValue {
        val r = confined { temp ->
            LLVMBuildAdd(B, lhs.V, rhs.V, temp.allocateFrom(name))
        }

        val v = Value.from(r)
        require(v is IntValue)
        return v
    }


    fun buildReturn(ret: Value): Value {
        return Value.from(LLVMBuildRet(B, ret.V))
    }

    fun buildReturn(): Value {
        return Value.from(LLVMBuildRetVoid(B))
    }


    inline operator fun invoke(builder: BuilderDSL.() -> Unit) {
        BuilderDSL(this, B).apply(builder)
    }

}