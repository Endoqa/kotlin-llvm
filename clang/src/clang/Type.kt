package clang

import lib.clang.*
import java.lang.foreign.Arena


class Type(
    val t: CXType,
    private val owner: Arena? = null
) {


    companion object {
        /**
         * Maps a given `CXType` to a `Type` instance if the `CXType` is valid, otherwise returns null.
         *
         * @param t the `CXType` instance to be mapped.
         * @param owner the optional `Arena` instance associated with the `Type`, defaulted to null.
         * @return a `Type` instance representing the mapped `CXType` if valid, or null if the `CXType` is invalid.
         */
        private fun safeMap(t: CXType, owner: Arena? = null): Type? {
            if (t.kind == CXTypeKind.Invalid) {
                return null
            }

            return Type(t, owner)
        }
    }


    val kind: TypeKind by t::kind

    val displayName by lazy { String.from { clang_getTypeSpelling(t) } }

    val align get() = clang_Type_getAlignOf(t)


    fun offsetOf(fieldName: String): Long {
        return unsafe {
            val field = it.allocateFrom(fieldName)
            clang_Type_getOffsetOf(t, field)
        }
    }

    val size get() = clang_Type_getSizeOf(t)
    val addressSpace get() = clang_getAddressSpace(t)

    val argumentTypes: List<Type> by lazy {
        List(clang_getNumArgTypes(t)) {
            isolateOwner { Type(clang_getArgType(t, it.toUInt()), this) }
        }
    }

    val callingConvention: CallingConv get() = clang_getFunctionTypeCallingConv(t)

    val canonicalType: Type by lazy { isolateOwner { Type(clang_getCanonicalType(t), this) } }
    val classType: Type? by lazy {
        isolateOwner { safeMap(clang_getPointeeType(t), this) }
    }

    val declaration by lazy { isolateOwner { Entity(clang_getTypeDeclaration(t), this) } }

    val elaboratedType: Type? by lazy { isolateOwner { safeMap(clang_getResultType(t), this) } }
    val elementType: Type? by lazy { isolateOwner { safeMap(clang_getElementType(t), this) } }
    val exceptionSpecification: ExceptionSpecificationType
        get() = CXCursor_ExceptionSpecificationKind.fromInt(
            clang_getExceptionSpecificationType(t)
        )

}