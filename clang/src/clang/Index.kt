package clang

import clang.c.*
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment


@JvmInline
value class Index(val index: CXIndex) {

    fun parseTranslationUnit(sourceFilename: String, options: UInt = TranslationUnit.None): TranslationUnit {
        val tu = Arena.ofConfined().use { onStack ->
            clang_parseTranslationUnit(
                index,
                onStack.allocateFrom(sourceFilename),
                MemorySegment.NULL,
                0,
                MemorySegment.NULL,
                0u,
                options
            )
        }

        return TranslationUnit(tu)
    }


    fun dispose() {
        clang_disposeIndex(index)
    }

}


fun createIndex(): Index {
    return Index(clang_createIndex(0, 0))
}