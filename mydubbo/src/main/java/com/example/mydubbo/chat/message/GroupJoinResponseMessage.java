package com.example.mydubbo.chat.message;

public class GroupJoinResponseMessage extends Message{
    @Override
    public byte getMessageTyp() {
        return GroupJoinResponseMessage;
    }

    @Override
    public int getSequenceId() {
        return 0;
    }
}
