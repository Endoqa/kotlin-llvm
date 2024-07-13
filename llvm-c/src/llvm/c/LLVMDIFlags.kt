// this file is auto generated by endoqa kotlin ffi, modify it with caution
package llvm.c

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.Int

public enum class LLVMDIFlags(
    public val `value`: Int,
) {
    Zero(0),
    Private(1),
    Protected(2),
    Public(3),
    FwdDecl(1 shl 2),
    AppleBlock(1 shl 3),
    ReservedBit4(1 shl 4),
    Virtual(1 shl 5),
    Artificial(1 shl 6),
    Explicit(1 shl 7),
    Prototyped(1 shl 8),
    ObjcClassComplete(1 shl 9),
    ObjectPointer(1 shl 10),
    Vector(1 shl 11),
    StaticMember(1 shl 12),
    LValueReference(1 shl 13),
    RValueReference(1 shl 14),
    Reserved(1 shl 15),
    SingleInheritance(1 shl 16),
    MultipleInheritance(2 shl 16),
    VirtualInheritance(3 shl 16),
    IntroducedVirtual(1 shl 18),
    BitField(1 shl 19),
    NoReturn(1 shl 20),
    TypePassByValue(1 shl 22),
    TypePassByReference(1 shl 23),
    EnumClass(1 shl 24),
    FixedEnum(EnumClass.value),
    Thunk(1 shl 25),
    NonTrivial(1 shl 26),
    BigEndian(1 shl 27),
    LittleEndian(1 shl 28),
    IndirectVirtualBase((1 shl 2) or (1 shl 5)),
    Accessibility(Private.value or Protected.value or Public.value),
    PtrToMemberRep(SingleInheritance.value or MultipleInheritance.value or VirtualInheritance.value),
    ;

    public companion object {
        @JvmStatic
        public val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            LLVMDIFlags::class.java,
            "fromInt",
            MethodType.methodType(LLVMDIFlags::class.java, Int::class.java)
        )

        @JvmStatic
        public val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            LLVMDIFlags::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        public fun fromInt(`value`: Int): LLVMDIFlags = when (value) {
            Zero.value -> Zero
            Private.value -> Private
            Protected.value -> Protected
            Public.value -> Public
            FwdDecl.value -> FwdDecl
            AppleBlock.value -> AppleBlock
            ReservedBit4.value -> ReservedBit4
            Virtual.value -> Virtual
            Artificial.value -> Artificial
            Explicit.value -> Explicit
            Prototyped.value -> Prototyped
            ObjcClassComplete.value -> ObjcClassComplete
            ObjectPointer.value -> ObjectPointer
            Vector.value -> Vector
            StaticMember.value -> StaticMember
            LValueReference.value -> LValueReference
            RValueReference.value -> RValueReference
            Reserved.value -> Reserved
            SingleInheritance.value -> SingleInheritance
            MultipleInheritance.value -> MultipleInheritance
            VirtualInheritance.value -> VirtualInheritance
            IntroducedVirtual.value -> IntroducedVirtual
            BitField.value -> BitField
            NoReturn.value -> NoReturn
            TypePassByValue.value -> TypePassByValue
            TypePassByReference.value -> TypePassByReference
            EnumClass.value -> EnumClass
            FixedEnum.value -> FixedEnum
            Thunk.value -> Thunk
            NonTrivial.value -> NonTrivial
            BigEndian.value -> BigEndian
            LittleEndian.value -> LittleEndian
            IndirectVirtualBase.value -> IndirectVirtualBase
            Accessibility.value -> Accessibility
            PtrToMemberRep.value -> PtrToMemberRep
            else -> error("enum not found")
        }
    }
}
