package clang

import java.nio.file.Path

data class Location(
    val path: Path?,
    val line: UInt,
    val col: UInt,
    val offset: UInt
)