package llvm.types

import lib.llvm.*
import llvm.confined
import java.lang.foreign.ValueLayout

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