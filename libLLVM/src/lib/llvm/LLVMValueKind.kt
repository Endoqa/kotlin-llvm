// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.llvm

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType

public enum class LLVMValueKind(
    public val `value`: Int,
) {
    ArgumentValueKind(0),
    BasicBlockValueKind(1),
    MemoryUseValueKind(2),
    MemoryDefValueKind(3),
    MemoryPhiValueKind(4),
    FunctionValueKind(5),
    GlobalAliasValueKind(6),
    GlobalIFuncValueKind(7),
    GlobalVariableValueKind(8),
    BlockAddressValueKind(9),
    ConstantExprValueKind(10),
    ConstantArrayValueKind(11),
    ConstantStructValueKind(12),
    ConstantVectorValueKind(13),
    UndefValueValueKind(14),
    ConstantAggregateZeroValueKind(15),
    ConstantDataArrayValueKind(16),
    ConstantDataVectorValueKind(17),
    ConstantIntValueKind(18),
    ConstantFPValueKind(19),
    ConstantPointerNullValueKind(20),
    ConstantTokenNoneValueKind(21),
    MetadataAsValueValueKind(22),
    InlineAsmValueKind(23),
    InstructionValueKind(24),
    PoisonValueValueKind(25),
    ConstantTargetNoneValueKind(26),
    ConstantPtrAuthValueKind(27),
    ;

    public companion object {
        @JvmStatic
        public val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            LLVMValueKind::class.java,
            "fromInt",
            MethodType.methodType(LLVMValueKind::class.java, Int::class.java)
        )

        @JvmStatic
        public val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            LLVMValueKind::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        public fun fromInt(`value`: Int): LLVMValueKind = when (value) {
            ArgumentValueKind.value -> ArgumentValueKind
            BasicBlockValueKind.value -> BasicBlockValueKind
            MemoryUseValueKind.value -> MemoryUseValueKind
            MemoryDefValueKind.value -> MemoryDefValueKind
            MemoryPhiValueKind.value -> MemoryPhiValueKind
            FunctionValueKind.value -> FunctionValueKind
            GlobalAliasValueKind.value -> GlobalAliasValueKind
            GlobalIFuncValueKind.value -> GlobalIFuncValueKind
            GlobalVariableValueKind.value -> GlobalVariableValueKind
            BlockAddressValueKind.value -> BlockAddressValueKind
            ConstantExprValueKind.value -> ConstantExprValueKind
            ConstantArrayValueKind.value -> ConstantArrayValueKind
            ConstantStructValueKind.value -> ConstantStructValueKind
            ConstantVectorValueKind.value -> ConstantVectorValueKind
            UndefValueValueKind.value -> UndefValueValueKind
            ConstantAggregateZeroValueKind.value -> ConstantAggregateZeroValueKind
            ConstantDataArrayValueKind.value -> ConstantDataArrayValueKind
            ConstantDataVectorValueKind.value -> ConstantDataVectorValueKind
            ConstantIntValueKind.value -> ConstantIntValueKind
            ConstantFPValueKind.value -> ConstantFPValueKind
            ConstantPointerNullValueKind.value -> ConstantPointerNullValueKind
            ConstantTokenNoneValueKind.value -> ConstantTokenNoneValueKind
            MetadataAsValueValueKind.value -> MetadataAsValueValueKind
            InlineAsmValueKind.value -> InlineAsmValueKind
            InstructionValueKind.value -> InstructionValueKind
            PoisonValueValueKind.value -> PoisonValueValueKind
            ConstantTargetNoneValueKind.value -> ConstantTargetNoneValueKind
            ConstantPtrAuthValueKind.value -> ConstantPtrAuthValueKind
            else -> error("enum not found")
        }
    }
}
