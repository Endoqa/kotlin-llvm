# FFI Guide for Kotlin-LLVM Clang Module

## Overview

This guide explains how Foreign Function Interface (FFI) is implemented and used in the Kotlin-LLVM Clang module. The module provides a Kotlin wrapper around the Clang C API, allowing Kotlin code to interact with Clang's functionality for parsing and analyzing C/C++ code.

## FFI Approach

The Kotlin-LLVM Clang module uses Java's Foreign Function & Memory API (FFM) to interact with the native Clang library. This approach involves:

1. **Memory Management**: Using Java's `Arena` class for managing native memory.
2. **Type Mapping**: Wrapping C types in Kotlin classes and using type aliases for C enums.
3. **Function Calling**: Directly calling C functions from Kotlin code.
4. **Resource Management**: Explicitly disposing of native resources and using arenas for automatic memory management.

## How to Use FFI in This Project

### Setting Up

The Clang module depends on the libClang module, which provides the low-level bindings to the Clang C API. This dependency is defined in the `module.yaml` file:

```yaml
dependencies:
    - ../libClang
```

### Creating and Using Clang Objects

To use the Clang API, you typically follow these steps:

1. Create an index using `createIndex()`.
2. Parse a translation unit using `index.parseTranslationUnit()`.
3. Access the translation unit's contents using its properties and methods.
4. Clean up resources when done.

Example:

```kotlin
// Create an index
val index = createIndex()

// Parse a translation unit
val tu = index.parseTranslationUnit(
    sourceFilename = "example.cpp",
    args = listOf("-std=c++11"),
    options = TranslationUnit.DetailedPreprocessingRecord
)

// Access the translation unit's contents
val entity = tu.entity
println("Translation unit: ${entity.name}")

// Check for diagnostics
for (diagnostic in tu.diagnostics) {
    println("${diagnostic.severity}: ${diagnostic.spelling}")
}

// Clean up resources
index.dispose()
```

## Common Patterns and Best Practices

### Memory Management

The project uses Java's Foreign Function & Memory API (FFM) for memory management, specifically the `Arena` class, which provides a safe and efficient way to manage native memory. Understanding how memory is managed is crucial for preventing memory leaks and ensuring proper resource cleanup.

#### Arena Lifecycle Management

The project uses several patterns for memory management:

1. **Temporary Allocations**: Use `tempAllocate` or `unsafe` for short-lived native memory allocations that don't need to outlive the current method call.

```kotlin
val result = tempAllocate {
    // Use 'this' as an Arena
    // Memory is automatically freed when the block exits
    val nativeString = this.allocateFrom("example")
    // Use nativeString...
}
// nativeString is no longer valid here
```

Under the hood, `tempAllocate` creates a confined Arena using `Arena.ofConfined().use { ... }`, which ensures that all memory allocated within the block is automatically freed when the block exits, even if an exception occurs.

2. **Long-lived Allocations**: Use `Arena.ofAuto()` for allocations that need to persist beyond the current method call.

```kotlin
// In a method that creates a long-lived object
fun parseTranslationUnit(
    sourceFilename: String,
    args: List<String>,
    options: UInt
): TranslationUnit {
    val owner = Arena.ofAuto()  // Long-lived arena
    val tu = owner.allocate(ValueLayout.ADDRESS)

    // Use tu to store the result of parsing

    // Pass the owner arena to the TranslationUnit so it can manage the memory
    return TranslationUnit(tu, owner)
}
```

3. **Owner Isolation**: Use `isolateOwner` to create objects with their own memory arena, which helps prevent memory leaks by ensuring that each object manages its own native memory.

```kotlin
val entity = isolateOwner { Entity(cursor, this) }
```

The `isolateOwner` function creates a new automatic Arena using `Arena.ofAuto()` and passes it to the provided lambda. This allows the created object to manage its own memory independently of other objects.

4. **Resource Cleanup**: Use explicit `dispose` methods or Java's `Cleaner` for cleaning up native resources that aren't automatically managed by Arenas.

```kotlin
// Register a cleanup action with the Cleaner
gc(obj) {
    // This will be called when 'obj' is garbage collected
    clang_disposeIndex(index)
}

// Or call dispose explicitly when you're done with the resource
index.dispose()
```

#### Memory Safety Considerations

When working with native memory in FFI, keep these safety considerations in mind:

1. **Never access memory after its Arena is closed**: Accessing memory segments after their Arena is closed can lead to undefined behavior or crashes.

2. **Be careful with memory lifetimes**: Ensure that memory allocated for native resources lives at least as long as the resources themselves.

3. **Avoid memory leaks**: Always ensure that native resources are properly disposed of, either through explicit `dispose` calls or by using Arenas that are automatically closed.

4. **Use the right Arena type for the job**:
   - `Arena.ofConfined()` for short-lived allocations within a single method
   - `Arena.ofAuto()` for longer-lived allocations that need to persist across method calls
   - `Arena.global()` for allocations that need to live for the entire duration of the program (use sparingly)

