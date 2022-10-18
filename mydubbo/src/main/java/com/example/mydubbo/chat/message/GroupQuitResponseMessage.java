package com.example.mydubbo.chat.message;

public class GroupQuitResponseMessage extends Message{
    @Override
    public byte getMessageTyp() {
        return GroupQuitResponseMessage;
    }

    @Override
    public int getSequenceId() {
        return 0;
    }
}
