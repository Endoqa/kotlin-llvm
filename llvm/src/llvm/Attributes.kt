package llvm

import lib.llvm.*
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
        /**
         * Gets the enum kind id associated with a builtin name.
         *
         * Example:
         * ```
         * // This kind id doesn't exist:
         * assert(Attribute.getNamedEnumKindId("foobar") == 0u)
         *
         * // These are real kind ids:
         * assert(Attribute.getNamedEnumKindId("align") == 1u)
         * assert(Attribute.getNamedEnumKindId("builtin") == 5u)
         * ```
         */
        fun getNamedEnumKindId(name: String): UInt {
            return confined { temp ->
                LLVMGetEnumAttributeKindForName(temp.allocateFrom(name), name.length.toULong())
            }
        }

        /**
         * Gets the last enum kind id associated with builtin names.
         *
         * Example:
         * ```
         * assert(Attribute.getLastEnumKindId() == 56u)
         * ```
         */
        fun getLastEnumKindId(): UInt {
            return LLVMGetLastEnumAttributeKind()
        }
    }

    init {
        assert(attribute != MemorySegment.NULL) { "Attribute reference cannot be null" }
    }

    /**
     * Acquires the underlying raw pointer belonging to this `Attribute` type.
     */
    fun asMutPtr(): LLVMAttributeRef {
        return attribute
    }

    /**
     * Determines whether or not an `Attribute` is an enum.
     *
     * Example:
     * ```
     * val context = Context()
     * val enumAttribute = context.createEnumAttribute(0u, 10u)
     *
     * assert(enumAttribute.isEnum())
     * ```
     */
    fun isEnum(): Boolean {
        return LLVMIsEnumAttribute(attribute) == 1
    }

    /**
     * Determines whether or not an `Attribute` is a string.
     *
     * Example:
     * ```
     * val context = Context()
     * val stringAttribute = context.createStringAttribute("my_key_123", "my_val")
     *
     * assert(stringAttribute.isString())
     * ```
     */
    fun isString(): Boolean {
        return LLVMIsStringAttribute(attribute) == 1
    }

    /**
     * Determines whether or not an `Attribute` is a type attribute.
     *
     * Example:
     * ```
     * val context = Context()
     * val kindId = Attribute.getNamedEnumKindId("sret")
     * val typeAttribute = context.createTypeAttribute(
     *     kindId,
     *     context.types.i32.asAnyTypeEnum(),
     * )
     *
     * assert(typeAttribute.isType())
     * ```
     */
    fun isType(): Boolean {
        return LLVMIsTypeAttribute(attribute) == 1
    }

    /**
     * Gets the kind id associated with an enum `Attribute`.
     *
     * Example:
     * ```
     * val context = Context()
     * val enumAttribute = context.createEnumAttribute(0u, 10u)
     *
     * assert(enumAttribute.getEnumKindId() == 0u)
     * ```
     *
     * This function also works for type `Attribute`s.
     */
    fun getEnumKindId(): UInt {
        assert(getEnumKindIdIsValid()) { "Attribute is not an enum or type attribute" }
        return LLVMGetEnumAttributeKind(attribute)
    }

    private fun getEnumKindIdIsValid(): Boolean {
        return isEnum() || isType()
    }

    /**
     * Gets the value associated with an enum `Attribute`.
     *
     * Example:
     * ```
     * val context = Context()
     * val enumAttribute = context.createEnumAttribute(0u, 10u)
     *
     * assert(enumAttribute.getEnumValue() == 10uL)
     * ```
     */
    fun getEnumValue(): ULong {
        assert(isEnum()) { "Attribute is not an enum attribute" }
        return LLVMGetEnumAttributeValue(attribute)
    }

    /**
     * Gets the string kind id associated with a string attribute.
     *
     * Example:
     * ```
     * val context = Context()
     * val stringAttribute = context.createStringAttribute("my_key", "my_val")
     *
     * assert(stringAttribute.getStringKindId() == "my_key")
     * ```
     */
    fun getStringKindId(): String {
        assert(isString()) { "Attribute is not a string attribute" }
        return confined { temp ->
            val length = temp.allocate(ValueLayout.JAVA_LONG)
            val cstrPtr = LLVMGetStringAttributeKind(attribute, length)
            cstrPtr.getString(0)
        }
    }

    /**
     * Gets the string value associated with a string attribute.
     *
     * Example:
     * ```
     * val context = Context()
     * val stringAttribute = context.createStringAttribute("my_key", "my_val")
     *
     * assert(stringAttribute.getStringValue() == "my_val")
     * ```
     */
    fun getStringValue(): String {
        assert(isString()) { "Attribute is not a string attribute" }
        return confined { temp ->
            val length = temp.allocate(ValueLayout.JAVA_LONG)
            val cstrPtr = LLVMGetStringAttributeValue(attribute, length)
            cstrPtr.getString(0)
        }
    }

    /**
     * Gets the type associated with a type attribute.
     *
     * Example:
     * ```
     * val context = Context()
     * val kindId = Attribute.getNamedEnumKindId("sret")
     * val anyType = context.types.i32.asAnyTypeEnum()
     * val typeAttribute = context.createTypeAttribute(
     *     kindId,
     *     anyType,
     * )
     *
     * assert(typeAttribute.isType())
     * assert(typeAttribute.getTypeValue() == anyType)
     * assert(typeAttribute.getTypeValue() != context.types.i64.asAnyTypeEnum())
     * ```
     */
    fun getTypeValue(): Type {
        assert(isType()) { "Attribute is not a type attribute" }
        val typeRef = LLVMGetTypeAttributeValue(attribute)
        return Type.from(typeRef)
    }
}

/**
 * An `AttributeLoc` determines where on a function an attribute is assigned to.
 */
enum class AttributeLoc {
    /**
     * Assign to the `FunctionValue`'s return type.
     */
    Return,

    /**
     * Assign to one of the `FunctionValue`'s params (0-indexed).
     */
    Param,

    /**
     * Assign to the `FunctionValue` itself.
     */
    Function;

    /**
     * Gets the index for this attribute location.
     */
    fun getIndex(paramIndex: UInt = 0u): UInt {
        return when (this) {
            Return -> 0u
            Param -> {
                assert(paramIndex <= UInt.MAX_VALUE - 2u) { "Param index must be <= UInt.MAX_VALUE - 2" }
                paramIndex + 1u
            }
            Function -> UInt.MAX_VALUE
        }
    }
}
