package com.example.mydubbo.chat.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GroupQuitRequestMessage  extends Message{

    private String username;
    private String gname;
    public GroupQuitRequestMessage(String username, String gname) {
        this.username=username;
        this.gname=gname;
    }

    @Override
    public byte getMessageTyp() {
        return GroupQuitRequestMessage;
    }

    @Override
    public int getSequenceId() {
        return 0;
    }
}
