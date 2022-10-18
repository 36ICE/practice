package com.example.mydubbo.protocol;

import java.io.Serializable;

public interface Message extends Serializable {

    public static final byte LoginRequestMessage = 0;
    public static final byte LoginResponseMessage = 1;
    public static final byte ChatRequestMessage = 2;
    public static final byte ChatResponseMessage = 3;

    public abstract byte getMessageTyp();
    public abstract int getSequenceId();
}
