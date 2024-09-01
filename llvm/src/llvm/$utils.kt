package llvm

import java.lang.foreign.Arena


inline fun <R> confined(action: (Arena) -> R) : R {
    return Arena.ofConfined().use(action)
}