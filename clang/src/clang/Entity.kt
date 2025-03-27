package clang

import lib.clang.*
import java.lang.foreign.Arena

class Entity(
    private val raw: CXCursor,
    private val owner: Arena? = null
) {

    companion object {
        private fun safeMap(cursor: CXCursor, owner: Arena? = null): Entity? {
            if (clang_Cursor_isNull(cursor) != 0) {
                return null
            }
            return Entity(cursor, owner)
        }
    }

    val kind get() = clang_getCursorKind(raw)
    val displayName by lazy { String.from { clang_getCursorDisplayName(raw) } }
    // pretty printer TODO

    val location by lazy {
        isolateOwner { SourceLocation(clang_getCursorLocation(raw), this) }
    }

    val range by lazy {
        isolateOwner { SourceRange(clang_getCursorExtent(raw), this) }
    }

    val accessibility: Accessibility get() = clang_getCXXAccessSpecifier(raw)

    val arguments: List<Entity>
        get() {
            val numArgs = clang_Cursor_getNumArguments(raw)
            return List(numArgs) {
                isolateOwner { Entity(clang_Cursor_getArgument(raw, it.toUInt()), this) }
            }
        }

    val availability: Availability get() = clang_getCursorAvailability(raw)
    val bitFieldWidth get() = clang_getFieldDeclBitWidth(raw)

    val canonicalEntity by lazy { isolateOwner { Entity(clang_getCanonicalCursor(raw), this) } }
    val comment by lazy { String.from { clang_Cursor_getRawCommentText(raw) } }


    val definition: Entity? by lazy {
        isolateOwner { safeMap(clang_getCursorDefinition(raw), this) }
    }

    val lexicalParent: Entity? by lazy {
        isolateOwner { safeMap(clang_getCursorLexicalParent(raw), this) }
    }

    val name by lazy { String.from { clang_getCursorSpelling(raw) } }

    val reference: Entity? by lazy {
        isolateOwner { safeMap(clang_getCursorReferenced(raw), this) }
    }

    val semanticParent: Entity? by lazy {
        isolateOwner { safeMap(clang_getCursorSemanticParent(raw), this) }
    }

    val storageClass: StorageClass get() = clang_Cursor_getStorageClass(raw)
    val template: Entity? by lazy {
        isolateOwner { safeMap(clang_getSpecializedCursorTemplate(raw), this) }
    }

    val type by lazy {
        isolateOwner { Type(clang_getCursorType(raw), this) }
    }

    val typedefUnderlyingType: Type? by lazy {
        isolateOwner { Type(clang_getTypedefDeclUnderlyingType(raw), this) }
    }

    val visibility: Visibility get() = clang_getCursorVisibility(raw)

    val resultType: Type? by lazy {
        isolateOwner { Type(clang_getCursorResultType(raw), this) }
    }

    val hasAttributes: Boolean get() = clang_Cursor_hasAttrs(raw) != 0u
    val isAbstractRecord: Boolean get() = clang_CXXRecord_isAbstract(raw) != 0u
    val isAnonymous: Boolean get() = clang_Cursor_isAnonymous(raw) != 0u
    val isAnonymousRecordDecl: Boolean get() = clang_Cursor_isAnonymousRecordDecl(raw) != 0u
    val isInlineNamespace: Boolean get() = clang_Cursor_isInlineNamespace(raw) != 0u
    val isBitField: Boolean get() = clang_Cursor_isBitField(raw) != 0u
    val isBuiltinMacro: Boolean get() = clang_Cursor_isMacroBuiltin(raw) != 0u
    val icConstMethod: Boolean get() = clang_CXXMethod_isConst(raw) != 0u
    val isConvertingConstructor: Boolean get() = clang_CXXConstructor_isConvertingConstructor(raw) != 0u
    val isCopyConstructor: Boolean get() = clang_CXXConstructor_isCopyConstructor(raw) != 0u
    val isDefaultConstructor: Boolean get() = clang_CXXConstructor_isDefaultConstructor(raw) != 0u
    val isDefinition: Boolean get() = clang_isCursorDefinition(raw) != 0u
    val isDynamicCall: Boolean get() = clang_Cursor_isDynamicCall(raw) != 0
    val isFunctionLikeMacro: Boolean get() = clang_Cursor_isMacroFunctionLike(raw) != 0u
    val isInlineFunction: Boolean get() = clang_Cursor_isFunctionInlined(raw) != 0u
    val isInvalidDeclaration: Boolean get() = clang_isInvalidDeclaration(raw) == 0u
    val isMoveConstructor: Boolean get() = clang_CXXConstructor_isMoveConstructor(raw) != 0u
    val isMutable: Boolean get() = clang_CXXField_isMutable(raw) != 0u
    val isObjCOptional: Boolean get() = clang_Cursor_isObjCOptional(raw) != 0u
    val isPureVirtualMethod: Boolean get() = clang_CXXMethod_isPureVirtual(raw) != 0u
    val isScoped: Boolean get() = clang_EnumDecl_isScoped(raw) != 0u
    val isStaticMethod: Boolean get() = clang_CXXMethod_isStatic(raw) != 0u
    val isVariadic: Boolean get() = clang_Cursor_isVariadic(raw) != 0u
    val isVirtualBase: Boolean get() = clang_isVirtualBase(raw) != 0u
    val isVirtualMethod: Boolean get() = clang_CXXMethod_isVirtual(raw) != 0u

    // Categorization
    val isAttribute: Boolean get() = clang_isAttribute(kind) != 0u
    val isDeclaration: Boolean get() = clang_isDeclaration(kind) != 0u
    val isExpression: Boolean get() = clang_isExpression(kind) != 0u
    val isPreprocessing: Boolean get() = clang_isPreprocessing(kind) != 0u
    val isReference: Boolean get() = clang_isReference(kind) != 0u
    val isStatement: Boolean get() = clang_isStatement(kind) != 0u
    val isUnexposed: Boolean get() = clang_isUnexposed(kind) != 0u
}
