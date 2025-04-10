// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.clang

import java.lang.foreign.*
import java.lang.invoke.VarHandle

/**
 *
 * A parsed comment.
 */
@JvmInline
public value class CXComment(
    public val `$mem`: MemorySegment,
) {
    public var ASTNode: Pointer<Unit>
        get() = ASTNodeHandle.get(this.`$mem`, 0L) as MemorySegment
        set(`value`) {
            ASTNodeHandle.set(this.`$mem`, 0L, value)
        }

    public var TranslationUnit: CXTranslationUnit
        get() = TranslationUnitHandle.get(this.`$mem`, 0L) as MemorySegment
        set(`value`) {
            TranslationUnitHandle.set(this.`$mem`, 0L, value)
        }

    public constructor(gc: Boolean) : this(kotlin.run {
        require(gc) { "Do not call this if gc is not want" }
        Arena.ofAuto().allocate(layout)
    })

    public companion object {
        public val layout: StructLayout = MemoryLayout.structLayout(
            `$RuntimeHelper`.POINTER.withName("ASTNode"),
            `$RuntimeHelper`.POINTER.withName("TranslationUnit"),
        ).withName("CXComment")

        @JvmField
        public val ASTNodeHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("ASTNode"))

        @JvmField
        public val TranslationUnitHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("TranslationUnit"))

        @JvmStatic
        public fun allocate(alloc: SegmentAllocator): CXComment = CXComment(alloc.allocate(layout))
    }
}
