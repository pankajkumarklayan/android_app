package com.kalshee.modal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kalshee.LoginActivity;
import com.kalshee.MainActivity;
import com.kalshee.R;
import com.kalshee.presenter.LoginImp;
import com.kalshee.userData.UserData;
import com.kalshee.utill.NetworkClass;
import com.kalshee.utill.Utility;
import com.kalshee.view.LoginView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

/**
 * Created by eWeb_A1 on 7/6/2018.
 */

public class LoginImple implements LoginImp {

    Context mContext;
    LoginView mLoginView;
    String TAG = "LoginImple";


    public LoginImple(Context context, LoginView mView) {

        this.mContext = context;
        this.mLoginView = mView;

    }

    @Override
    public void setInput(String email, String password, String device_token) {


        if (email.isEmpty() || password.isEmpty()) {


        } else {


            if (Utility.checkInternet(mContext)) {

                hitAPI(email, password, device_token);
            }

        }

    }

    @Override
    public void facebookInput(String email, String name,  String device_token)
    {


        if(Utility.checkInternet(mContext))
        {


            facebookLogin(email, name, device_token);
        }


    }
    private void facebookLogin(String email, String name,String device_token) {

        AsyncHttpClient mHttpClient = new AsyncHttpClient();
        RequestParams mParams = new RequestParams();
        mParams.add("email", email);
        mParams.add("name", name);
        mParams.add("device_token", device_token);
        mParams.add("device_type", "1");  /// 1 android
        mParams.add("oauth_provider", "facebook");

        String mURL = NetworkClass.BASE_URL + NetworkClass.FACEBOOK_LOGIN;
        Log.e(TAG, "=========mURL=======" + mURL);

        mHttpClient.post(mURL, mParams, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                mLoginView.showProgressBar();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);


                Log.e(TAG, "==============RESPONSE===========" + response);

                if (statusCode == 200) {

                    mLoginView.showFacebookResponse(response.toString());
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);


                mLoginView.showErrorr(responseString);
                Log.e(TAG, "==============ERRORR==============" + responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Log.e(TAG, "==============ERRORR==============" + errorResponse);
                mLoginView.showErrorr(errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Log.e(TAG, "==============ERRORR==============" + errorResponse);
                mLoginView.showErrorr(errorResponse.toString());
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mLoginView.hideProgressBar();

            }
        });


    }

    private void hitAPI(String email, String password, String device_token) {

        AsyncHttpClient mHttpClient = new AsyncHttpClient();

        mHttpClient.setTimeout(90000);
        mHttpClient.setConnectTimeout(90000);
        mHttpClient.setResponseTimeout(90000);


        RequestParams mParams = new RequestParams();
        mParams.add("email", email);
        mParams.add("password", password);
        mParams.add("device_token", device_token);
        mParams.add("device_type", "1");  /// 1 android
        mParams.add("oauth_provider", "SIMPLE");

        String mURL = NetworkClass.BASE_URL + NetworkClass.LOGIN;
        Log.e(TAG, "=========mURL=======" + mURL);

        mHttpClient.post(mURL, mParams, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                mLoginView.showProgressBar();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);


                Log.e(TAG, "==============RESPONSE===========" + response);

                if (statusCode == 200) {

                    mLoginView.showSuccess(response.toString());
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);


                mLoginView.showErrorr(responseString);
                Log.e(TAG, "==============ERRORR==============" + responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                mLoginView.showErrorr(errorResponse.toString());
                Log.e(TAG, "==============ERRORR==============" + errorResponse);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                mLoginView.showErrorr(errorResponse.toString());
                Log.e(TAG, "==============ERRORR==============" + errorResponse);

            }

            @Override
            public void onFinish() {
                super.onFinish();
                mLoginView.hideProgressBar();

            }
        });


    }

}
