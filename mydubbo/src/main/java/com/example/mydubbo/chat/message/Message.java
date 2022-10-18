package com.example.mydubbo.chat.message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public abstract class Message implements Serializable {

    public static final byte LoginRequestMessage = 0;
    public static final byte LoginResponseMessage = 1;
    public static final byte ChatRequestMessage = 2;
    public static final byte ChatResponseMessage = 3;
    public static final byte GroupChatRequestMessage = 4;
    public static final byte GroupChatResponseMessage = 5;
    public static final byte GroupCreateRequestMessage = 6;
    public static final byte GroupCreateResponseMessage = 7;
    public static final byte GroupMembersRequestMessage = 8;
    public static final byte GroupMembersResponseMessage = 9;
    public static final byte GroupJoinRequestMessage = 10;
    public static final byte GroupJoinResponseMessage = 11;
    public static final byte GroupQuitRequestMessage = 12;
    public static final byte GroupQuitResponseMessage = 13;
    public static final byte PingMessage = 14;


    //RPC框架消息
    public static final byte RpcRequestMessage=15;

    public static final byte RpcResponseMessage=16;

    public abstract byte getMessageTyp();

    public abstract int getSequenceId();




    public static Map<Byte,Class> messageClasses = new HashMap<>();


    static {
        messageClasses.put(LoginRequestMessage, LoginResponseMessage.class);
        messageClasses.put(LoginResponseMessage, LoginResponseMessage.class);
        messageClasses.put(ChatRequestMessage, ChatRequestMessage.class);
        messageClasses.put(ChatResponseMessage, ChatResponseMessage.class);
        messageClasses.put(GroupChatRequestMessage, GroupChatRequestMessage.class);
        messageClasses.put(GroupChatResponseMessage, GroupChatResponseMessage.class);
        messageClasses.put(GroupCreateRequestMessage, GroupCreateRequestMessage.class);
        messageClasses.put(GroupCreateResponseMessage, GroupCreateResponseMessage.class);
        messageClasses.put(GroupMembersRequestMessage, GroupMembersRequestMessage.class);
        messageClasses.put(GroupJoinRequestMessage, GroupJoinRequestMessage.class);
        messageClasses.put(GroupJoinResponseMessage, GroupJoinResponseMessage.class);
        messageClasses.put(GroupQuitRequestMessage, GroupQuitRequestMessage.class);
        messageClasses.put(GroupQuitResponseMessage, GroupQuitResponseMessage.class);
        messageClasses.put(PingMessage, PingMessage.class);


        messageClasses.put(RpcRequestMessage, RpcRequestMessage.class);
        messageClasses.put(RpcResponseMessage, RpcResponseMessage.class);
    }

    public static Class<?> getMessageClass(byte messagetType){
        return messageClasses.get(messagetType);
    }
}
