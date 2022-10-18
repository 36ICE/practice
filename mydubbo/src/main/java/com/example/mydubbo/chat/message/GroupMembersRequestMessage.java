package com.example.mydubbo.chat.message;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class GroupMembersRequestMessage extends Message {

    private String gname;


    public GroupMembersRequestMessage(String gname) {
        this.gname=gname;
    }

    @Override
    public byte getMessageTyp() {
        return 0;
    }

    @Override
    public int getSequenceId() {
        return 0;
    }
}
