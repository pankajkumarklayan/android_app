package com.kalshee.modal;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kalshee.R;
import com.kalshee.adapter.SpinnerAdapter;
import com.kalshee.globalArray.GlobalArray;
import com.kalshee.pojo.BasePojo;
import com.kalshee.pojo.ProductModal;
import com.kalshee.pojo.SpinnerModal;
import com.kalshee.presenter.HomeViewPres;
import com.kalshee.userData.UserData;
import com.kalshee.utill.NetworkClass;
import com.kalshee.utill.Utility;
import com.kalshee.view.HomeView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by eWeb_A1 on 7/2/2018.
 */

public class HomePresImpl implements HomeViewPres {

    HomeView mHomeView;
    Context mContext;
    String TAG = "HomePresImpl";


    public HomePresImpl(Context context, HomeView mView) {

        this.mContext = context;
        this.mHomeView = mView;


    }


    @Override
    public void setInput(String user_id) {


        if (Utility.checkInternet(mContext)) {

            hitAPI(user_id);

        }

    }

    @Override
    public void onLikeClick(String userId, String addId) {

        if (Utility.checkInternet(mContext)) {


            likeApi(userId, addId);
        }


    }

    @Override
    public void onFilterInput(String categoryId, String minimum, String maximum, String renttype, String userId)
    {


        filterAPI(categoryId, minimum, maximum,userId, renttype);

    }
    public void likeApi(String user_id, final String addId) {

        AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();
        mAsyncHttpClient.setTimeout(90000);
        mAsyncHttpClient.setConnectTimeout(90000);
        mAsyncHttpClient.setResponseTimeout(90000);

        RequestParams mParams = new RequestParams();
        mParams.add("user_id", user_id);
        mParams.add("ads_id", addId);


        String mURL = NetworkClass.BASE_URL + NetworkClass.ADD_TO_FAVOURITE;
        Log.e(TAG, "==============URL==========" + mURL);
        mAsyncHttpClient.post(mURL, mParams, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                mHomeView.showProgressBar();


            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Log.e(TAG, "==========response=======" + response);
                if (statusCode == 200) {
                    try {
                        String code = response.getString("code");
                        String message = response.getString("message");

                        mHomeView.showLikeSuccess(addId, response.toString());


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Log.e(TAG, "============ERRORR========"+errorResponse);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mHomeView.hideProgressBar();
            }
        });


    }

    private void hitAPI(String userId) {

        AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();
        mAsyncHttpClient.setTimeout(90000);
        mAsyncHttpClient.setConnectTimeout(90000);
        mAsyncHttpClient.setResponseTimeout(90000);
        RequestParams mParams = new RequestParams();
        mParams.add("user_id", userId);

        String mURL = NetworkClass.BASE_URL + NetworkClass.GET_ALL_PRODUCT;
        Log.e(TAG, "============mURL======" + mURL);
        mAsyncHttpClient.post(mURL, mParams, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();


            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Log.e(TAG, "=========response=========" + response);

                if (statusCode == 200) {

                    mHomeView.showSuccess(response.toString());

                    getCity();

                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                mHomeView.showErrorr(responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                mHomeView.showErrorr(errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                mHomeView.showErrorr(errorResponse.toString());
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });


    }

    private void getCity() {

        AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();
        mAsyncHttpClient.setTimeout(90000);
        mAsyncHttpClient.setConnectTimeout(90000);
        mAsyncHttpClient.setResponseTimeout(90000);
        String mURL = NetworkClass.BASE_URL + NetworkClass.GET_ALL_CITY;
        Log.e(TAG, "==============URL==========" + mURL);
        mAsyncHttpClient.get(mURL, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Log.e(TAG, "==========response=======" + response);
                if (statusCode == 200) {

                    mHomeView.showCityResponse(response.toString());

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });


    }

    private void filterAPI(String category_id , String minimumPrice, String maximumPrice, String user_id, String rent_type)
    {

        AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();
        mAsyncHttpClient.setTimeout(90000);
        mAsyncHttpClient.setConnectTimeout(90000);
        mAsyncHttpClient.setResponseTimeout(90000);
        RequestParams mParams= new RequestParams();





        mParams.add("rent_type", rent_type);
        mParams.add("category_id",category_id);
        mParams.add("minimumPrice",minimumPrice );
        mParams.add("maximumPrice", maximumPrice);
        mParams.add("user_id", user_id);

        String mURL = NetworkClass.BASE_URL + NetworkClass.FILTER_API;
        Log.e(TAG, "==============URL==========" + mURL);
        Log.e(TAG, "===========PARM============"+mParams);
        mAsyncHttpClient.post(mURL,mParams, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                mHomeView.showProgressBar();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Log.e(TAG, "==========response=======" + response);
                if (statusCode == 200) {

                    mHomeView.showFilterResponse(response.toString());

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                mHomeView.showErrorr(errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                mHomeView.showErrorr(responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                mHomeView.showErrorr(errorResponse.toString());
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mHomeView.hideProgressBar();
            }
        });


    }

}
