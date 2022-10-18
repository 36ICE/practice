package com.example.mydubbo.chat.message;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ChatResponseMessage extends Message {


    private String from;
    private String content;

    private boolean success;
    private String reason;

    public ChatResponseMessage(String from ,String content){
        this.from=from;
        this.content=content;
    }
    public ChatResponseMessage(boolean success ,String reason){
        this.success=success;
        this.reason=reason;
    }
    @Override
    public byte getMessageTyp() {
        return ChatResponseMessage;
    }

    @Override
    public int getSequenceId() {
        return 0;
    }
}
