package llvm.builder

import lib.llvm.LLVMBuilderRef
import lib.llvm.LLVMRealPredicate
import llvm.*
import llvm.values.FloatValue
import llvm.values.FunctionValue
import llvm.values.IntValue
import llvm.values.*
import llvm.types.*
import kotlin.reflect.KProperty


class BuilderDSL(
    val builder: Builder,
    val B: LLVMBuilderRef
) {


    // invoke
    operator fun <T : Value> IR<T>.getValue(thisRef: Any?, property: KProperty<*>): T {

        if (this.name == "") {
            this.name = property.name
        }



        with(this@BuilderDSL) {
            var v = irVal ?: buildIR()
            irVal = v

            return v
        }
    }


    operator fun FunctionValue.get(idx: UInt): Value {
        return this.getParam(idx)
    }


    operator fun IntValue.plus(rhs: IntValue): IntAddIR {
        return IntAddIR(this, rhs)
    }

    operator fun FloatValue.plus(rhs: FloatValue): FloatAddIR {
        return FloatAddIR(this, rhs)
    }


    infix fun IntValue.or(rhs: IntValue): OrIR {
        return OrIR(this, rhs)
    }

    infix fun IntValue.xor(rhs: IntValue): XorIR {
        return XorIR(this, rhs)
    }

    infix fun IntValue.shl(rhs: IntValue): SHLIR {
        return SHLIR(this, rhs)
    }

    infix fun IntValue.shr(rhs: IntValue): SHRIR {
        return SHRIR(this, rhs)
    }

    infix fun IntValue.ushr(rhs: IntValue): USHRIR {
        return USHRIR(this, rhs)
    }

    infix fun IntValue.and(rhs: IntValue): AndIR {
        return AndIR(this, rhs)
    }

    fun <T : Value> load(type: Type, value: PointerValue): LoadIR<T> {
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

    fun isNull(value: Value): IsNullIR {
        return IsNullIR(value)
    }


    fun isNotNull(value: Value): IsNotNullIR {
        return IsNotNullIR(value)
    }

    fun store(value: Value, ptr: PointerValue): Value {
        return builder.store(value, ptr)
    }

    fun sub(lhs: IntValue, rhs: IntValue): SubIR {
        return subIR(lhs, rhs)
    }

    fun sub(lhs: FloatValue, rhs: FloatValue): SubIR {
        return subIR(lhs, rhs)
    }

    fun mul(lhs: IntValue, rhs: IntValue): MulIR {
        return mulIR(lhs, rhs)
    }

    fun mul(lhs: FloatValue, rhs: FloatValue): MulIR {
        return mulIR(lhs, rhs)
    }

    fun uDiv(lhs: IntValue, rhs: IntValue): UDivIR {
        return uDivIR(lhs, rhs)
    }

    fun sDiv(lhs: IntValue, rhs: IntValue): SDivIR {
        return sDivIR(lhs, rhs)
    }

    fun fDiv(lhs: FloatValue, rhs: FloatValue): FDivIR {
        return fDivIR(lhs, rhs)
    }

    fun uRem(lhs: IntValue, rhs: IntValue): URemIR {
        return uRemIR(lhs, rhs)
    }

    fun sRem(lhs: IntValue, rhs: IntValue): SRemIR {
        return sRemIR(lhs, rhs)
    }

    fun fRem(lhs: FloatValue, rhs: FloatValue): FRemIR {
        return fRemIR(lhs, rhs)
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

    fun globalStr(str: String): GlobalStrIR {
        return GlobalStrIR(str)
    }

    private fun callIR(type: FunctionType, value: Value, params: List<Value>): CallIR {
        return CallIR(type, value, params)
    }

    private fun subIR(lhs: Value, rhs: Value): SubIR {
        return SubIR(lhs, rhs)
    }

    private fun mulIR(lhs: Value, rhs: Value): MulIR {
        return MulIR(lhs, rhs)
    }

    private fun uDivIR(lhs: IntValue, rhs: IntValue): UDivIR {
        return UDivIR(lhs, rhs)
    }

    private fun sDivIR(lhs: IntValue, rhs: IntValue): SDivIR {
        return SDivIR(lhs, rhs)
    }

    private fun fDivIR(lhs: FloatValue, rhs: FloatValue): FDivIR {
        return FDivIR(lhs, rhs)
    }

    private fun uRemIR(lhs: IntValue, rhs: IntValue): URemIR {
        return URemIR(lhs, rhs)
    }

    private fun sRemIR(lhs: IntValue, rhs: IntValue): SRemIR {
        return SRemIR(lhs, rhs)
    }

    private fun fRemIR(lhs: FloatValue, rhs: FloatValue): FRemIR {
        return FRemIR(lhs, rhs)
    }

    infix fun <T : Value> T.align(bytes: UInt): T {
        return builder.setAlignment(this, bytes)
    }

    infix fun <T : Value> T.named(name: String): T {
        return builder.setName(this, name)
    }

    // icmp
    infix fun IntValue.eq(rhs: IntValue): IntCmpIR {
        return IntCmpIR(this, rhs, IntPredicate.EQ)
    }

    infix fun IntValue.ne(rhs: IntValue): IntCmpIR {
        return IntCmpIR(this, rhs, IntPredicate.NE)
    }

    infix fun IntValue.sgt(rhs: IntValue): IntCmpIR {
        return IntCmpIR(this, rhs, IntPredicate.SGT)
    }

    infix fun IntValue.sge(rhs: IntValue): IntCmpIR {
        return IntCmpIR(this, rhs, IntPredicate.SGE)
    }

    infix fun IntValue.slt(rhs: IntValue): IntCmpIR {
        return IntCmpIR(this, rhs, IntPredicate.SLT)
    }

    infix fun IntValue.sle(rhs: IntValue): IntCmpIR {
        return IntCmpIR(this, rhs, IntPredicate.SLE)
    }

    infix fun PointerValue.eq(rhs: PointerValue): IntCmpIR {
        return IntCmpIR(this, rhs, IntPredicate.EQ)
    }

    infix fun PointerValue.ne(rhs: PointerValue): IntCmpIR {
        return IntCmpIR(this, rhs, IntPredicate.NE)
    }


    infix fun Value.trunc(type: IntType): TruncIR {
        return TruncIR(this, type)
    }

    infix fun Value.zext(type: IntType): ZExtIR {
        return ZExtIR(this, type)
    }

    infix fun Value.intcast(type: IntType): IntCastIR {
        return IntCastIR(this, type)
    }

    infix fun Value.fpcast(type: FloatType): FpCastIR {
        return FpCastIR(this, type)
    }

    infix fun Value.sitofpt(type: FloatType): SIToFPIR {
        return SIToFPIR(this, type)
    }

    infix fun Value.fptosi(type: IntType): FPToSIIR {
        return FPToSIIR(this, type)
    }

    fun switch(value: Value, default: BasicBlock, cases: List<SwitchCase>) = builder.switch(value, default, cases)

    fun fcmp(predicate: LLVMRealPredicate, lhs: Value, rhs: Value): FcmpIR {
        return FcmpIR(lhs, rhs, predicate)
    }

    fun select(cond: Value, ifTrue: Value, ifFalse: Value): SelectIR {
        return SelectIR(cond, ifTrue, ifFalse)
    }


    fun returns() = builder.buildReturn()
    fun returns(value: Value) = builder.buildReturn(value)
}