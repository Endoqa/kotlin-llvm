package llvm

import lib.llvm.*
import llvm.builder.BuilderDSL
import java.lang.foreign.Arena
import java.lang.foreign.ValueLayout

class Builder(
    val B: LLVMBuilderRef
) : AutoCloseable {

    fun positionAtEnd(block: BasicBlock) {
        LLVMPositionBuilderAtEnd(B, block.B)
    }

    private inline fun <T : Value> buildAsWith(action: Arena.() -> LLVMValueRef): T {
        val V = confined { action(it) }

        return Value.from(V) as T
    }

    private inline fun <T : Value> build(action: () -> LLVMValueRef): T {
        return Value.from(action()) as T
    }

    private inline fun <V : Value> buildWith(action: Arena.() -> V): V {
        return confined { action(it) }
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

    fun alloca(type: Type, name: String): PointerValue {
        return Value.from(confined { temp ->
            LLVMBuildAlloca(B, type.T, temp.allocateFrom(name))
        }) as PointerValue
    }

    fun gep(inbound: Type, value: PointerValue, indecies: List<Value> = emptyList(), name: String): PointerValue {
        val V = confined { temp ->
            val arr = temp.allocate(ValueLayout.ADDRESS, indecies.size.toLong())

            indecies.forEachIndexed { i, it ->
                arr.setAtIndex(ValueLayout.ADDRESS, i.toLong(), it.V)
            }

            LLVMBuildInBoundsGEP2(B, inbound.T, value.V, arr, indecies.size.toUInt(), temp.allocateFrom(name))
        }

        return Value.from(V) as PointerValue
    }

    fun gep(type: Type, value: PointerValue, idx: UInt, name: String): PointerValue {
        return buildWith {
            PointerValue(LLVMBuildStructGEP2(B, type.T, value.V, idx, allocateFrom(name)))
        }
    }


    fun store(value: Value, ptr: PointerValue): Value {
        return Value.from(LLVMBuildStore(B, value.V, ptr.V))
    }


    fun condBr(cond: Value, then: BasicBlock, not: BasicBlock): Value {
        return build<Value> { LLVMBuildCondBr(B, cond.V, then.B, not.B) }
    }

    fun or(lhs: IntValue, rhs: IntValue, name: String): IntValue {
        return buildAsWith { LLVMBuildOr(B, lhs.V, rhs.V, allocateFrom(name)) }
    }


    fun icmp(op: IntPredicate, lhs: IntValue, rhs: IntValue, name: String): IntValue {
        return buildAsWith<IntValue> { LLVMBuildICmp(B, op, lhs.V, rhs.V, allocateFrom(name)) }
    }

    fun icmp(op: IntPredicate, lhs: PointerValue, rhs: PointerValue, name: String): IntValue {
        return buildAsWith<IntValue> { LLVMBuildICmp(B, op, lhs.V, rhs.V, allocateFrom(name)) }
    }

    fun br(dest: BasicBlock): Value {
        return build<Value> { LLVMBuildBr(B, dest.B) }
    }

    fun select(cond: Value, then: Value, not: Value, name: String): Value {
        return buildAsWith<Value> { LLVMBuildSelect(B, cond.V, then.V, not.V, allocateFrom(name)) }
    }


    fun phi(type: Type, name: String): PhiValue {
        return buildWith { PhiValue(LLVMBuildPhi(B, type.T, allocateFrom(name))) }
    }

    fun load(type: Type, value: Value, name: String): Value {
        return buildAsWith<Value> { LLVMBuildLoad2(B, type.T, value.V, allocateFrom(name)) }
    }


    fun isNull(value: Value, name: String): IntValue {
        return buildAsWith<IntValue> { LLVMBuildIsNull(B, value.V, allocateFrom(name)) }
    }

    fun isNotNull(value: Value, name: String): IntValue {
        return buildAsWith<IntValue> { LLVMBuildIsNotNull(B, value.V, allocateFrom(name)) }
    }


    fun fneg(value: FloatValue, name: String): FloatValue {
        return buildWith { FloatValue(LLVMBuildFNeg(B, value.V, allocateFrom(name))) }
    }

    fun bitcast(value: Value, destType: Type, name: String): Value {
        return buildAsWith { LLVMBuildBitCast(B, value.V, destType.T, allocateFrom(name)) }
    }


    fun intcast(value: Value, destType: IntType, name: String): Value {
        return buildAsWith { LLVMBuildIntCast(B, value.V, destType.T, allocateFrom(name)) }
    }

    fun sub(lhs: IntValue, rhs: IntValue, name: String): IntValue {
        return buildAsWith { LLVMBuildSub(B, lhs.V, rhs.V, allocateFrom(name)) }
    }


    fun fsub(lhs: FloatValue, rhs: FloatValue, name: String): FloatValue {
        return buildAsWith { LLVMBuildFSub(B, lhs.V, rhs.V, allocateFrom(name)) }
    }

    fun mul(lhs: IntValue, rhs: IntValue, name: String): IntValue {
        return buildAsWith { LLVMBuildMul(B, lhs.V, rhs.V, allocateFrom(name)) }
    }

    fun fmul(lhs: FloatValue, rhs: FloatValue, name: String): FloatValue {
        return buildAsWith { LLVMBuildFMul(B, lhs.V, rhs.V, allocateFrom(name)) }
    }

    fun udiv(lhs: IntValue, rhs: IntValue, name: String): IntValue {
        return buildAsWith { LLVMBuildUDiv(B, lhs.V, rhs.V, allocateFrom(name)) }
    }

    fun sdiv(lhs: IntValue, rhs: IntValue, name: String): IntValue {
        return buildAsWith { LLVMBuildSDiv(B, lhs.V, rhs.V, allocateFrom(name)) }
    }

    fun fdiv(lhs: FloatValue, rhs: FloatValue, name: String): FloatValue {
        return buildAsWith { LLVMBuildFDiv(B, lhs.V, rhs.V, allocateFrom(name)) }
    }

    fun urem(lhs: IntValue, rhs: IntValue, name: String): IntValue {
        return buildAsWith { LLVMBuildURem(B, lhs.V, rhs.V, allocateFrom(name)) }
    }

    fun srem(lhs: IntValue, rhs: IntValue, name: String): IntValue {
        return buildAsWith { LLVMBuildSRem(B, lhs.V, rhs.V, allocateFrom(name)) }
    }

    fun frem(lhs: FloatValue, rhs: FloatValue, name: String): FloatValue {
        return buildAsWith { LLVMBuildFRem(B, lhs.V, rhs.V, allocateFrom(name)) }
    }

    fun nswNeg(value: IntValue, name: String): IntValue {
        return buildAsWith { LLVMBuildNSWNeg(B, value.V, allocateFrom(name)) }
    }


    fun call(functionType: Type, function: FunctionValue, params: List<Value>, name: String): Value {
        return buildCall(functionType, function, params, name)
    }

    fun call(functionType: Type, function: PointerValue, params: List<Value>, name: String): Value {
        return buildCall(functionType, function, params, name)
    }


    private fun buildCall(functionType: Type, function: Value, params: List<Value>, name: String): Value {
        return buildAsWith<Value> {
            val paramArray = allocate(ValueLayout.ADDRESS, params.size.toLong())
            params.forEachIndexed { index, value ->
                paramArray.setAtIndex(
                    ValueLayout.ADDRESS,
                    index.toLong(),
                    value.V
                )
            }

            LLVMBuildCall2(B, functionType.T, function.V, paramArray, params.size.toUInt(), allocateFrom(name))
        }
    }


    fun globalStr(str: String, name: String): PointerValue {
        return buildWith {
            PointerValue(LLVMBuildGlobalStringPtr(B, allocateFrom(str), allocateFrom(name)))
        }
    }


    // utility
    fun <T : Value> setAlignment(value: T, bytes: UInt): T {

        if (bytes == 0u) {
            return value
        }

        LLVMSetAlignment(value.V, bytes)
        return value
    }

    fun <T : Value> setName(value: T, name: String): T {
        confined { temp -> LLVMSetValueName(value.V, temp.allocateFrom(name)) }
        return value
    }


    inline operator fun invoke(builder: BuilderDSL.() -> Unit) {
        BuilderDSL(this, B).apply(builder)
    }


    fun dispose() {
        LLVMDisposeBuilder(B)
    }

    override fun close() {
        return dispose()
    }

}