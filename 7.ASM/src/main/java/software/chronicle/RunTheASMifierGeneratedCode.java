package software.chronicle;

import jdk.internal.org.objectweb.asm.*;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class RunTheASMifierGeneratedCode implements Opcodes {

    public static final Class<RunTheASMifierGeneratedCode> CLAZZ = RunTheASMifierGeneratedCode.class;

    public static byte[] byteCode(final String name) throws Exception {

        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;
        AnnotationVisitor av0;

        cw.visit(V1_5, ACC_PUBLIC + ACC_SUPER, "software/chronicle/" + name, null, "java/lang/Object", null);

        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("hello world");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
            mv.visitCode();
            mv.visitTypeInsn(NEW, "software/chronicle/" + name);
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "software/chronicle/" + name, "<init>", "()V", false);
            mv.visitInsn(POP);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        String name = "HelloWorld2";
        byte[] byteCode = byteCode(name);

        // use the unsafe class to load in the class bytes
        final Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        final Unsafe unsafe = (Unsafe) f.get(null);
        final Class aClass = unsafe.defineClass("software.chronicle." + name, byteCode, 0, byteCode.length, CLAZZ.getClassLoader(), CLAZZ.getProtectionDomain());
        aClass.newInstance();
    }

}
