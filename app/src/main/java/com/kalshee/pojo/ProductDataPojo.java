package com.kalshee.pojo;

import java.io.Serializable;

/**
 * Created by eWeb_A1 on 7/13/2018.
 */

public class ProductDataPojo implements Serializable
{
    public String getADD_USER_ID() {
        return ADD_USER_ID;
    }

    public void setADD_USER_ID(String ADD_USER_ID) {
        this.ADD_USER_ID = ADD_USER_ID;
    }

    public String getADD_ID() {
        return ADD_ID;
    }

    public void setADD_ID(String ADD_ID) {
        this.ADD_ID = ADD_ID;
    }

    public String getP_NAME() {
        return P_NAME;
    }

    public void setP_NAME(String p_NAME) {
        P_NAME = p_NAME;
    }

    public String getDEVICE_TOKEN() {
        return DEVICE_TOKEN;
    }

    public void setDEVICE_TOKEN(String DEVICE_TOKEN) {
        this.DEVICE_TOKEN = DEVICE_TOKEN;
    }

    public String getSENDER_ID() {
        return SENDER_ID;
    }

    public void setSENDER_ID(String SENDER_ID) {
        this.SENDER_ID = SENDER_ID;
    }

    public String getPRICE() {
        return PRICE;
    }

    public void setPRICE(String PRICE) {
        this.PRICE = PRICE;
    }

    public String getPRODUCT_PICTURE() {
        return PRODUCT_PICTURE;
    }

    public void setPRODUCT_PICTURE(String PRODUCT_PICTURE) {
        this.PRODUCT_PICTURE = PRODUCT_PICTURE;
    }

    String ADD_USER_ID;
    String ADD_ID;
    String P_NAME;
    String DEVICE_TOKEN;
    String SENDER_ID;
    String PRICE;
    String PRODUCT_PICTURE;

}
