package clang

import lib.clang.*
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout


@JvmInline
value class Index(val index: CXIndex) {

    fun parseTranslationUnit(
        sourceFilename: String,
        args: List<String> = emptyList(),
        options: UInt = TranslationUnit.None
    ): TranslationUnit {
        val owner = Arena.ofAuto()
        val tu = owner.allocate(ValueLayout.ADDRESS)

        val errorCode = Arena.ofConfined().use { onStack ->

            val cArgs: Pointer<Pointer<Byte>> = if (args.isEmpty()) MemorySegment.NULL else {
                val ptrs = onStack.allocate(MemoryLayout.sequenceLayout(args.size.toLong(), ValueLayout.ADDRESS))

                args.forEachIndexed { index, s ->
                    ptrs.setAtIndex(ValueLayout.ADDRESS, index.toLong(), onStack.allocateFrom(s))
                }

                ptrs
            }


            clang_parseTranslationUnit2(
                index,
                onStack.allocateFrom(sourceFilename),
                cArgs,
                args.size,
                MemorySegment.NULL,
                0u,
                options,
                tu
            )
        }

        return when (errorCode) {
            CXErrorCode.Success -> TranslationUnit(tu, owner)
            else -> error(errorCode)
        }


    }


    fun dispose() {
        clang_disposeIndex(index)
    }

}


fun createIndex(): Index {
    return Index(clang_createIndex(0, 0))
}