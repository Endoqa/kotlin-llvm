import java.io.File

fun loadLLVM() {
    System.load(File("/nix/store/35mrxhx9vwf3ms734xydxfrh3d84d9fn-llvm-18.1.8-lib/lib/libLLVM.so").absolutePath)
    System.load(File("/nix/store/35mrxhx9vwf3ms734xydxfrh3d84d9fn-llvm-18.1.8-lib/lib/libRemarks.so").absolutePath)
    System.load(File("/nix/store/35mrxhx9vwf3ms734xydxfrh3d84d9fn-llvm-18.1.8-lib/lib/libLTO.so").absolutePath)
}