package llvm

import lib.llvm.*
import llvm.types.FunctionType
import llvm.types.StructType
import llvm.types.Type
import llvm.values.FunctionValue
import llvm.values.GlobalValue
import llvm.values.Value
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

/**
 * Represents a reference to an LLVM Module.
 * The underlying module will be disposed when this object is garbage collected.
 */
class Module(
    val M: LLVMModuleRef
) {
    /**
     * Gets the Context from which this Module originates.
     */
    val context by lazy {
        Context(LLVMGetModuleContext(M))
    }

    /**
     * Data layout for this module
     */
    var dataLayout: String
        get() = LLVMGetDataLayout(M).getString(0)
        set(value) {
            confined { temp -> LLVMSetDataLayout(M, temp.allocateFrom(value)) }
        }

    /**
     * Creates a GlobalValue based on a type in an address space.
     */
    fun addGlobal(type: Type, name: String): Value {
        return Value.from(confined { temp -> LLVMAddGlobal(M, type.T, temp.allocateFrom(name)) })
    }

    /**
     * Creates a function given its name and type, adds it to the Module and returns it.
     */
    fun addFunction(name: String, type: FunctionType): FunctionValue {
        val f = confined { temp ->
            LLVMAddFunction(M, temp.allocateFrom(name), type.T)
        }

        return FunctionValue(type, f)
    }

    /**
     * Gets the first FunctionValue defined in this Module.
     */
    fun getFirstFunction(): FunctionValue? {
        val value = LLVMGetFirstFunction(M)
        return if (value == MemorySegment.NULL) null else {
            val type = LLVMTypeOf(value)
            val functionType = FunctionType.from(type)
            FunctionValue(functionType, value)
        }
    }

    /**
     * Gets the last FunctionValue defined in this Module.
     */
    fun getLastFunction(): FunctionValue? {
        val value = LLVMGetLastFunction(M)
        return if (value == MemorySegment.NULL) null else {
            val type = LLVMTypeOf(value)
            val functionType = FunctionType.from(type)
            FunctionValue(functionType, value)
        }
    }

    /**
     * Gets a FunctionValue defined in this Module by its name.
     */
    fun getFunction(name: String): FunctionValue? {
        val value = confined { temp ->
            LLVMGetNamedFunction(M, temp.allocateFrom(name))
        }
        return if (value == MemorySegment.NULL) null else {
            val type = LLVMTypeOf(value)
            val functionType = FunctionType.from(type)
            FunctionValue(functionType, value)
        }
    }

    /**
     * Gets a named StructType from this Module's Context.
     */
    fun getStructType(name: String): StructType? {
        return context.getStructType(name)
    }

    /**
     * Assigns a TargetTriple to this Module.
     */
    fun setTriple(triple: String) {
        confined { temp -> LLVMSetTarget(M, temp.allocateFrom(triple)) }
    }

    /**
     * Gets the TargetTriple assigned to this Module.
     */
    fun getTriple(): String {
        val triple = LLVMGetTarget(M)
        return triple.getString(0)
    }

    /**
     * Creates an ExecutionEngine from this Module.
     */
    fun createExecutionEngine(): ExecutionEngine {
        Target.initialize()

        val e = confined { temp ->
            val out = Arena.global().allocate(ValueLayout.ADDRESS)
            val outErr = temp.allocate(ValueLayout.ADDRESS)

            val rst = LLVMCreateExecutionEngineForModule(
                out,
                M,
                outErr
            )
            ExecutionEngine(out.getAtIndex(ValueLayout.ADDRESS, 0))
        }

        return e
    }

    /**
     * Creates an interpreter ExecutionEngine from this Module.
     */
    fun createInterpreterExecutionEngine(): ExecutionEngine {
        Target.initialize()

        val e = confined { temp ->
            val out = Arena.global().allocate(ValueLayout.ADDRESS)
            val outErr = temp.allocate(ValueLayout.ADDRESS)

            val rst = LLVMCreateInterpreterForModule(
                out,
                M,
                outErr
            )

            ExecutionEngine(out.getAtIndex(ValueLayout.ADDRESS, 0))
        }

        return e
    }

    /**
     * Creates a JIT ExecutionEngine from this Module.
     */
    fun createJitExecutionEngine(optLvl: LLVMCodeGenOptLevel = LLVMCodeGenOptLevel.Default): ExecutionEngine {
        Target.initialize()

        val e = confined { temp ->
            val out = Arena.global().allocate(ValueLayout.ADDRESS)
            val outErr = temp.allocate(ValueLayout.ADDRESS)

            val rst = LLVMCreateJITCompilerForModule(
                out,
                M,
                optLvl.value.toUInt(),
                outErr
            )
            ExecutionEngine(out.getAtIndex(ValueLayout.ADDRESS, 0))
        }

        return e
    }

    /**
     * Writes a Module to a file.
     */
    fun writeBitcodeToPath(path: String): Boolean {
        return confined { temp ->
            LLVMWriteBitcodeToFile(M, temp.allocateFrom(path)) == 0
        }
    }

    /**
     * Writes this Module to a MemoryBuffer.
     */
    fun writeBitcodeToMemory(): MemorySegment {
        return LLVMWriteBitcodeToMemoryBuffer(M)
    }

    /**
     * Check whether the current Module is valid.
     */
    fun verify(): Boolean {
        val action = LLVMVerifierFailureAction.ReturnStatusAction
        return confined { temp ->
            val outErr = temp.allocate(ValueLayout.ADDRESS)
            val code = LLVMVerifyModule(M, action, outErr)
            code == 0
        }
    }

    /**
     * Prints the content of the Module to stderr.
     */
    fun printToStderr() {
        LLVMDumpModule(M)
    }

    /**
     * Prints the content of the Module to a String.
     */
    fun printToString(): String? {
        val s = LLVMPrintModuleToString(M)
        val str = s.getString(0)
        LLVMDisposeMessage(s)
        return str
    }

    /**
     * Prints the content of the Module to a file.
     */
    fun printToFile(path: String): Boolean {
        return confined { temp ->
            val outErr = temp.allocate(ValueLayout.ADDRESS)
            val code = LLVMPrintModuleToFile(M, temp.allocateFrom(path), outErr)
            code == 0
        }
    }

    /**
     * Sets the inline assembly for the Module.
     */
    fun setInlineAssembly(asm: String) {
        confined { temp ->
            LLVMSetModuleInlineAsm2(M, temp.allocateFrom(asm), asm.length.toULong())
        }
    }

    /**
     * Gets the first GlobalValue in a module.
     */
    fun getFirstGlobal(): GlobalValue? {
        val value = LLVMGetFirstGlobal(M)
        return if (value == MemorySegment.NULL) null else GlobalValue(value)
    }

    /**
     * Gets the last GlobalValue in a module.
     */
    fun getLastGlobal(): GlobalValue? {
        val value = LLVMGetLastGlobal(M)
        return if (value == MemorySegment.NULL) null else GlobalValue(value)
    }

    /**
     * Gets a named GlobalValue in a module.
     */
    fun getGlobal(name: String): GlobalValue? {
        val value = confined { temp ->
            LLVMGetNamedGlobal(M, temp.allocateFrom(name))
        }
        return if (value == MemorySegment.NULL) null else GlobalValue(value)
    }

    /**
     * Name of this module
     */
    var name: String
        get() {
            val name = confined { temp ->
                val lengthPtr = temp.allocate(ValueLayout.JAVA_LONG)
                val ptr = LLVMGetModuleIdentifier(M, lengthPtr)
                val len = lengthPtr.getAtIndex(ValueLayout.JAVA_LONG, 0)
                ptr.reinterpret(len).toArray(ValueLayout.JAVA_BYTE).decodeToString()
            }
            return name
        }
        set(value) {
            confined { temp ->
                LLVMSetModuleIdentifier(M, temp.allocateFrom(value), value.length.toULong())
            }
        }

    /**
     * Links one module into another. This will merge two Modules into one.
     */
    fun linkInModule(other: Module): Boolean {
        return LLVMLinkModules2(M, other.M) == 0
    }

    override fun toString(): String {
        return printToString() ?: "Module(null)"
    }
}
