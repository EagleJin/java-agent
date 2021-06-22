package com.sloth.learn.premain;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * @Description:
 * @author: sloth
 * @Date: 2021/06/22/9:52
 */
public class PreMainTraceAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("agentArgs: "+agentArgs);
        inst.addTransformer(new DefineTransformer(), true);
    }
    static class DefineTransformer implements ClassFileTransformer {
        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            System.out.println("premain load class:" + className);
            return classfileBuffer;
        }
    }
}
