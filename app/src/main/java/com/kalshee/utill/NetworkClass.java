package com.kalshee.utill;

/**
 * Created by eWeb_A1 on 6/18/2018.
 */

public class NetworkClass {

    public static String BASE_URL = "http://a1professionals.net/Klshee/";
    public static String PROFILE_BASE_URL = "http://a1professionals.net/Klshee/uploads/userimage/";


    public static String SIGNUP = "webservices/Registration/register";
    public static String LOGIN = "webservices/Registration/login";
    public static String FORGOT_PASSWORD = "webservices/Registration/forgotPassword";
    public static String RESET_PASSWORD = "webservices/Registration/resetPassword";
    public static String GET_ALL_PRODUCT = "webservices/Product/fetchAds";
    public static String GET_ALL_CITY = "webservices/StaticData/fetchCity";
    public static String UPDATE_PROFILE = "webservices/Registration/updateProfile";
    public static String GET_ALL_CATEGORY = "webservices/StaticData/fetchProductCategory";
    public static String ADD_POST = "/webservices/Product/addAds";
    public static String GET_SINGLE_PRODUCT_DETAILS = "/webservices/Product/fetchSIngleAds";
    public static String ADD_TO_FAVOURITE = "webservices/Product/AdFavorite";
    public static String GET_SELLER_PRODUCT = "webservices/Product/fetchSellerProduct";
    public static String MARK_AS_SOLD = "webservices/Product/doSOld";
    public static String RE_POST_API = "webservices/Product/RepostAds";
    public static String DELETE_POST="webservices/Product/deleteAds";
   public static String FILTER_API="webservices/Product/filterAds";
   public static String GET_FAVORITE="webservices/Product/fetchMyFavProduct";
   public static String FACEBOOK_LOGIN="webservices/Registration/facebookLogin";
}
