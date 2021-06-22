package com.sloth.learn.agentmain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @Description:
 * @author: sloth
 * @Date: 2021/06/21/17:52
 */
public class FutureTaskTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if ("java/util/concurrent/FutureTask".equals(className)) {
            System.out.println("----Instrument FutureTask----");
            try {
                ClassPool classPool = ClassPool.getDefault();
                CtClass ctClass = classPool.get("java.util.concurrent.FutureTask");

                CtMethod ctMethod = ctClass.getDeclaredMethod("setException");
                ctMethod.insertBefore("{ $1.printStackTrace();}");
                return ctClass.toBytecode();
            } catch (Throwable e) {
                System.out.println("Instrument FutureTask Fail:"+e);
                e.printStackTrace();
            }
        }
        return null;
    }
}
