package llvm

import lib.llvm.LLVMGetIntrinsicDeclaration
import lib.llvm.LLVMIntrinsicIsOverloaded
import lib.llvm.LLVMLookupIntrinsicID
import lib.llvm.LLVMTypeOf
import llvm.values.FunctionValue
import java.lang.foreign.ValueLayout

/**
 * A wrapper around LLVM intrinsic id
 *
 * To call it you would need to create a declaration inside a module using [getDeclaration].
 */
class Intrinsic(
    private val id: UInt
) {
    companion object {
        /**
         * Find llvm intrinsic id from name
         *
         * # Example
         * ```
         * val trapIntrinsic = Intrinsic.find("llvm.trap")
         *
         * val context = Context.create()
         * val module = context.createModule("trap")
         * val builder = context.createBuilder()
         * val voidType = context.voidType()
         * val fnType = voidType.fnType(emptyList(), false)
         * val fnValue = module.addFunction("trap", fnType)
         * val entry = context.appendBasicBlock(fnValue, "entry")
         *
         * val trapFunction = trapIntrinsic?.getDeclaration(module, emptyList())
         *
         * builder.positionAtEnd(entry)
         * builder.buildCall(trapFunction, emptyList(), "trap_call")
         * ```
         */
        fun find(name: String): Intrinsic? {
            val id = confined { temp ->
                LLVMLookupIntrinsicID(temp.allocateFrom(name), name.length.toULong())
            }

            if (id == 0u) {
                return null
            }

            return Intrinsic(id)
        }
    }


    /**
     * Check if specified intrinsic is overloaded
     *
     * Overloaded intrinsics need some argument types to be specified to declare them
     */
    val overloaded: Boolean get() = LLVMIntrinsicIsOverloaded(id) != 0

    /**
     * Create or insert the declaration of an intrinsic.
     *
     * For overloaded intrinsics, parameter types must be provided to uniquely identify an overload.
     *
     * # Example
     * ```
     * val trapIntrinsic = Intrinsic.find("llvm.trap")
     *
     * val context = Context.create()
     * val module = context.createModule("trap")
     * val builder = context.createBuilder()
     * val voidType = context.voidType()
     * val fnType = voidType.fnType(emptyList(), false)
     * val fnValue = module.addFunction("trap", fnType)
     * val entry = context.appendBasicBlock(fnValue, "entry")
     *
     * val trapFunction = trapIntrinsic?.getDeclaration(module, emptyList())
     *
     * builder.positionAtEnd(entry)
     * builder.buildCall(trapFunction, emptyList(), "trap_call")
     * ```
     */
    fun getDeclaration(module: Module, paramTypes: List<Type>): FunctionValue? {
        // param_types should be empty for non-overloaded intrinsics
        // for overloaded intrinsics they determine the overload used

        if (overloaded && paramTypes.isEmpty()) {
            // LLVM crashes otherwise
            return null
        }

        val res = confined { temp ->
            val paramTypesArray = temp.allocate(ValueLayout.ADDRESS, paramTypes.size.toLong())
            paramTypes.forEachIndexed { i, type ->
                paramTypesArray.setAtIndex(ValueLayout.ADDRESS, i.toLong(), type.T)
            }

            val functionRef = LLVMGetIntrinsicDeclaration(
                module.M,
                id,
                paramTypesArray,
                paramTypes.size.toULong()
            )

            FunctionValue(FunctionType.from(LLVMTypeOf(functionRef)), functionRef)
        }

        return res
    }
}