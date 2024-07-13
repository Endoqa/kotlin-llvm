package clang

import java.lang.foreign.Arena

inline fun <R> tempAllocate(action: Arena.() -> R): R {
    return Arena.ofConfined().use(action)
}

internal inline fun <R> isolateOwner(action: Arena.() -> R): R {
    return action(Arena.ofAuto())
}