package software.chronicle;

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class StartHere {

    // 1. first dump the code of hello world, this creates some new source code to std out, the code
    // it creates, is a visitor patten of byte code, that when run will create the byte code of HelloWorld.
    public static void main(String[] args) throws Exception {
        ASMifier.main(new String[]{"software.chronicle.HelloWorld"});
    }

    // 2. now, we will modify the generated code, by changing the class name, and run run it see : software.chronicle.RunTheASMifierGeneratedCode

}
