package clang

import lib.clang.*

typealias CursorKind = CXCursorKind
typealias CursorLanguage = CXLanguageKind

typealias TypeKind = CXTypeKind

typealias DiagnosticSeverity = CXDiagnosticSeverity

typealias CallingConv = CXCallingConv
typealias ExceptionSpecificationType = CXCursor_ExceptionSpecificationKind
typealias Accessibility = CX_CXXAccessSpecifier
typealias Availability = CXAvailabilityKind
typealias StorageClass = CX_StorageClass
typealias Visibility = CXVisibilityKind