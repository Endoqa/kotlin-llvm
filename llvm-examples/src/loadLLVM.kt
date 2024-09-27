import java.io.File


private enum class Os(
    val libPrefix: String,
    val libExtension: String,
    val binPath: String
) {
    Linux("lib", ".so", "lib"),
    Win("", ".dll", "bin");


    fun libName(name: String) = "${libPrefix}${name}${libExtension}"
}

fun loadLLVM() {
    val os = getOS()

    val llvmPath = System.getenv("LLVM_DEV_PATH") + "/" + os.binPath


    System.load(File("${llvmPath}/${os.libName("LLVM")}").absolutePath)
    System.load(File("${llvmPath}/${os.libName("Remarks")}").absolutePath)
    System.load(File("${llvmPath}/${os.libName("LTO")}").absolutePath)
}

private fun getOS(): Os {

    val osName = System.getProperty("os.name")

    return when {
        osName.startsWith("Win") -> Os.Win
        osName.contains("Linux") -> Os.Linux
        else -> error("Unknown OS")
    }
}