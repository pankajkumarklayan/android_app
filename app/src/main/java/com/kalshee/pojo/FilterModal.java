package com.kalshee.pojo;

/**
 * Created by eWeb_A1 on 6/14/2018.
 */

public class FilterModal
{

    private String ar_title;

    public String getAr_title() {
        return ar_title;
    }

    public void setAr_title(String ar_title) {
        this.ar_title = ar_title;
    }

    public String getEng_title() {
        return eng_title;
    }

    public void setEng_title(String eng_title) {
        this.eng_title = eng_title;
    }

    private String eng_title;

    private String categoryId;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getTextColor()
    {
        return TextColor;
    }

    public void setTextColor(String textColor) {
        TextColor = textColor;
    }

    public int getBlueimage() {

        return blueimage;

    }

    public void setBlueimage(int blueimage) {
        this.blueimage = blueimage;
    }

    public int getRedImage() {
        return redImage;
    }

    public void setRedImage(int redImage) {
        this.redImage = redImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    int blueimage;
int redImage;
String name;
boolean isCheck;
String TextColor;
}
