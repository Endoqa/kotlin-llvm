package clang

import clang.c.*
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.SegmentAllocator


class Cursor(
    val c: CXCursor,
    private val owner: Arena = Arena.ofAuto()
) : SegmentAllocator by owner {


    val kind: CursorKind = clang_getCursorKind(c)
//        get() {
//            return clang_getCursorKind(c)
//        }

    val type: Type by lazy { Type(clang_getCursorType(c)) }
    val enumDeclIntegerType: Type by lazy { Type(clang_getEnumDeclIntegerType(c)) }
    val sourceLocation: SourceLocation? by lazy {
        tempAllocate {
            val loc = clang_getCursorLocation(c)
            if (clang_equalLocations(loc, clang_getNullLocation()) != 0u) {
                null
            } else {
                SourceLocation(loc)
            }
        }
    }
    val extent: SourceRange? by lazy {
        tempAllocate {
            val range = clang_getCursorExtent(c)
            if (clang_Range_isNull(range) != 0) {
                null
            } else {
                SourceRange(range)
            }
        }
    }


    val definition: Cursor by lazy { Cursor(clang_getCursorDefinition(c)) }
    val cursorReferenced: Cursor by lazy { Cursor(clang_getCursorReferenced(c)) }

    val numberOfArgs get() = clang_Cursor_getNumArguments(c)
    val arugments: List<Cursor> by lazy {
        if (numberOfArgs == 0) {
            emptyList()
        } else {
            List(numberOfArgs) {
                Cursor(clang_Cursor_getArgument(c, it.toUInt()))
            }
        }
    }
    val bitFieldWidth get() = clang_getFieldDeclBitWidth(c)

    //todo language linkage


    val isDeclaration get() = clang_isDeclaration(kind) != 0u
    val isPreprocessing get() = clang_isPreprocessing(kind) != 0u
    val isInvalid get() = clang_isPreprocessing(kind) != 0u
    val isDefinition get() = clang_isCursorDefinition(c) != 0u
    val isAttribute get() = clang_isAttribute(kind) != 0u
    val isAnonymousStruct get() = clang_Cursor_isAnonymousRecordDecl(c) != 0u
    val isAnonymous get() = clang_Cursor_isAnonymous(c) != 0u
    val isMacroFunctionLike get() = clang_Cursor_isMacroFunctionLike(c) != 0u
    val isFunctionInlined get() = clang_Cursor_isFunctionInlined(c) != 0u
    val isBitField get() = clang_Cursor_isBitField(c) != 0u

    val enumConstantValue get() = clang_getEnumConstantDeclValue(c)
    val enumConstantUnsignedValue get() = clang_getEnumConstantDeclUnsignedValue(c)

    val spelling: String by lazy {
        tempAllocate {
            clang_getCursorSpelling(c).unwrap()
        }
    }
    val USR: String by lazy {
        tempAllocate {
            clang_getCursorUSR(c).unwrap()
        }
    }
    val prettyPrinted: String by lazy {
        tempAllocate {
            clang_getCursorPrettyPrinted(c, printingPolicy).unwrap()
        }
    }

    val printingPolicy get() = clang_getCursorPrintingPolicy(c)


    val displayName: String by lazy {
        tempAllocate { clang_getCursorDisplayName(c).unwrap() }
    }


    fun forEach(action: (Cursor) -> Unit) {
        var ex: Throwable? = null
        val visitor = clang.c.callback.CXCursorVisitor { c, _, _ ->
            try {
                action(Cursor(c))
                return@CXCursorVisitor CXChildVisitResult.Continue
            } catch (e: Exception) {
                ex = e
                return@CXCursorVisitor CXChildVisitResult.Break
            }
        }
        tempAllocate { clang_visitChildren(c, visitor.allocate(this), MemorySegment.NULL) }

        ex?.let { throw it }
    }


    override fun equals(other: Any?): Boolean {
        if (other !is Cursor) return false

        return clang_equalCursors(c, other.c) != 0u
    }

    override fun hashCode(): Int {
        return displayName.hashCode()
    }
}