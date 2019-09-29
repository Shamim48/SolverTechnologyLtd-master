package com.Solver.Solver.ModelClass;

public class GroupMessage {

    private String name;
    private String sender;
    private String sub;
    private String message;
    private String time;
    private String date;
    private String tagClientName;
    private String sparePart;
    private String imageUri;
    private String msgType;
    private String msgKey;
    private String groupName;

    // reply variable

    private String name_reply;
    private String client_reply;
    private String job_reply;
    private String msg_reply;
    private String spearPart_reply;
    private String image_reply;


    public GroupMessage() {

    }

    public GroupMessage(String name,String msg, String sender, String time, String date, String imageUri, String msgType,String groupName) {
        this.name = name;
        this.sender = sender;
        this.time = time;
        this.date = date;
        this.imageUri = imageUri;
        this.msgType = msgType;
        this.groupName=groupName;
        this.message=msg;
    }

    public GroupMessage(String name, String sender, String sub, String message, String time, String date, String tagClientName, String sparePart,String msgKey,String groupName,String msgType) {
        this.name = name;
        this.sender = sender;
        this.sub = sub;
        this.message = message;
        this.time = time;
        this.date = date;
        this.tagClientName = tagClientName;
        this.sparePart = sparePart;
        this.msgKey=msgKey;
        this.groupName=groupName;
        this.msgType=msgType;
    }

    public GroupMessage(String name, String sender, String sub, String message, String time, String date, String tagClientName, String sparePart, String msgType, String msgKey, String groupName, String name_reply, String client_reply, String job_reply, String msg_reply, String spearPart_reply) {
        this.name = name;
        this.sender = sender;
        this.sub = sub;
        this.message = message;
        this.time = time;
        this.date = date;
        this.tagClientName = tagClientName;
        this.sparePart = sparePart;
        this.msgType = msgType;
        this.msgKey = msgKey;
        this.groupName = groupName;
        this.name_reply = name_reply;
        this.client_reply = client_reply;
        this.job_reply = job_reply;
        this.msg_reply = msg_reply;
        this.spearPart_reply = spearPart_reply;
    }

    public GroupMessage(String name, String sender, String sub, String message, String time, String date, String tagClientName, String sparePart,String msgType, String msgKey, String groupName, String name_reply,String image_reply) {
        this.name = name;
        this.sender = sender;
        this.sub = sub;
        this.message = message;
        this.time = time;
        this.date = date;
        this.tagClientName = tagClientName;
        this.sparePart = sparePart;
        this.msgType = msgType;
        this.msgKey = msgKey;
        this.groupName = groupName;
        this.name_reply = name_reply;
        this.image_reply=image_reply;
    }

    public String getImage_reply() {
        return image_reply;
    }

    public void setImage_reply(String image_reply) {
        this.image_reply = image_reply;
    }

    public String getTagClientName() {
        return tagClientName;
    }

    public void setTagClientName(String tagClientName) {
        this.tagClientName = tagClientName;
    }

    public String getSparePart() {
        return sparePart;
    }

    public void setSparePart(String sparePart) {
        this.sparePart = sparePart;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String imageType) {
        this.msgType = imageType;
    }

    public String getMsgKey() {
        return msgKey;
    }

    public void setMsgKey(String msgKey) {
        this.msgKey = msgKey;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getName_reply() {
        return name_reply;
    }

    public void setName_reply(String name_reply) {
        this.name_reply = name_reply;
    }

    public String getClient_reply() {
        return client_reply;
    }

    public void setClient_reply(String client_reply) {
        this.client_reply = client_reply;
    }

    public String getJob_reply() {
        return job_reply;
    }

    public void setJob_reply(String job_reply) {
        this.job_reply = job_reply;
    }

    public String getMsg_reply() {
        return msg_reply;
    }

    public void setMsg_reply(String msg_reply) {
        this.msg_reply = msg_reply;
    }

    public String getSpearPart_reply() {
        return spearPart_reply;
    }

    public void setSpearPart_reply(String spearPart_reply) {
        this.spearPart_reply = spearPart_reply;
    }
}
