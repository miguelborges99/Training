package software.chronicle;

import jdk.internal.org.objectweb.asm.util.ASMifier;


public class StartHere {

    // 1. first dump the code of hello world
    public static void main(String[] args) throws Exception {
        ASMifier.main(new String[]{"software.chronicle.HelloWorld"});
    }

    // 2. run this class, it creates a visitor patten that when run will create the byte code of HelloWorld.
    // 3. we will modify the generated code, by changing the class name, and run run it see : software.chronicle.ModifedDumpToCreateANewClass
    // 4. The step above, creates a java class file, now run that java class file on the command line. $ java software.chronicle.HelloWorld2
}
