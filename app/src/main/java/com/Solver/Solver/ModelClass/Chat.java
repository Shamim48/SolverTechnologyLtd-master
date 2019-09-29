package com.Solver.Solver.ModelClass;

public class Chat {

    private String sender;
    private String senderName;
    private String receiver;
    private String message;
    private String msg_Type;
    private String imageUri;
    private String reply;
    private String replyText;
    private String msgId;
    private String time;
    private String date;
    private String receiverName;
    private boolean isseen;
    public Chat(String msgId,String senderName,String sender, String receiver, String message,String msg_Type,String time,String date, boolean isseen) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.isseen = isseen;
        this.msgId=msgId;
        this.msg_Type=msg_Type;
        this.senderName=senderName;
        this.time=time;
        this.date=date;

    }
 public Chat(String msgId,String senderName,String receiverName,String sender, String receiver, String message,String replyText,String msg_Type,String time,String date, boolean isseen) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.isseen = isseen;
        this.msgId=msgId;
        this.msg_Type=msg_Type;
        this.senderName=senderName;
        this.time=time;
        this.date=date;
        this.receiverName=receiverName;
        this.replyText=replyText;

    }

public Chat(String msgId,String senderName,String receiverName,String sender, String receiver, String message,String replyText,String imageUri,String msg_Type,String time,String date, boolean isseen) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.isseen = isseen;
        this.msgId=msgId;
        this.msg_Type=msg_Type;
        this.senderName=senderName;
        this.time=time;
        this.date=date;
        this.receiverName=receiverName;
        this.replyText=replyText;
        this.imageUri=imageUri;

    }


    public Chat(String msgId,String senderName,String sender, String receiver, String msg_Type, String imageUri,String message,String time,String date, boolean isseen) {
        this.sender = sender;
        this.receiver = receiver;
        this.msg_Type = msg_Type;
        this.imageUri = imageUri;
        this.isseen = isseen;
        this.message=message;
        this.msgId=msgId;
        this.senderName=senderName;
        this.time=time;
        this.date=date;
    }



    public Chat() {

    }


    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsg_Type() {
        return msg_Type;
    }

    public void setMsg_Type(String msg_Type) {
        this.msg_Type = msg_Type;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getReplyText() {
        return replyText;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public boolean isIsseen() {
        return isseen;
    }

    public void setIsseen(boolean isseen) {
        this.isseen = isseen;
    }
}
