package com.example.mydubbo.chat;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 基于此内存实现的业务服务
 */
@Slf4j
public class MemoryService implements Service {

    public static Map<String, String> userMap = new HashMap<>();
    public static Map<String, Channel> username2Channel = new ConcurrentHashMap<>();
    public static Map<Channel, String> channel2Username = new ConcurrentHashMap<>();
    public static Map<String, Set<String>> groupMap = new ConcurrentHashMap<>();
    public static Map<String, List<Channel>> groupMembersChannelMap = new ConcurrentHashMap<>();

    static {
        userMap.put("zcd", "123456");
        userMap.put("zcd2", "123456");
        userMap.put("zcd3", "123456");
        userMap.put("zcd4", "123456");
        userMap.put("zcd5", "123456");
        userMap.put("zcd6", "123456");
    }

    @Override
    public boolean login(String username, String password) {
        return userMap.get(username) != null && userMap.get(username).equals(password);
    }

    @Override
    public boolean bind(String username, Channel channel) {
        username2Channel.put(username, channel);
        channel2Username.put(channel, username);
        return false;
    }

    @Override
    public boolean unbind(Channel channel) {

        String username = channel2Username.get(channel);
        try {
            username2Channel.remove(username);

        } catch (Exception e) {
            log.debug("{}已经解除绑定", channel);
        }
        try {
            channel2Username.remove(channel);

        } catch (Exception e) {
            log.debug("{}已经解除绑定", channel);
        }
        return false;
    }

    public boolean createGroup(String gname, Set<String> gset) {
        if (gname != null && gset != null) {
            groupMap.put(gname, gset);
            List<Channel> channels = new ArrayList<>();
            for (String username : gset) {
                Channel channel = username2Channel.get(username);
                channels.add(channel);
            }
            groupMembersChannelMap.put(gname, channels);
            return true;
        }
        return false;
    }

    public Set<String> removeGroup(String gname) {

        return groupMap.remove(gname);
    }

    /**
     * 根据群名获取群的channel
     *
     * @param gname
     * @return
     */
    public List<Channel> getMembersChannel(String gname) {
        return groupMembersChannelMap.get(gname);
    }

    public static void main(String[] args) {

        System.out.println(MemoryService.userMap);

    }
}

class Group {

}