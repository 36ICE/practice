package com.example.mydubbo.chat.message;


import lombok.Data;
import lombok.ToString;

@ToString
@Data
abstract class ResponseMessage extends Message {


    String status;
    String reason;

    public ResponseMessage(String status, String reason) {
        this.status = status;
        this.reason = reason;
    }

}
