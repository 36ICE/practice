package com.example.mydubbo.chat.message;


import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class GroupJoinRequestMessage extends Message {

    private String username;
    private String gname;

    public GroupJoinRequestMessage(String username, String gname) {

        this.username=username;
        this.gname=gname;
    }

    @Override
    public byte getMessageTyp() {
        return GroupJoinRequestMessage;
    }

    @Override
    public int getSequenceId() {
        return 0;
    }
}
