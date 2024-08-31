// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.llvm

import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.SegmentAllocator
import java.lang.foreign.StructLayout
import kotlin.Boolean
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic

@JvmInline
public value class LLVMOrcOpaqueDefinitionGenerator(
  public val `$mem`: MemorySegment,
) {
  public constructor(gc: Boolean) : this(kotlin.run {
      require(gc) { "Do not call this if gc is not want" }
      Arena.ofAuto().allocate(LLVMOrcOpaqueDefinitionGenerator.layout)
  })

  public companion object {
    public val layout: StructLayout = MemoryLayout.structLayout(
    ).withName("LLVMOrcOpaqueDefinitionGenerator")

    @JvmStatic
    public fun allocate(alloc: SegmentAllocator): LLVMOrcOpaqueDefinitionGenerator =
        LLVMOrcOpaqueDefinitionGenerator(alloc.allocate(LLVMOrcOpaqueDefinitionGenerator.layout))
  }
}
