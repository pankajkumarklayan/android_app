package com.kalshee.pojo;

/**
 * Created by eWeb_A1 on 7/10/2018.
 */

public class ChatPojo
{
    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    private String ownerId;
    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    private String productImage;

    public String getSenderProfile() {
        return senderProfile;
    }

    public void setSenderProfile(String senderProfile) {
        this.senderProfile = senderProfile;
    }

    public String getReceiverProfile() {
        return receiverProfile;
    }

    public void setReceiverProfile(String receiverProfile) {
        this.receiverProfile = receiverProfile;
    }

    private String senderProfile;
    private String receiverProfile;
    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    String productPrice;
    String addUserId;
    public String getPname() {
        return Pname;
    }

    public String getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(String addUserId) {
        this.addUserId = addUserId;
    }

    public void setPname(String pname) {
        Pname = pname;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    private String ReceiverMsg="";
    private String SenderMsg="";
    private String dateTime;
    private String Pname;
    private String senderName;

    public String getReceiverMsg() {
        return ReceiverMsg;
    }

    public void setReceiverMsg(String receiverMsg) {
        ReceiverMsg = receiverMsg;
    }

    public String getSenderMsg() {
        return SenderMsg;
    }

    public void setSenderMsg(String senderMsg) {
        SenderMsg = senderMsg;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    private String deviceID;
private String Add_id;

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceivingId() {
        return receivingId;
    }

    public void setReceivingId(String receivingId) {
        this.receivingId = receivingId;
    }

    private String message;
private String senderId;
private String receivingId;



    public String getAdd_id() {
        return Add_id;
    }

    public void setAdd_id(String add_id) {
        Add_id = add_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
