package com.example.myattach;

import com.sun.tools.attach.*;

import java.io.IOException;
import java.util.List;

public class AgentAttach {

    public static void main(String[] args) throws AgentLoadException, IOException, AgentInitializationException, AttachNotSupportedException, InterruptedException {
        if(args.length<1){
            return;
        }
//        VirtualMachine virtualMachine = null;
//        try {
//            virtualMachine = VirtualMachine.attach("18388");
//        } catch (AttachNotSupportedException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        while (true){
//            try {
//                virtualMachine.loadAgent("C:\\Users\\Administrator\\IdeaProjects\\practice\\myjava\\myjavaagent\\target\\myjavaagent-1.0-SNAPSHOT-jar-with-dependencies.jar");
//            } catch (AgentLoadException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (AgentInitializationException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    virtualMachine.detach();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        //获取当前系统中所有 运行中的 虚拟机
        System.out.println("running JVM start ");
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor vmd : list) {
            //如果虚拟机的名称为 xxx 则 该虚拟机为目标虚拟机，获取该虚拟机的 pid
            //然后加载 agent.jar 发送给该虚拟机
            System.out.println(vmd.displayName());
            if (vmd.displayName().contains("agentmain")) {
                VirtualMachine virtualMachine =null;
                    try{
                        virtualMachine=VirtualMachine.attach(vmd.id());
                        virtualMachine.loadAgent(args[0]);
                    }finally {
                        virtualMachine.detach();
                    }
                    Thread.sleep(2000);
            }
        }
    }
}
