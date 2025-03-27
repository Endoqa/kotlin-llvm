package llvm


import lib.llvm.*
import llvm.values.IntValue
import llvm.values.Value
import java.lang.foreign.ValueLayout

sealed class Type(
    val T: LLVMTypeRef
) {

    val isSized: Boolean get() = LLVMTypeIsSized(T) == 1

    /**
     * Check isSized before use
     *
     * A property that calculates the size of the LLVM type referenced by `T` in bytes.
     * This returns an `IntValue` representing the size.
     */
    val sizeOf: IntValue get() = Value.from(LLVMSizeOf(T)) as IntValue

    /**
     * Returns this type as an AnyTypeEnum, which is just the Type itself in this implementation.
     * This method exists for API compatibility with the Rust implementation.
     */
    fun asAnyTypeEnum(): Type = this

    companion object {
        fun from(T: LLVMTypeRef): Type = when (LLVMGetTypeKind(T)) {
            LLVMTypeKind.VoidTypeKind -> VoidType(T)
            LLVMTypeKind.HalfTypeKind -> TODO()

            LLVMTypeKind.FloatTypeKind,
            LLVMTypeKind.DoubleTypeKind -> FloatType(T)

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

class StructType(val elements: List<Type>, T: LLVMTypeRef) : Type(T) {

    constructor(elements: List<Type>) : this(elements, T = createStructType(elements))

    constructor(C: LLVMContextRef, elements: List<Type>) : this(elements, createStructType(elements, C))
    constructor(C: LLVMContextRef, name: String, elements: List<Type>) : this(
        elements,
        createStructTypeNamed(C, name, elements)
    )

    /**
     * Creates a StructType from an existing LLVMTypeRef.
     * This is used when getting a struct type from a module or context.
     */
    constructor(T: LLVMTypeRef) : this(getStructTypeElements(T), T)
}

class ArrayType(
    val type: Type,
    val count: UInt
) : Type(createArrayType(type, count))


private fun createArrayType(type: Type, count: UInt): LLVMTypeRef {
    return LLVMArrayType(type.T, count)
}

class PointerType(T: LLVMTypeRef) : Type(T) {
    constructor() : this(LLVMPointerTypeInContext(LLVMGetGlobalContext(), 0u))
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

private fun createStructTypeNamed(C: LLVMContextRef, name: String, elements: List<Type>): LLVMTypeRef {
    val T = confined { temp ->
        val arr = temp.allocate(ValueLayout.ADDRESS, elements.size.toLong())
        elements.forEachIndexed { i, it ->
            arr.setAtIndex(ValueLayout.ADDRESS, i.toLong(), it.T)
        }


        val T = LLVMStructCreateNamed(C, temp.allocateFrom(name))



        LLVMStructSetBody(T, arr, elements.size.toUInt(), 0)

        T

    }

    return T
}


private fun createStructType(elements: List<Type>, context: LLVMContextRef? = null): LLVMTypeRef {
    val T = confined { temp ->
        val arr = temp.allocate(ValueLayout.ADDRESS, elements.size.toLong())
        elements.forEachIndexed { i, it ->
            arr.setAtIndex(ValueLayout.ADDRESS, i.toLong(), it.T)
        }

        if (context != null) {
            LLVMStructTypeInContext(
                context,
                arr,
                elements.size.toUInt(),
                0
            )
        } else {
            LLVMStructType(
                arr,
                elements.size.toUInt(),
                0
            )
        }


    }

    return T
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

/**
 * Gets the elements of a struct type from an LLVMTypeRef.
 */
private fun getStructTypeElements(T: LLVMTypeRef): List<Type> {
    val elemCount = LLVMCountStructElementTypes(T)

    return confined { temp ->
        val ptr = temp.allocate(ValueLayout.ADDRESS, elemCount.toLong())
        LLVMGetStructElementTypes(T, ptr)

        val elements = List(elemCount.toInt()) {
            Type.from(ptr.getAtIndex(ValueLayout.ADDRESS, it.toLong()))
        }

        elements
    }
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
