package com.example.mydubbo.chat.message;

import lombok.Data;
import lombok.ToString;

import java.util.Set;

@ToString
@Data
public class GroupCreateRequestMessage extends Message {

    private String gname;
    private Set<String> set;

    public GroupCreateRequestMessage(String gname, Set<String> set) {
        this.gname = gname;
        this.set = set;
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
