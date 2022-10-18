package com.example.mydubbo.chat.message;

import lombok.ToString;

@ToString
public class PingMessage extends Message {
    @Override
    public byte getMessageTyp() {
        return PingMessage;
    }

    @Override
    public int getSequenceId() {
        return 0;
    }
}