#### Example: Proper Memory Management in Translation Unit Parsing

Here's an example of proper memory management when parsing a translation unit:

```kotlin
fun parseTranslationUnit(sourceFilename: String, args: List<String>): TranslationUnit {
    // Create a long-lived arena for the translation unit
    val owner = Arena.ofAuto()
    val tu = owner.allocate(ValueLayout.ADDRESS)

    // Use a temporary arena for the parsing operation
    val errorCode = Arena.ofConfined().use { onStack: Arena ->
        // Allocate memory for the source filename and arguments
        val cFilename = onStack.allocateFrom(sourceFilename)

        // Allocate memory for the arguments array
        val cArgs = if (args.isEmpty()) MemorySegment.NULL else {
            val ptrs = onStack.allocate(MemoryLayout.sequenceLayout(args.size.toLong(), ValueLayout.ADDRESS))
            args.forEachIndexed { index, arg ->
                ptrs.setAtIndex(ValueLayout.ADDRESS, index.toLong(), onStack.allocateFrom(arg))
            }
            ptrs
        }

        // Call the native function
        clang_parseTranslationUnit2(
            index,
            cFilename,
            cArgs,
            args.size,
            MemorySegment.NULL,
            0u,
            options,
            tu
        )
    }

    // Handle the result
    return when (errorCode) {
        CXErrorCode.Success -> TranslationUnit(tu, owner)  // Pass ownership of the arena
        else -> error(errorCode)
    }
}
```

In this example:
- The `owner` arena is created with `Arena.ofAuto()` and will be managed by the returned `TranslationUnit` object
- A temporary arena is created with `Arena.ofConfined().use { ... }` for the short-lived allocations needed during parsing
- All memory allocated within the temporary arena is automatically freed when the block exits
- The `TranslationUnit` takes ownership of the `tu` memory segment and its associated arena

### Type Mapping

The project uses several patterns for mapping between C and Kotlin types:

1. **Wrapper Classes**: Use classes to wrap C types and provide a more idiomatic Kotlin API.

```kotlin
class Entity(
    private val raw: CXCursor,
    private val owner: Arena? = null
) {
    // Kotlin-friendly API
}
```

2. **Value Classes**: Use value classes for lightweight wrappers around C types.

```kotlin
@JvmInline
value class Index(val index: CXIndex) {
    // Methods for working with the index
}
```

3. **Type Aliases**: Use type aliases to create Kotlin-friendly names for C types.

```kotlin
typealias CursorKind = CXCursorKind
typealias TypeKind = CXTypeKind
```

### String Handling

The project provides utilities for converting between C strings and Kotlin strings:

```kotlin
// Convert a CXString to a Kotlin String
val name = String.from { clang_getCursorSpelling(cursor) }

// Or use the extension function
val name = clang_getCursorSpelling(cursor).unwrap()
```

### Lazy Initialization

The project uses lazy initialization to avoid unnecessary native calls:

```kotlin
val name by lazy { String.from { clang_getCursorSpelling(raw) } }
```

### Error Handling

The project handles errors by checking return codes from C functions:

```kotlin
// Example error handling pattern
val errorCode = clang_parseTranslationUnit2(/* ... */)
// Handle the error code
when (errorCode) {
    CXErrorCode.Success -> TranslationUnit(tu, owner)
    else -> error(errorCode)
}
```

## Examples from the Codebase

### Parsing a Translation Unit

From `Index.kt`:

```kotlin
fun parseTranslationUnit(
    sourceFilename: String,
    args: List<String> = emptyList(),
    options: UInt = TranslationUnit.None
): TranslationUnit {
    val owner = Arena.ofAuto()
    val tu = owner.allocate(ValueLayout.ADDRESS)

    val errorCode = Arena.ofConfined().use { onStack: Arena ->
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
```

### Accessing Entity Properties

From `Entity.kt`:

```kotlin
val kind get() = clang_getCursorKind(raw)
val displayName by lazy { String.from { clang_getCursorDisplayName(raw) } }

val location by lazy {
    isolateOwner { SourceLocation(clang_getCursorLocation(raw), this) }
}

val range by lazy {
    isolateOwner { SourceRange(clang_getCursorExtent(raw), this) }
}
```

### Working with Types

From `Type.kt`:

```kotlin
val kind: TypeKind by t::kind
val displayName by lazy { String.from { clang_getTypeSpelling(t) } }
val align get() = clang_Type_getAlignOf(t)

fun offsetOf(fieldName: String): Long {
    return unsafe {
        val field = it.allocateFrom(fieldName)
        clang_Type_getOffsetOf(t, field)
    }
}
```

## Conclusion

The Kotlin-LLVM Clang module provides a powerful and idiomatic Kotlin API for working with Clang's functionality. By following the patterns and best practices described in this guide, you can effectively use FFI to interact with native libraries in your Kotlin code.
