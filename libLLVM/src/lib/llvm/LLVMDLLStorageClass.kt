// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.llvm

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.Int

public enum class LLVMDLLStorageClass(
    public val `value`: Int,
) {
    efaultStorageClass(0),
    LLImportStorageClass(1),

    /**
     * < Function to be imported from DLL.
     */
    LLExportStorageClass(2),
    ;

    public companion object {
        @JvmStatic
        public val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            LLVMDLLStorageClass::class.java,
            "fromInt",
            MethodType.methodType(LLVMDLLStorageClass::class.java, Int::class.java)
        )

        @JvmStatic
        public val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            LLVMDLLStorageClass::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        public fun fromInt(`value`: Int): LLVMDLLStorageClass = when (value) {
            efaultStorageClass.value -> efaultStorageClass
            LLImportStorageClass.value -> LLImportStorageClass
            LLExportStorageClass.value -> LLExportStorageClass
            else -> error("enum not found")
        }
    }
}
