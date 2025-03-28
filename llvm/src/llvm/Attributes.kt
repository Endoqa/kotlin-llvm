package llvm

import lib.llvm.*
import llvm.types.Type
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

/**
 * Functions, function parameters, and return types can have `Attribute`s to indicate
 * how they should be treated by optimizations and code generation.
 */
class Attribute(
    val attribute: LLVMAttributeRef
) {
    companion object {
        fun getNamedEnumKindId(name: String): UInt {
            return confined { temp ->
                LLVMGetEnumAttributeKindForName(temp.allocateFrom(name), name.length.toULong())
            }
        }

        fun getLastEnumKindId(): UInt {
            return LLVMGetLastEnumAttributeKind()
        }
    }

    init {
        assert(attribute != MemorySegment.NULL) { "Attribute reference cannot be null" }
    }


    fun asMutPtr(): LLVMAttributeRef {
        return attribute
    }

    val isEnum: Boolean get() = LLVMIsEnumAttribute(attribute) == 1
    val isString: Boolean get() = LLVMIsStringAttribute(attribute) == 1
    val isType: Boolean get() = LLVMIsTypeAttribute(attribute) == 1


    val enumKindId: UInt
        get() {
            assert(enumKindIDIsValid) { "Attribute is not an enum or type attribute" }
            return LLVMGetEnumAttributeKind(attribute)
        }

    val enumKindIDIsValid: Boolean get() = isEnum || isType

    val enumValue: ULong
        get() {
            assert(isEnum) { "Attribute is not an enum attribute" }
            return LLVMGetEnumAttributeValue(attribute)
        }


    val stringKindID: String
        get() {
            assert(isString) { "Attribute is not a string attribute" }
            return confined { temp ->
                val length = temp.allocate(ValueLayout.JAVA_LONG)
                val cstrPtr = LLVMGetStringAttributeKind(attribute, length)
                cstrPtr.getString(0)
            }
        }

    val stringValue: String
        get() {
            assert(isString) { "Attribute is not a string attribute" }
            return confined { temp ->
                val length = temp.allocate(ValueLayout.JAVA_LONG)
                val cstrPtr = LLVMGetStringAttributeValue(attribute, length)
                cstrPtr.getString(0)
            }
        }


    val typeValue: Type
        get() {
            assert(isType) { "Attribute is not a type attribute" }
            val typeRef = LLVMGetTypeAttributeValue(attribute)
            return Type.from(typeRef)
        }
}