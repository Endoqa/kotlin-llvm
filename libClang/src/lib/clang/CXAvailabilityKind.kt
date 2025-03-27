// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.clang

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.Int

/**
 *
 * Describes the availability of a particular entity, which indicates
 * whether the use of this entity will result in a warning or error due to
 * it being deprecated or unavailable.
 */
public enum class CXAvailabilityKind(
    public val `value`: Int,
) {
    /**
     *
     * The entity is available.
     */
    Available(0),

    /**
     *
     * The entity is available, but has been deprecated (and its use is
     * not recommended).
     */
    Deprecated(1),

    /**
     *
     * The entity is not available; any use of it will be an error.
     */
    NotAvailable(2),

    /**
     *
     * The entity is available, but not accessible; any use of it will be
     * an error.
     */
    NotAccessible(3),
    ;

    public companion object {
        @JvmStatic
        public val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            CXAvailabilityKind::class.java,
            "fromInt",
            MethodType.methodType(CXAvailabilityKind::class.java, Int::class.java)
        )

        @JvmStatic
        public val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            CXAvailabilityKind::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        public fun fromInt(`value`: Int): CXAvailabilityKind = when (value) {
            Available.value -> Available
            Deprecated.value -> Deprecated
            NotAvailable.value -> NotAvailable
            NotAccessible.value -> NotAccessible
            else -> error("enum not found")
        }
    }
}
