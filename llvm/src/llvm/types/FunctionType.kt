package llvm.types

import lib.llvm.*
import llvm.confined
import java.lang.foreign.ValueLayout

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