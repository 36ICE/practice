package com.example.mydubbo.chat.message;


import lombok.Data;
import lombok.ToString;


/**
 * RPC响应消息
 *
 */
@Data
@ToString(callSuper = true)
public class RpcResponseMessage extends Message  {

    private int sequenceId;

    /**
     * 返回值
     */
    private Object returnValue;

    /**
     * 异常值
     */
    private Exception exceptionValue;

    @Override
    public byte getMessageTyp() {
        return RpcResponseMessage;
    }

    @Override
    public int getSequenceId() {
        return 0;
    }
}
