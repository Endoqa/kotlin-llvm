// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.clang

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.Int

public enum class CXIdxEntityKind(
    public val `value`: Int,
) {
    Unexposed(0),
    Typedef(1),
    Function(2),
    Variable(3),
    Field(4),
    EnumConstant(5),
    ObjCClass(6),
    ObjCProtocol(7),
    ObjCCategory(8),
    ObjCInstanceMethod(9),
    ObjCClassMethod(10),
    ObjCProperty(11),
    ObjCIvar(12),
    Enum(13),
    Struct(14),
    Union(15),
    CXXClass(16),
    CXXNamespace(17),
    CXXNamespaceAlias(18),
    CXXStaticVariable(19),
    CXXStaticMethod(20),
    CXXInstanceMethod(21),
    CXXConstructor(22),
    CXXDestructor(23),
    CXXConversionFunction(24),
    CXXTypeAlias(25),
    CXXInterface(26),
    CXXConcept(27),
    ;

    public companion object {
        @JvmStatic
        public val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            CXIdxEntityKind::class.java,
            "fromInt",
            MethodType.methodType(CXIdxEntityKind::class.java, Int::class.java)
        )

        @JvmStatic
        public val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            CXIdxEntityKind::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        public fun fromInt(`value`: Int): CXIdxEntityKind = when (value) {
            Unexposed.value -> Unexposed
            Typedef.value -> Typedef
            Function.value -> Function
            Variable.value -> Variable
            Field.value -> Field
            EnumConstant.value -> EnumConstant
            ObjCClass.value -> ObjCClass
            ObjCProtocol.value -> ObjCProtocol
            ObjCCategory.value -> ObjCCategory
            ObjCInstanceMethod.value -> ObjCInstanceMethod
            ObjCClassMethod.value -> ObjCClassMethod
            ObjCProperty.value -> ObjCProperty
            ObjCIvar.value -> ObjCIvar
            Enum.value -> Enum
            Struct.value -> Struct
            Union.value -> Union
            CXXClass.value -> CXXClass
            CXXNamespace.value -> CXXNamespace
            CXXNamespaceAlias.value -> CXXNamespaceAlias
            CXXStaticVariable.value -> CXXStaticVariable
            CXXStaticMethod.value -> CXXStaticMethod
            CXXInstanceMethod.value -> CXXInstanceMethod
            CXXConstructor.value -> CXXConstructor
            CXXDestructor.value -> CXXDestructor
            CXXConversionFunction.value -> CXXConversionFunction
            CXXTypeAlias.value -> CXXTypeAlias
            CXXInterface.value -> CXXInterface
            CXXConcept.value -> CXXConcept
            else -> error("enum not found")
        }
    }
}
