package com.example.mydubbo.protocol;

import lombok.Data;

@Data
public class LoginRequestMessage implements Message {

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
