// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.clang

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.Int

/**
 *
 * Describes a single piece of text within a code-completion string.
 *
 * Each "chunk" within a code-completion string (\c CXCompletionString) is
 * either a piece of text with a specific "kind" that describes how that text
 * should be interpreted by the client or is another completion string.
 */
public enum class CXCompletionChunkKind(
    public val `value`: Int,
) {
    /**
     *
     * A code-completion string that describes "optional" text that
     * could be a part of the template (but is not required).
     *
     * The Optional chunk is the only kind of chunk that has a code-completion
     * string for its representation, which is accessible via
     * \c clang_getCompletionChunkCompletionString(). The code-completion string
     * describes an additional part of the template that is completely optional.
     * For example, optional chunks can be used to describe the placeholders for
     * arguments that match up with defaulted function parameters, e.g. given:
     *
     * \code
     * void f(int x, float y = 3.14, double z = 2.71828);
     * \endcode
     *
     * The code-completion string for this function would contain:
     * - a TypedText chunk for "f".
     * - a LeftParen chunk for "(".
     * - a Placeholder chunk for "int x"
     * - an Optional chunk containing the remaining defaulted arguments, e.g.,
     * - a Comma chunk for ","
     * - a Placeholder chunk for "float y"
     * - an Optional chunk containing the last defaulted argument:
     * - a Comma chunk for ","
     * - a Placeholder chunk for "double z"
     * - a RightParen chunk for ")"
     *
     * There are many ways to handle Optional chunks. Two simple approaches are:
     * - Completely ignore optional chunks, in which case the template for the
     * function "f" would only include the first parameter ("int x").
     * - Fully expand all optional chunks, in which case the template for the
     * function "f" would have all of the parameters.
     */
    Optional(0),

    /**
     *
     * Text that a user would be expected to type to get this
     * code-completion result.
     *
     * There will be exactly one "typed text" chunk in a semantic string, which
     * will typically provide the spelling of a keyword or the name of a
     * declaration that could be used at the current code point. Clients are
     * expected to filter the code-completion results based on the text in this
     * chunk.
     */
    TypedText(1),

    /**
     *
     * Text that should be inserted as part of a code-completion result.
     *
     * A "text" chunk represents text that is part of the template to be
     * inserted into user code should this particular code-completion result
     * be selected.
     */
    Text(2),

    /**
     *
     * Placeholder text that should be replaced by the user.
     *
     * A "placeholder" chunk marks a place where the user should insert text
     * into the code-completion template. For example, placeholders might mark
     * the function parameters for a function declaration, to indicate that the
     * user should provide arguments for each of those parameters. The actual
     * text in a placeholder is a suggestion for the text to display before
     * the user replaces the placeholder with real code.
     */
    Placeholder(3),

    /**
     *
     * Informative text that should be displayed but never inserted as
     * part of the template.
     *
     * An "informative" chunk contains annotations that can be displayed to
     * help the user decide whether a particular code-completion result is the
     * right option, but which is not part of the actual template to be inserted
     * by code completion.
     */
    Informative(4),

    /**
     *
     * Text that describes the current parameter when code-completion is
     * referring to function call, message send, or template specialization.
     *
     * A "current parameter" chunk occurs when code-completion is providing
     * information about a parameter corresponding to the argument at the
     * code-completion point. For example, given a function
     *
     * \code
     * int add(int x, int y);
     * \endcode
     *
     * and the source code \c add(, where the code-completion point is after the
     * "(", the code-completion string will contain a "current parameter" chunk
     * for "int x", indicating that the current argument will initialize that
     * parameter. After typing further, to \c add(17, (where the code-completion
     * point is after the ","), the code-completion string will contain a
     * "current parameter" chunk to "int y".
     */
    CurrentParameter(5),

    /**
     *
     * A left parenthesis ('('), used to initiate a function call or
     * signal the beginning of a function parameter list.
     */
    LeftParen(6),

    /**
     *
     * A right parenthesis (')'), used to finish a function call or
     * signal the end of a function parameter list.
     */
    RightParen(7),

    /**
     *
     * A left bracket ('[').
     */
    LeftBracket(8),

    /**
     *
     * A right bracket (']').
     */
    RightBracket(9),

    /**
     *
     * A left brace ('{').
     */
    LeftBrace(10),

    /**
     *
     * A right brace ('}').
     */
    RightBrace(11),

    /**
     *
     * A left angle bracket ('<').
     */
    LeftAngle(12),

    /**
     *
     * A right angle bracket ('>').
     */
    RightAngle(13),

    /**
     *
     * A comma separator (',').
     */
    Comma(14),

    /**
     *
     * Text that specifies the result type of a given result.
     *
     * This special kind of informative chunk is not meant to be inserted into
     * the text buffer. Rather, it is meant to illustrate the type that an
     * expression using the given completion string would have.
     */
    ResultType(15),

    /**
     *
     * A colon (':').
     */
    Colon(16),

    /**
     *
     * A semicolon (';').
     */
    SemiColon(17),

    /**
     *
     * An '=' sign.
     */
    Equal(18),

    /**
     *
     * Horizontal space (' ').
     */
    HorizontalSpace(19),

    /**
     *
     * Vertical space ('\\n'), after which it is generally a good idea to
     * perform indentation.
     */
    VerticalSpace(20),
    ;

    public companion object {
        @JvmStatic
        public val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            CXCompletionChunkKind::class.java,
            "fromInt",
            MethodType.methodType(CXCompletionChunkKind::class.java, Int::class.java)
        )

        @JvmStatic
        public val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            CXCompletionChunkKind::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        public fun fromInt(`value`: Int): CXCompletionChunkKind = when (value) {
            Optional.value -> Optional
            TypedText.value -> TypedText
            Text.value -> Text
            Placeholder.value -> Placeholder
            Informative.value -> Informative
            CurrentParameter.value -> CurrentParameter
            LeftParen.value -> LeftParen
            RightParen.value -> RightParen
            LeftBracket.value -> LeftBracket
            RightBracket.value -> RightBracket
            LeftBrace.value -> LeftBrace
            RightBrace.value -> RightBrace
            LeftAngle.value -> LeftAngle
            RightAngle.value -> RightAngle
            Comma.value -> Comma
            ResultType.value -> ResultType
            Colon.value -> Colon
            SemiColon.value -> SemiColon
            Equal.value -> Equal
            HorizontalSpace.value -> HorizontalSpace
            VerticalSpace.value -> VerticalSpace
            else -> error("enum not found")
        }
    }
}
