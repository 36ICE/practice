package com.example.mydubbo.chat.message;

public class GroupChatResponseMessage extends Message{

    @Override
    public byte getMessageTyp() {
        return GroupChatResponseMessage;
    }

    @Override
    public int getSequenceId() {
        return 0;
    }
}
