package com.kalshee.userData;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by eWeb_A1 on 6/18/2018.
 */

public class UserData
{


    private static String DATABASE_NAME="User_Details";

    public static SharedPreferences mSharedPreferences;
    public static String USER_id="user_id";
    public static String NAME="name";
    public static String EMAIL="email";
    public static String CHECK_STATUS="status";
    public static String JOIN_DATE="join_date";
    private static String PROFILE_PIC="profile_pic";
    private static String PRODUCT_TITLE="ptitle";
    private static String CATEGORY_ID="categoryId";
    private static String DESCRIPTION="description";
    private static  String ITEM_TYPE="item_type";
    private static String PROPERTY_TYPE="type";
    private static String SELLER_NAME="Sellername";
    private static String SELLER_IMAGE="SellerImage";
    private static String SELLER_ID="sellerId";
    private static String DEVICE_id="device";

    public static void setUSER_id(Context mContext, String userId)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putString(USER_id, userId);
        mEditor.apply();


    }
    public static void setEMAIL(Context mContext, String userId)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putString(EMAIL, userId);
        mEditor.apply();


    }
    public static void setNAME(Context mContext, String userId)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putString(NAME, userId);
        mEditor.apply();


    }
    public static void setCheckStatus(Context mContext, String userId)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putString(CHECK_STATUS, userId);
        mEditor.apply();


    }
    public static void setJoinDate(Context mContext, String userId)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putString(JOIN_DATE, userId);
        mEditor.apply();


    }
    public static void setProfilePic(Context mContext, String userId)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putString(PROFILE_PIC, userId);
        mEditor.apply();


    }
    public static void setProductTitle(Context mContext, String userId)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putString(PRODUCT_TITLE, userId);
        mEditor.apply();


    }
    public static void setCategoryId(Context mContext, String userId)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putString(CATEGORY_ID, userId);
        mEditor.apply();


    }
    public static void setDESCRIPTION(Context mContext, String userId)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putString(DESCRIPTION, userId);
        mEditor.apply();


    }
    public static void setItemType(Context mContext, String userId)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putString(ITEM_TYPE, userId);
        mEditor.apply();


    }
    public static void setPropertyType(Context mContext, String userId)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putString(PROPERTY_TYPE, userId);
        mEditor.apply();


    }
    public static void setSellerName(Context mContext, String userId)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putString(SELLER_NAME, userId);
        mEditor.apply();


    }
    public static void setSellerImage(Context mContext, String userId)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putString(SELLER_IMAGE, userId);
        mEditor.apply();


    }
    public static void setSellerId(Context mContext, String userId)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putString(SELLER_ID, userId);
        mEditor.apply();


    }
    public static void setDEVICE_id(Context mContext, String userId)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putString(DEVICE_id, userId);
        mEditor.apply();


    }
    public static String  getDEVICE_id(Context mContext)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        String UserID= mSharedPreferences.getString(DEVICE_id, "");

        return UserID;
    }
    public static String  getSellerId(Context mContext)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        String UserID= mSharedPreferences.getString(SELLER_ID, "");

        return UserID;
    }
    public static String  getSellerImage(Context mContext)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        String UserID= mSharedPreferences.getString(SELLER_IMAGE, "");

        return UserID;
    }
    public static String  getSellerName(Context mContext)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        String UserID= mSharedPreferences.getString(SELLER_NAME, "");

        return UserID;
    }


    public static String  getPropertyType(Context mContext)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        String UserID= mSharedPreferences.getString(PROPERTY_TYPE, "");

        return UserID;
    }
    public static String  getItemType(Context mContext)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        String UserID= mSharedPreferences.getString(ITEM_TYPE, "");

        return UserID;
    }
    public static String  getDESCRIPTION(Context mContext)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        String UserID= mSharedPreferences.getString(DESCRIPTION, "");

        return UserID;
    }
    public static String  getCategoryId(Context mContext)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        String UserID= mSharedPreferences.getString(CATEGORY_ID, "");

        return UserID;
    }
    public static String  getProductTitle(Context mContext)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        String UserID= mSharedPreferences.getString(PRODUCT_TITLE, "");

        return UserID;
    }
    public static String  getProfilePic(Context mContext)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        String UserID= mSharedPreferences.getString(PROFILE_PIC, "");

        return UserID;
    }
    public static String  getJoinDate(Context mContext)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        String UserID= mSharedPreferences.getString(JOIN_DATE, "");

        return UserID;
    }
    public static String  getCheckStatus(Context mContext)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        String UserID= mSharedPreferences.getString(CHECK_STATUS, "");

        return UserID;
    }
    public static String  getNAME(Context mContext)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        String UserID= mSharedPreferences.getString(NAME, "");

        return UserID;
    }
    public static String  getEMAIL(Context mContext)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        String UserID= mSharedPreferences.getString(EMAIL, "");

        return UserID;
    }
    public static String  getUserID(Context mContext)
    {
        mSharedPreferences=mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        String UserID= mSharedPreferences.getString(USER_id, "");

        return UserID;
    }


    public static void Logout(Context mContext)
    {

        mSharedPreferences= mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor= mSharedPreferences.edit();
        mEditor.clear();
        mEditor.apply();

    }

}
