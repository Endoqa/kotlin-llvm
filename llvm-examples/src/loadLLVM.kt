import java.io.File

fun loadLLVM() {
    System.load(File("C:\\Users\\Jeff\\scoop\\apps\\llvm\\18.1.8\\bin\\LLVM-C.dll").absolutePath)
    System.load(File("C:\\Users\\Jeff\\scoop\\apps\\llvm\\18.1.8\\bin\\Remarks.dll").absolutePath)
    System.load(File("C:\\Users\\Jeff\\scoop\\apps\\llvm\\18.1.8\\bin\\LTO.dll").absolutePath)
}