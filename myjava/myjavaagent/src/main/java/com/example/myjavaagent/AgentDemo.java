package com.example.myjavaagent;

import java.lang.instrument.Instrumentation;

public class AgentDemo {

    /**
     * jvm 参数形式启动，运行此方法
     * @param agentArgs
     * @param inst
     */
    public static void premain(String agentArgs, Instrumentation inst){
        System.out.println("premain");
        customLogic(inst);
    }
    /**
     * 动态 attach 方式启动，运行此方法
     * @param agentArgs
     * @param inst
     */
    public static void agentmain(String agentArgs, Instrumentation inst){
        System.out.println("agentmain");
        customLogic(inst);
    }
    /**
     * 打印所有已加载的类名称
     * 修改字节码
     * @param inst
     */
    public static void customLogic(Instrumentation inst){
        MyTransformer myTransformer = new MyTransformer();
//        try{
            inst.addTransformer(myTransformer, true);
//            inst.retransformClasses(Class.forName("com.example.agentmain.MyMain",false,ClassLoader.getSystemClassLoader()));
//        } catch (UnmodifiableClassException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        finally {
//               inst.removeTransformer(myTransformer);
//        }

        Class[] classes = inst.getAllLoadedClasses();
        for(Class cls :classes){
            if("com.example.agentmain.Person".equals(cls.getName())){
                System.out.println("==>"+cls.getName());

            }
        }
    }
}
