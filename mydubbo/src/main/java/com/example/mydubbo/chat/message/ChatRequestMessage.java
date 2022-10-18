package com.example.mydubbo.chat.message;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ChatRequestMessage extends Message {

    //消息发送者
    private String from;
    //消息接收者
    private String to;
    private String content;

    public ChatRequestMessage(String from ,String to,String content){
        this.from=from;
        this.to=to;
        this.content=content;
    }
    @Override
    public byte getMessageTyp() {
        return ChatRequestMessage;
    }

    @Override
    public int getSequenceId() {
        return 0;
    }
}
