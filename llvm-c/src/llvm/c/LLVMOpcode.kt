// this file is auto generated by endoqa kotlin ffi, modify it with caution
package llvm.c

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.Int

public enum class LLVMOpcode(
    public val `value`: Int,
) {
    Ret(1),
    Br(2),
    Switch(3),
    IndirectBr(4),
    Invoke(5),
    Unreachable(7),
    CallBr(67),
    FNeg(66),
    Add(8),
    FAdd(9),
    Sub(10),
    FSub(11),
    Mul(12),
    FMul(13),
    UDiv(14),
    SDiv(15),
    FDiv(16),
    URem(17),
    SRem(18),
    FRem(19),
    Shl(20),
    LShr(21),
    AShr(22),
    And(23),
    Or(24),
    Xor(25),
    Alloca(26),
    Load(27),
    Store(28),
    GetElementPtr(29),
    Trunc(30),
    ZExt(31),
    SExt(32),
    FPToUI(33),
    FPToSI(34),
    UIToFP(35),
    SIToFP(36),
    FPTrunc(37),
    FPExt(38),
    PtrToInt(39),
    IntToPtr(40),
    BitCast(41),
    AddrSpaceCast(60),
    ICmp(42),
    FCmp(43),
    PHI(44),
    Call(45),
    Select(46),
    UserOp1(47),
    UserOp2(48),
    VAArg(49),
    ExtractElement(50),
    InsertElement(51),
    ShuffleVector(52),
    ExtractValue(53),
    InsertValue(54),
    Freeze(68),
    Fence(55),
    AtomicCmpXchg(56),
    AtomicRMW(57),
    Resume(58),
    LandingPad(59),
    CleanupRet(61),
    CatchRet(62),
    CatchPad(63),
    CleanupPad(64),
    CatchSwitch(65),
    ;

    public companion object {
        @JvmStatic
        public val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            LLVMOpcode::class.java,
            "fromInt",
            MethodType.methodType(LLVMOpcode::class.java, Int::class.java)
        )

        @JvmStatic
        public val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            LLVMOpcode::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        public fun fromInt(`value`: Int): LLVMOpcode = when (value) {
            Ret.value -> Ret
            Br.value -> Br
            Switch.value -> Switch
            IndirectBr.value -> IndirectBr
            Invoke.value -> Invoke
            Unreachable.value -> Unreachable
            CallBr.value -> CallBr
            FNeg.value -> FNeg
            Add.value -> Add
            FAdd.value -> FAdd
            Sub.value -> Sub
            FSub.value -> FSub
            Mul.value -> Mul
            FMul.value -> FMul
            UDiv.value -> UDiv
            SDiv.value -> SDiv
            FDiv.value -> FDiv
            URem.value -> URem
            SRem.value -> SRem
            FRem.value -> FRem
            Shl.value -> Shl
            LShr.value -> LShr
            AShr.value -> AShr
            And.value -> And
            Or.value -> Or
            Xor.value -> Xor
            Alloca.value -> Alloca
            Load.value -> Load
            Store.value -> Store
            GetElementPtr.value -> GetElementPtr
            Trunc.value -> Trunc
            ZExt.value -> ZExt
            SExt.value -> SExt
            FPToUI.value -> FPToUI
            FPToSI.value -> FPToSI
            UIToFP.value -> UIToFP
            SIToFP.value -> SIToFP
            FPTrunc.value -> FPTrunc
            FPExt.value -> FPExt
            PtrToInt.value -> PtrToInt
            IntToPtr.value -> IntToPtr
            BitCast.value -> BitCast
            AddrSpaceCast.value -> AddrSpaceCast
            ICmp.value -> ICmp
            FCmp.value -> FCmp
            PHI.value -> PHI
            Call.value -> Call
            Select.value -> Select
            UserOp1.value -> UserOp1
            UserOp2.value -> UserOp2
            VAArg.value -> VAArg
            ExtractElement.value -> ExtractElement
            InsertElement.value -> InsertElement
            ShuffleVector.value -> ShuffleVector
            ExtractValue.value -> ExtractValue
            InsertValue.value -> InsertValue
            Freeze.value -> Freeze
            Fence.value -> Fence
            AtomicCmpXchg.value -> AtomicCmpXchg
            AtomicRMW.value -> AtomicRMW
            Resume.value -> Resume
            LandingPad.value -> LandingPad
            CleanupRet.value -> CleanupRet
            CatchRet.value -> CatchRet
            CatchPad.value -> CatchPad
            CleanupPad.value -> CleanupPad
            CatchSwitch.value -> CatchSwitch
            else -> error("enum not found")
        }
    }
}
