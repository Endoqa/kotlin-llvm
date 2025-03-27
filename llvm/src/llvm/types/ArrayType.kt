package llvm.types

import lib.llvm.*

class ArrayType(
    val type: Type,
    val count: UInt
) : Type(createArrayType(type, count))


private fun createArrayType(type: Type, count: UInt): LLVMTypeRef {
    return LLVMArrayType(type.T, count)
}