package clang

import clang.c.CXType
import clang.c.clang_Type_getAlignOf
import clang.c.clang_Type_getSizeOf
import clang.c.clang_getPointeeType
import java.lang.foreign.Arena
import java.lang.foreign.SegmentAllocator


class Type(
    val t: CXType,
    private val owner: Arena = Arena.ofAuto()
) : SegmentAllocator by owner {

    val kind: TypeKind by t::kind

    val size get() = clang_Type_getSizeOf(t)
    val align get() = clang_Type_getAlignOf(t)


    val pointeeType: Type by lazy {
        Type(clang_getPointeeType(t))
    }

}