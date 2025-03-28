// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.clang

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.Int

/**
 *
 * Describes a kind of token.
 */
public enum class CXTokenKind(
    public val `value`: Int,
) {
    /**
     *
     * A token that contains some kind of punctuation.
     */
    Punctuation(0),

    /**
     *
     * A language keyword.
     */
    Keyword(1),

    /**
     *
     * An identifier (that is not a keyword).
     */
    Identifier(2),

    /**
     *
     * A numeric, string, or character literal.
     */
    Literal(3),

    /**
     *
     * A comment.
     */
    Comment(4),
    ;

    public companion object {
        @JvmStatic
        public val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            CXTokenKind::class.java,
            "fromInt",
            MethodType.methodType(CXTokenKind::class.java, Int::class.java)
        )

        @JvmStatic
        public val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            CXTokenKind::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        public fun fromInt(`value`: Int): CXTokenKind = when (value) {
            Punctuation.value -> Punctuation
            Keyword.value -> Keyword
            Identifier.value -> Identifier
            Literal.value -> Literal
            Comment.value -> Comment
            else -> error("enum not found")
        }
    }
}
