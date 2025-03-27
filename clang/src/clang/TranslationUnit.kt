package clang

import lib.clang.CXTranslationUnit
import lib.clang.clang_getDiagnostic
import lib.clang.clang_getNumDiagnostics
import lib.clang.clang_getTranslationUnitCursor
import java.lang.foreign.Arena

class TranslationUnit(
    val tu: CXTranslationUnit,
    private val owner: Arena = Arena.ofAuto()
) {


    val entity get() = isolateOwner { Entity(clang_getTranslationUnitCursor(tu), this) }


    val diagnostics: List<Diagnostic>
        get() {
            return List(clang_getNumDiagnostics(tu).toInt()) {
                Diagnostic(clang_getDiagnostic(tu, it.toUInt()))
            }
        }


    companion object {
        val None: UInt = 0x00000000u
        val DetailedPreprocessingRecord: UInt = 0x00000001u
        val Incomplete: UInt = 0x00000002u
        val PrecompiledPreamble: UInt = 0x00000004u
        val CacheCompletionResults: UInt = 0x00000008u
        val ForSerialization: UInt = 0x00000010u
        val CXXChainedPCH: UInt = 0x00000020u
        val SkipFunctionBodies: UInt = 0x00000040u
        val IncludeBriefCommentsInCodeCompletion: UInt = 0x00000080u
        val CreatePreambleOnFirstParse: UInt = 0x00000100u
        val KeepGoing: UInt = 0x00000200u
        val SingleFileParse: UInt = 0x00000400u
        val LimitSkipFunctionBodiesToPreamble: UInt = 0x00000800u
        val IncludeAttributedTypes: UInt = 0x00001000u
        val VisitImplicitAttributes: UInt = 0x00002000u
        val IgnoreNonErrorsFromIncludedFiles: UInt = 0x00004000u
        val RetainExcludedConditionalBlocks: UInt = 0x00008000u
    }


}