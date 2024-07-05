package clang

import clang.c.CXString
import clang.c.clang_disposeString
import clang.c.clang_getCString

fun CXString.unwrap(): String {
    val str = clang_getCString(this).getString(0)
    clang_disposeString(this)
    return str
}