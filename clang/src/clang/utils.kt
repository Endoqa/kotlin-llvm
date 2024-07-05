package clang

import java.lang.foreign.Arena

inline fun <R> tempAllocate(action: Arena.() -> R): R {
    return Arena.ofConfined().use(action)
}