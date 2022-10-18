package com.example.mydubbo.chat;

import io.netty.channel.Channel;

public interface Service {
    /**
     * 登录接口
     * @param username
     * @param password
     * @return
     */
    public boolean login(String username,String password);

    /**
     * 用户绑定channnel
     * @param username
     * @param channel
     * @return
     */
    public boolean bind(String username, Channel channel);

    /**
     *
     * 用户解绑channel
     * @param channel
     * @return
     */
    public boolean unbind(Channel channel);



}
