package com.example.mydubbo.protocol;

import lombok.Data;

@Data
public class LoginResponseMessage implements Message {

    private boolean success;

    private String reason;

    public LoginResponseMessage(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
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
