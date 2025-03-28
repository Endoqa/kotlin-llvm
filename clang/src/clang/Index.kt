package clang

import lib.clang.CXIndex
import lib.clang.clang_createIndex
import lib.clang.clang_disposeIndex


@JvmInline
value class Index private constructor(val index: CXIndex) {


    constructor(
        exclude: Boolean,
        diagnostics: Boolean
    ) : this(createIndex(exclude, diagnostics))

    fun dispose() {
        clang_disposeIndex(index)
    }

}


private fun createIndex(exclude: Boolean, diagnostics: Boolean): CXIndex {
    return clang_createIndex(if (exclude) 1 else 0, if (diagnostics) 1 else 0)
}