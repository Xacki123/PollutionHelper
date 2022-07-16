package com.example.main_map;

public class Message {
    public String userName;
    public String textMessage;
    public String messageTime;

    public Message() {}
    public Message(String userName, String textMessage, String messageTime)
    {
        this.userName = userName;
        this.textMessage = textMessage;
        this.messageTime = messageTime;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
}
