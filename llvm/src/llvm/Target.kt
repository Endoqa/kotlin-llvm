package llvm

import lib.llvm.*

object Target {


    private val x86: Unit by lazy {
        LLVMInitializeX86TargetInfo()
        LLVMInitializeX86Target()
        LLVMInitializeX86TargetMC()

        LLVMInitializeX86AsmPrinter()
        LLVMInitializeX86AsmParser()
    }

    fun initialize() {
        x86
    }
}