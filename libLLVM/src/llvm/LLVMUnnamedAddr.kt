// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.llvm

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.Int
import kotlin.jvm.JvmStatic
import lib.llvm.LLVMUnnamedAddr.GlobalUnnamedAddr
import lib.llvm.LLVMUnnamedAddr.LocalUnnamedAddr
import lib.llvm.LLVMUnnamedAddr.NoUnnamedAddr

public enum class LLVMUnnamedAddr(
  public val `value`: Int,
) {
  NoUnnamedAddr(0),
  LocalUnnamedAddr(1),
  GlobalUnnamedAddr(2),
  ;

  public companion object {
    @JvmStatic
    public val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            LLVMUnnamedAddr::class.java,
            "fromInt",
            MethodType.methodType(LLVMUnnamedAddr::class.java, Int::class.java)
        )

    @JvmStatic
    public val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            LLVMUnnamedAddr::class.java,
            "value",
            Int::class.java
        )

    @JvmStatic
    public fun fromInt(`value`: Int): LLVMUnnamedAddr = when (value) {
      NoUnnamedAddr.value -> NoUnnamedAddr
      LocalUnnamedAddr.value -> LocalUnnamedAddr
      GlobalUnnamedAddr.value -> GlobalUnnamedAddr
      else -> error("enum not found")
    }
  }
}
