package com.kalshee.pojo;

/**
 * Created by eWeb_A1 on 7/12/2018.
 */

public class UserChatPojo
{

    private String ownerId="";

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getmAddId() {
        return mAddId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getmDeviceID() {
        return mDeviceID;
    }

    public void setmDeviceID(String mDeviceID) {
        this.mDeviceID = mDeviceID;
    }

    public void setmAddId(String mAddId) {

        this.mAddId = mAddId;
    }

    String senderID="";
    String mAddId="";

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    String mDeviceID="";
    String productImage="";
    String dateTime="";

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    String price="";

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    String productId="";

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    String senderName="";

    public String getSender_Device_Id() {
        return Sender_Device_Id;
    }

    public void setSender_Device_Id(String sender_Device_Id) {
        Sender_Device_Id = sender_Device_Id;
    }

    String Sender_Device_Id="";

    public String getmAdd_UserId() {
        return mAdd_UserId;
    }

    public void setmAdd_UserId(String mAdd_UserId) {
        this.mAdd_UserId = mAdd_UserId;
    }

    String name="";
String mAdd_UserId="";

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    String msg="";
}
