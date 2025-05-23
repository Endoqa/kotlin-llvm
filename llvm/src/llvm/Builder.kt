package llvm

import lib.llvm.*
import llvm.builder.BuilderDSL
import llvm.values.*
import llvm.types.*
import java.lang.foreign.Arena
import java.lang.foreign.ValueLayout

class Builder(
    val B: LLVMBuilderRef
) : AutoCloseable {

    fun positionAtEnd(block: BasicBlock) {
        LLVMPositionBuilderAtEnd(B, block.B)
    }

    fun positionBefore(instr: Value) {
        LLVMPositionBuilderBefore(B, instr.V)
    }

    fun positionBuilder(block: BasicBlock, instr: Value) {
        LLVMPositionBuilder(B, block.B, instr.V)
    }

    fun clearInsertionPosition() {
        LLVMClearInsertionPosition(B)
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


    fun add(lhs: IntValue, rhs: IntValue, name: String): IntValue {
        val r = confined { temp ->
            LLVMBuildAdd(B, lhs.V, rhs.V, temp.allocateFrom(name))
        }

        val v = Value.from(r)
        require(v is IntValue)
        return v
    }

    fun nswAdd(lhs: IntValue, rhs: IntValue, name: String): IntValue {
        return buildAsWith { LLVMBuildNSWAdd(B, lhs.V, rhs.V, allocateFrom(name)) }
    }

    fun nuwAdd(lhs: IntValue, rhs: IntValue, name: String): IntValue {
        return buildAsWith { LLVMBuildNUWAdd(B, lhs.V, rhs.V, allocateFrom(name)) }
    }

    fun fadd(lhs: FloatValue, rhs: FloatValue, name: String): FloatValue {
        return buildAsWith { LLVMBuildFAdd(B, lhs.V, rhs.V, allocateFrom(name)) }
    }


    fun ret(ret: Value): Value {
        return Value.from(LLVMBuildRet(B, ret.V))
    }

    fun retVoid(): Value {
        return Value.from(LLVMBuildRetVoid(B))
    }

    fun aggregateRet(values: List<Value>): Value {
        return buildAsWith {
            val valuesArray = allocate(ValueLayout.ADDRESS, values.size.toLong())
            values.forEachIndexed { index, value ->
                valuesArray.setAtIndex(ValueLayout.ADDRESS, index.toLong(), value.V)
            }
            LLVMBuildAggregateRet(B, valuesArray, values.size.toUInt())
        }
    }

    fun unreachable(): Value {
        return Value.from(LLVMBuildUnreachable(B))
    }

    fun resume(value: Value): Value {
        return Value.from(LLVMBuildResume(B, value.V))
    }

    fun alloca(type: Type, name: String): PointerValue {
        return Value.from(confined { temp ->
            LLVMBuildAlloca(B, type.T, temp.allocateFrom(name))
        }) as PointerValue
    }

    fun arrayAlloca(type: Type, size: Value, name: String): PointerValue {
        return buildAsWith { LLVMBuildArrayAlloca(B, type.T, size.V, allocateFrom(name)) }
    }

    fun malloc(type: Type, name: String): PointerValue {
        return buildAsWith { LLVMBuildMalloc(B, type.T, allocateFrom(name)) }
    }

    fun arrayMalloc(type: Type, size: Value, name: String): PointerValue {
        return buildAsWith { LLVMBuildArrayMalloc(B, type.T, size.V, allocateFrom(name)) }
    }

    fun free(pointer: PointerValue): Value {
        return Value.from(LLVMBuildFree(B, pointer.V))
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

    fun fence(ordering: AtomicOrdering, singleThread: Boolean, name: String): Value {
        return buildAsWith { LLVMBuildFence(B, ordering, if (singleThread) 1 else 0, allocateFrom(name)) }
    }

    fun atomicCmpXchg(
        ptr: PointerValue,
        cmp: Value,
        new: Value,
        successOrdering: AtomicOrdering,
        failureOrdering: AtomicOrdering,
        singleThread: Boolean
    ): Value {
        return Value.from(LLVMBuildAtomicCmpXchg(B, ptr.V, cmp.V, new.V, successOrdering, failureOrdering, if (singleThread) 1 else 0))
    }

    fun atomicRmw(
        op: AtomicRMWBinOp,
        ptr: PointerValue,
        val_: Value,
        ordering: AtomicOrdering,
        singleThread: Boolean
    ): Value {
        return Value.from(LLVMBuildAtomicRMW(B, op, ptr.V, val_.V, ordering, if (singleThread) 1 else 0))
    }


    fun condBr(cond: Value, then: BasicBlock, not: BasicBlock): Value {
        return build<Value> { LLVMBuildCondBr(B, cond.V, then.B, not.B) }
    }

    fun or(lhs: IntValue, rhs: IntValue, name: String): IntValue {
        return buildAsWith { LLVMBuildOr(B, lhs.V, rhs.V, allocateFrom(name)) }
    }

    fun xor(lhs: IntValue, rhs: IntValue, name: String): IntValue {
        return buildAsWith { LLVMBuildXor(B, lhs.V, rhs.V, allocateFrom(name)) }
    }

    fun shl(lhs: IntValue, rhs: IntValue, name: String): IntValue {
        return buildAsWith { LLVMBuildShl(B, lhs.V, rhs.V, allocateFrom(name)) }
    }

    fun ashr(lhs: IntValue, rhs: IntValue, name: String): IntValue {
        return buildAsWith { LLVMBuildAShr(B, lhs.V, rhs.V, allocateFrom(name)) }
    }

    fun lshr(lhs: IntValue, rhs: IntValue, name: String): IntValue {
        return buildAsWith { LLVMBuildLShr(B, lhs.V, rhs.V, allocateFrom(name)) }
    }

    fun and(lhs: IntValue, rhs: IntValue, name: String): IntValue {
        return buildAsWith { LLVMBuildAnd(B, lhs.V, rhs.V, allocateFrom(name)) }
    }


    fun icmp(op: IntPredicate, lhs: IntValue, rhs: IntValue, name: String): IntValue {
        return buildAsWith<IntValue> { LLVMBuildICmp(B, op, lhs.V, rhs.V, allocateFrom(name)) }
    }

    fun icmp(op: IntPredicate, lhs: PointerValue, rhs: PointerValue, name: String): IntValue {
        return buildAsWith<IntValue> { LLVMBuildICmp(B, op, lhs.V, rhs.V, allocateFrom(name)) }
    }

    fun fcmp(op: FloatPredicate, lhs: Value, rhs: Value, name: String): IntValue {
        return buildAsWith<IntValue> { LLVMBuildFCmp(B, op, lhs.V, rhs.V, allocateFrom(name)) }
    }

    fun br(dest: BasicBlock): Value {
        return build<Value> { LLVMBuildBr(B, dest.B) }
    }

    fun indirectBr(address: Value, destinations: UInt): Value {
        return Value.from(LLVMBuildIndirectBr(B, address.V, destinations))
    }

    fun select(cond: Value, then: Value, not: Value, name: String): Value {
        return buildAsWith<Value> { LLVMBuildSelect(B, cond.V, then.V, not.V, allocateFrom(name)) }
    }

    fun extractElement(vector: Value, index: Value, name: String): Value {
        return buildAsWith { LLVMBuildExtractElement(B, vector.V, index.V, allocateFrom(name)) }
    }

    fun insertElement(vector: Value, element: Value, index: Value, name: String): Value {
        return buildAsWith { LLVMBuildInsertElement(B, vector.V, element.V, index.V, allocateFrom(name)) }
    }

    fun shuffleVector(v1: Value, v2: Value, mask: Value, name: String): Value {
        return buildAsWith { LLVMBuildShuffleVector(B, v1.V, v2.V, mask.V, allocateFrom(name)) }
    }

    fun extractValue(agg: Value, index: UInt, name: String): Value {
        return buildAsWith { LLVMBuildExtractValue(B, agg.V, index, allocateFrom(name)) }
    }

    fun insertValue(agg: Value, element: Value, index: UInt, name: String): Value {
        return buildAsWith { LLVMBuildInsertValue(B, agg.V, element.V, index, allocateFrom(name)) }
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

    fun bitCast(value: Value, destType: Type, name: String): Value {
        return buildAsWith { LLVMBuildBitCast(B, value.V, destType.T, allocateFrom(name)) }
    }

    fun ptrToInt(value: Value, destType: IntType, name: String): IntValue {
        return buildAsWith { LLVMBuildPtrToInt(B, value.V, destType.T, allocateFrom(name)) }
    }

    fun intToPtr(value: Value, destType: PointerType, name: String): PointerValue {
        return buildAsWith { LLVMBuildIntToPtr(B, value.V, destType.T, allocateFrom(name)) }
    }

    fun addrSpaceCast(value: Value, destType: PointerType, name: String): PointerValue {
        return buildAsWith { LLVMBuildAddrSpaceCast(B, value.V, destType.T, allocateFrom(name)) }
    }

    fun pointerCast(value: Value, destType: PointerType, name: String): PointerValue {
        return buildAsWith { LLVMBuildPointerCast(B, value.V, destType.T, allocateFrom(name)) }
    }


    fun intCast(value: Value, destType: IntType, name: String): Value {
        return buildAsWith { LLVMBuildIntCast(B, value.V, destType.T, allocateFrom(name)) }
    }

    fun sextOrBitCast(value: Value, destType: Type, name: String): Value {
        return buildAsWith { LLVMBuildSExtOrBitCast(B, value.V, destType.T, allocateFrom(name)) }
    }

    fun zextOrBitCast(value: Value, destType: Type, name: String): Value {
        return buildAsWith { LLVMBuildZExtOrBitCast(B, value.V, destType.T, allocateFrom(name)) }
    }

    fun truncOrBitCast(value: Value, destType: Type, name: String): Value {
        return buildAsWith { LLVMBuildTruncOrBitCast(B, value.V, destType.T, allocateFrom(name)) }
    }

    fun sub(lhs: IntValue, rhs: IntValue, name: String): IntValue {
        return buildAsWith { LLVMBuildSub(B, lhs.V, rhs.V, allocateFrom(name)) }
    }

    fun nswSub(lhs: IntValue, rhs: IntValue, name: String): IntValue {
        return buildAsWith { LLVMBuildNSWSub(B, lhs.V, rhs.V, allocateFrom(name)) }
    }

    fun nuwSub(lhs: IntValue, rhs: IntValue, name: String): IntValue {
        return buildAsWith { LLVMBuildNUWSub(B, lhs.V, rhs.V, allocateFrom(name)) }
    }


    fun fsub(lhs: FloatValue, rhs: FloatValue, name: String): FloatValue {
        return buildAsWith { LLVMBuildFSub(B, lhs.V, rhs.V, allocateFrom(name)) }
    }

    fun mul(lhs: IntValue, rhs: IntValue, name: String): IntValue {
        return buildAsWith { LLVMBuildMul(B, lhs.V, rhs.V, allocateFrom(name)) }
    }

    fun nswMul(lhs: IntValue, rhs: IntValue, name: String): IntValue {
        return buildAsWith { LLVMBuildNSWMul(B, lhs.V, rhs.V, allocateFrom(name)) }
    }

    fun nuwMul(lhs: IntValue, rhs: IntValue, name: String): IntValue {
        return buildAsWith { LLVMBuildNUWMul(B, lhs.V, rhs.V, allocateFrom(name)) }
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

    fun exactSdiv(lhs: IntValue, rhs: IntValue, name: String): IntValue {
        return buildAsWith { LLVMBuildExactSDiv(B, lhs.V, rhs.V, allocateFrom(name)) }
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

    fun nuwNeg(value: IntValue, name: String): IntValue {
        return buildAsWith { LLVMBuildNUWNeg(B, value.V, allocateFrom(name)) }
    }

    fun neg(value: IntValue, name: String): IntValue {
        return buildAsWith { LLVMBuildNeg(B, value.V, allocateFrom(name)) }
    }

    fun siToFp(value: Value, destType: FloatType, name: String): FloatValue {
        return buildAsWith { LLVMBuildSIToFP(B, value.V, destType.T, allocateFrom(name)) }
    }

    fun fpToSi(value: Value, destType: IntType, name: String): IntValue {
        return buildAsWith { LLVMBuildFPToSI(B, value.V, destType.T, allocateFrom(name)) }
    }

    fun fpCast(value: Value, destType: FloatType, name: String): FloatValue {
        return buildAsWith { LLVMBuildFPCast(B, value.V, destType.T, allocateFrom(name)) }
    }

    fun switch(value: Value, default: BasicBlock, cases: List<SwitchCase>): SwitchValue {
        val numCases = cases.size.toUInt()
        val switch = LLVMBuildSwitch(B, value.V, default.B, numCases)
        val v = SwitchValue(switch, numCases)
        cases.forEach {
            v.addCase(it)
        }

        return v
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


    fun globalStringPtr(str: String, name: String): PointerValue {
        return buildWith {
            PointerValue(LLVMBuildGlobalStringPtr(B, allocateFrom(str), allocateFrom(name)))
        }
    }

    fun trunc(value: Value, destType: IntType, name: String): Value {
        return buildAsWith { LLVMBuildTrunc(B, value.V, destType.T, allocateFrom(name)) }
    }

    fun zext(value: Value, destType: IntType, name: String): IntValue {
        return buildAsWith { LLVMBuildZExt(B, value.V, destType.T, allocateFrom(name)) }
    }

    fun cast(value: Value, destType: IntType, name: String): Value {
        return buildAsWith { LLVMBuildIntCast(B, value.V, destType.T, allocateFrom(name)) }
    }

    fun vaArg(list: Value, type: Type, name: String): Value {
        return buildAsWith { LLVMBuildVAArg(B, list.V, type.T, allocateFrom(name)) }
    }

    fun landingPad(type: Type, personality: Value, clauses: UInt, name: String): Value {
        return buildAsWith { LLVMBuildLandingPad(B, type.T, personality.V, clauses, allocateFrom(name)) }
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
