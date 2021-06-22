package com.sloth.learn.agentmain;

import java.lang.instrument.Instrumentation;

/**
 * 捕获线程池中线程异常
 * @Description:
 * @author: sloth
 * @Date: 2021/06/21/18:01
 */
public class InstrumentAgent {
    public static void agentmain(String agentArgs, Instrumentation inst) {
        Class<?>[] classes = inst.getAllLoadedClasses();
        boolean isLoad = false;
        for (Class<?> clazz : classes) {
            if (clazz.getName().equals("java.util.concurrent.FutureTask")) {
                System.out.println("--add transformer to loaded FutureTask.class--");
                try {
                    inst.addTransformer(new FutureTaskTransformer(),true);
                    inst.retransformClasses(clazz);
                } catch (Throwable e) {
                    System.out.println("--add transformer to loaded FutureTask Fail---:"+e);
                    e.printStackTrace();
                }
                isLoad  = true;
                break;
            }
        }
        if (!isLoad) {
            System.out.println("--add transformer to unloaded FutureTask.class--");
            inst.addTransformer(new FutureTaskTransformer());
        }
    }
}
