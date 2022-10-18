package com.example.mydubbo.chat.message;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class LoginResponseMessage extends Message {

    private boolean success;

    private String reason;

    public LoginResponseMessage(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    @Override
    public byte getMessageTyp() {
        return LoginResponseMessage;
    }

    @Override
    public int getSequenceId() {
        return 0;
    }

}
