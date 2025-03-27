package llvm.types

import lib.llvm.*

class PointerType(T: LLVMTypeRef) : Type(T) {
    constructor() : this(LLVMPointerTypeInContext(LLVMGetGlobalContext(), 0u))
}