package com.example.mydubbo.chat.message;


import lombok.Data;
import lombok.ToString;

/**
 *
 * RPC 请求消息
 */
@Data
@ToString(callSuper = true)
public class RpcRequestMessage extends Message {

    private int sequenceId=0;
    /**
     * 调用接口全限定名，服务的根据它找到实现
     */
    private String interfaceName;

    /**
     * 调用接口中的方法
     */
    private String methodName;

    /**
     * 方法返回类型
     */
    private Class<?> returnType;

    /**
     * 方法参数类型数组
     */
    private Class[] parameterType;

    /**
     * 方法参数值数组
     */
   private Object[] parameterValue;

   public RpcRequestMessage(
           int sequenceId,
           String interfaceName,
           String methodName,
           Class<?> returnType,
           Class[] parameterType,
           Object[] parameterValue){

       this.sequenceId=sequenceId;
       this.interfaceName=interfaceName;
       this.methodName=methodName;
       this.returnType=returnType;
       this.parameterType=parameterType;
       this.parameterValue=parameterValue;
   }
    @Override
    public byte getMessageTyp() {
        return RpcRequestMessage;
    }

    @Override
    public int getSequenceId() {
        return 0;
    }
}
