package com.mycompany.myapp.service.util;

import com.mycompany.myapp.service.dto.Message;

import java.util.ArrayList;
import java.util.List;
import com.mycompany.myapp.service.dto.Message;

public class StaticWebsocketMessageStore {

    public static List<Message> messages = new ArrayList<>();

    public static List<String> subsciberNames = new ArrayList<>();

    public static List<Message> getMessages() {
        return messages;
    }

    public static void setMessages(List<Message> messages) {
        StaticWebsocketMessageStore.messages = messages;
    }

    public static List<String> getSubsciberNames() {
        return subsciberNames;
    }

    public static void setSubsciberNames(List<String> subsciberNames) {
        StaticWebsocketMessageStore.subsciberNames = subsciberNames;
    }
}
