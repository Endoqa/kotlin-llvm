// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.clang

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.Int

/**
 *
 * Extra C++ template information for an entity. This can apply to:
 * CXIdxEntity_Function
 * CXIdxEntity_CXXClass
 * CXIdxEntity_CXXStaticMethod
 * CXIdxEntity_CXXInstanceMethod
 * CXIdxEntity_CXXConstructor
 * CXIdxEntity_CXXConversionFunction
 * CXIdxEntity_CXXTypeAlias
 */
public enum class CXIdxEntityCXXTemplateKind(
    public val `value`: Int,
) {
    NonTemplate(0),
    Template(1),
    TemplatePartialSpecialization(2),
    TemplateSpecialization(3),
    ;

    public companion object {
        @JvmStatic
        public val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            CXIdxEntityCXXTemplateKind::class.java,
            "fromInt",
            MethodType.methodType(CXIdxEntityCXXTemplateKind::class.java, Int::class.java)
        )

        @JvmStatic
        public val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            CXIdxEntityCXXTemplateKind::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        public fun fromInt(`value`: Int): CXIdxEntityCXXTemplateKind = when (value) {
            NonTemplate.value -> NonTemplate
            Template.value -> Template
            TemplatePartialSpecialization.value -> TemplatePartialSpecialization
            TemplateSpecialization.value -> TemplateSpecialization
            else -> error("enum not found")
        }
    }
}
