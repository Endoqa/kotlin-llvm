// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.llvm

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType

public enum class lto_symbol_attributes(
    public val `value`: Int,
) {
    /**
     * log2 of alignment
     */
    ALIGNMENT_MASK(0x0000001F),

    /**
     * log2 of alignment
     */
    PERMISSIONS_MASK(0x000000E0),
    PERMISSIONS_CODE(0x000000A0),
    PERMISSIONS_DATA(0x000000C0),
    PERMISSIONS_RODATA(0x00000080),
    DEFINITION_MASK(0x00000700),
    DEFINITION_REGULAR(0x00000100),
    DEFINITION_TENTATIVE(0x00000200),
    DEFINITION_WEAK(0x00000300),
    DEFINITION_UNDEFINED(0x00000400),
    DEFINITION_WEAKUNDEF(0x00000500),
    SCOPE_MASK(0x00003800),
    SCOPE_INTERNAL(0x00000800),
    SCOPE_HIDDEN(0x00001000),
    SCOPE_PROTECTED(0x00002000),
    SCOPE_DEFAULT(0x00001800),
    SCOPE_DEFAULT_CAN_BE_HIDDEN(0x00002800),
    COMDAT(0x00004000),
    ALIAS(0x00008000),
    ;

    public companion object {
        @JvmStatic
        public val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            lto_symbol_attributes::class.java,
            "fromInt",
            MethodType.methodType(lto_symbol_attributes::class.java, Int::class.java)
        )

        @JvmStatic
        public val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            lto_symbol_attributes::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        public fun fromInt(`value`: Int): lto_symbol_attributes = when (value) {
            ALIGNMENT_MASK.value -> ALIGNMENT_MASK
            PERMISSIONS_MASK.value -> PERMISSIONS_MASK
            PERMISSIONS_CODE.value -> PERMISSIONS_CODE
            PERMISSIONS_DATA.value -> PERMISSIONS_DATA
            PERMISSIONS_RODATA.value -> PERMISSIONS_RODATA
            DEFINITION_MASK.value -> DEFINITION_MASK
            DEFINITION_REGULAR.value -> DEFINITION_REGULAR
            DEFINITION_TENTATIVE.value -> DEFINITION_TENTATIVE
            DEFINITION_WEAK.value -> DEFINITION_WEAK
            DEFINITION_UNDEFINED.value -> DEFINITION_UNDEFINED
            DEFINITION_WEAKUNDEF.value -> DEFINITION_WEAKUNDEF
            SCOPE_MASK.value -> SCOPE_MASK
            SCOPE_INTERNAL.value -> SCOPE_INTERNAL
            SCOPE_HIDDEN.value -> SCOPE_HIDDEN
            SCOPE_PROTECTED.value -> SCOPE_PROTECTED
            SCOPE_DEFAULT.value -> SCOPE_DEFAULT
            SCOPE_DEFAULT_CAN_BE_HIDDEN.value -> SCOPE_DEFAULT_CAN_BE_HIDDEN
            COMDAT.value -> COMDAT
            ALIAS.value -> ALIAS
            else -> error("enum not found")
        }
    }
}
