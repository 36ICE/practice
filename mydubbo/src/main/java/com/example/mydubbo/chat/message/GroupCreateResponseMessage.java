package com.example.mydubbo.chat.message;

public class GroupCreateResponseMessage extends Message{
    @Override
    public byte getMessageTyp() {
        return GroupCreateResponseMessage;
    }

    @Override
    public int getSequenceId() {
        return 0;
    }
}
