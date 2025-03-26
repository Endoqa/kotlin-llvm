package llvm.values

import llvm.BasicBlock

data class SwitchCase(val condition: Value, val block: BasicBlock)