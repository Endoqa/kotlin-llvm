import lib.llvm.LLVMCodeGenOptLevel
import lib.llvm.LLVMDebugMetadataVersion
import llvm.*
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.Linker
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

val desc = FunctionDescriptor.of(
    ValueLayout.JAVA_LONG,
    ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG,
)

data class CodeGen(
    val context: Context,
    val module: Module,
    val builder: Builder,
    val ee: ExecutionEngine
)


fun CodeGen.compileSum(): MemorySegment {
    val types = context.types

    val fn = FunctionType(
        types.i64,
        listOf(types.i64, types.i64, types.i64),
    )

    val function = module.addFunction("sum", fn)
    val bb = context.appendBasicBlock(function, "entry")

    builder.positionAtEnd(bb)

    builder {
        val x = function[0u]
        val y = function[1u]
        val z = function[2u]

        check(x is IntValue)
        check(y is IntValue)
        check(z is IntValue)

        val sum1 by x + y
        val sum2 by sum1 + z

        returns(sum2)
    }

    return ee.getFunction("sum")
}

fun main() {

    loadLLVM()

    val context = Context()
    val module = context.createModule("test")
    val ee = module.createJITExecutionEngine(LLVMCodeGenOptLevel.Default)
    val codegen = CodeGen(context, module, context.createBuilder(), ee)


    val jitFunc = codegen.compileSum()

    val handle = Linker.nativeLinker().downcallHandle(jitFunc, desc)

    val result = handle.invokeExact(1L, 2L, 3L) as Long

    println(result)

}