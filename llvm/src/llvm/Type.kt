package llvm


import lib.llvm.*
import java.lang.foreign.ValueLayout

sealed class Type(
    val T: LLVMTypeRef
) {

    companion object {
        fun from(T: LLVMTypeRef): Type = when (LLVMGetTypeKind(T)) {
            LLVMTypeKind.VoidTypeKind -> VoidType(T)
            LLVMTypeKind.HalfTypeKind -> TODO()
            LLVMTypeKind.FloatTypeKind -> FloatType(T)
            LLVMTypeKind.DoubleTypeKind -> TODO()
            LLVMTypeKind.X86_FP80TypeKind -> TODO()
            LLVMTypeKind.FP128TypeKind -> TODO()
            LLVMTypeKind.PPC_FP128TypeKind -> TODO()
            LLVMTypeKind.LabelTypeKind -> TODO()
            LLVMTypeKind.IntegerTypeKind -> IntType(T)
            LLVMTypeKind.FunctionTypeKind -> FunctionType.from(T)
            LLVMTypeKind.StructTypeKind -> TODO()
            LLVMTypeKind.ArrayTypeKind -> TODO()
            LLVMTypeKind.PointerTypeKind -> TODO()
            LLVMTypeKind.VectorTypeKind -> TODO()
            LLVMTypeKind.MetadataTypeKind -> TODO()
            LLVMTypeKind.X86_MMXTypeKind -> TODO()
            LLVMTypeKind.TokenTypeKind -> TODO()
            LLVMTypeKind.ScalableVectorTypeKind -> TODO()
            LLVMTypeKind.BFloatTypeKind -> TODO()
            LLVMTypeKind.X86_AMXTypeKind -> TODO()
            LLVMTypeKind.TargetExtTypeKind -> TODO()
        }
    }

}

class VoidType(T: LLVMTypeRef) : Type(T)

class IntType(T: LLVMTypeRef) : Type(T)

class FloatType(T: LLVMTypeRef) : Type(T)


class PointerType(T: LLVMTypeRef) : Type(T) {
    constructor(type: Type) : this(LLVMPointerType(type.T, 0u))
}

data class FunctionType(
    val returnType: Type,
    val params: List<Type>,
    val isVarArg: Boolean = false
) : Type(createFunctionType(returnType, params, isVarArg)) {

    companion object {
        fun from(T: LLVMTypeRef) = getFunctionType(T)
    }

}


private fun getFunctionType(T: LLVMTypeRef): FunctionType {
    val r = LLVMGetReturnType(T)
    val paramsCount = LLVMCountParams(T)

    val prams = confined { temp ->
        val ptr = temp.allocate(ValueLayout.ADDRESS, paramsCount.toLong())
        LLVMGetParamTypes(T, ptr)

        val params = List(paramsCount.toInt()) {
            Type.from(ptr.getAtIndex(ValueLayout.ADDRESS, it.toLong()))
        }

        params
    }

    return FunctionType(Type.from(r), prams)
}


private fun createFunctionType(returnType: Type, params: List<Type>, isVarArg: Boolean): LLVMTypeRef {


    val funcType = confined { temp ->

        val arr = temp.allocate(ValueLayout.ADDRESS, params.size.toLong())

        params.forEachIndexed { index, type ->
            arr.setAtIndex(ValueLayout.ADDRESS, index.toLong(), type.T)
        }

        LLVMFunctionType(
            returnType.T,
            arr,
            params.size.toUInt(),
            if (isVarArg) 1 else 0,
        )
    }

    return funcType


}