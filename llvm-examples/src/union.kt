import llvm.Builder
import llvm.Context
import llvm.Target
import llvm.types.ArrayType
import llvm.types.FunctionType
import llvm.types.PointerType
import llvm.values.PointerValue

/**
 * An example with jni.
 */
fun main() {
    loadLLVM()

    val context = Context()
    val module = context.createModule("union_example")

    val types = context.types

    val jvalue = types.struct("union.jvalue", listOf(types.i64))

    val funcType = FunctionType(
        returnType = types.void,
        params = listOf(types.i16, types.i32, types.i64)
    )

    val function = module.addFunction("call", funcType)

    val bb = context.appendBasicBlock(function, "bb")



    context.builder(bb) {
        val arr by alloca(ArrayType(jvalue, 3u))

        val store by with(types) { gep(i64, arr, i64(0u), i64(0u)) }

        store(function[0u], store) align 8u
    }











    println(module.printToString())

}
