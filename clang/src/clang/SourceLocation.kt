package clang

import lib.clang.*
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout
import kotlin.io.path.Path

class SourceLocation(
    val sl: CXSourceLocation,
    private val owner: Arena? = null
) {


    val fileLocation: Location by lazy {
        getLocation(::clang_getFileLocation)
    }

    val expansionLocation: Location by lazy {
        getLocation(::clang_getExpansionLocation)
    }

    val spellingLocation: Location by lazy {
        getLocation(::clang_getSpellingLocation)
    }

    val inSystemHeader get() = clang_Location_isInSystemHeader(sl) != 0
    val fromMainFile get() = clang_Location_isFromMainFile(sl) != 0

    override fun equals(other: Any?): Boolean {
        if (other !is SourceLocation) return false

        return this.fileLocation == other.fileLocation
    }

    override fun hashCode(): Int {
        return this.fileLocation.hashCode()
    }


    private inline fun getLocation(func: (CXSourceLocation, Pointer<CXFile>, Pointer<UInt>, Pointer<UInt>, Pointer<UInt>) -> Unit): Location {
        return tempAllocate {
            val file = allocate(ValueLayout.ADDRESS)
            val line = allocate(ValueLayout.JAVA_INT)
            val col = allocate(ValueLayout.JAVA_INT)
            val offset = allocate(ValueLayout.JAVA_INT)

            func(sl, file, line, col, offset)


            Location(
                path = if (file == MemorySegment.NULL) null else Path(clang_getFileName(file).unwrap()),
                line = line[ValueLayout.JAVA_INT, 0].toUInt(),
                col = col[ValueLayout.JAVA_INT, 0].toUInt(),
                offset = offset[ValueLayout.JAVA_INT, 0].toUInt(),
            )
        }
    }

}