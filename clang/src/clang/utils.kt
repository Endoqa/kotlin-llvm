package clang

import lib.clang.CXString
import lib.clang.clang_disposeString
import lib.clang.clang_getCString
import java.lang.foreign.Arena
import java.lang.ref.Cleaner

inline fun <R> tempAllocate(action: Arena.() -> R): R {
    return Arena.ofConfined().use(action)
}

/**
 * Temporary on stack allocator
 */
inline fun <R> unsafe(action: (Arena) -> R): R {
    return Arena.ofConfined().use(action)
}

internal inline fun <R> isolateOwner(action: Arena.() -> R): R {
    return action(Arena.ofAuto())
}

fun CXString.unwrap(): String {
    val str = clang_getCString(this).getString(0)
    clang_disposeString(this)
    return str
}

fun String.Companion.from(action: Arena.() -> CXString) : String {
    return tempAllocate {
        action(this).unwrap()
    }
}

private val cleaner = Cleaner.create()

fun gc(obj: Any, action: () -> Unit) {
    cleaner.register(obj, action)
}
