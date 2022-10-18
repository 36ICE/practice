package com.example.mydubbo.chat.message;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class LoginRequestMessage extends Message {

    private String name;

    private String password;

    public LoginRequestMessage(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public byte getMessageTyp() {
        return LoginRequestMessage;
    }

    @Override
    public int getSequenceId() {
        return 0;
    }


}
