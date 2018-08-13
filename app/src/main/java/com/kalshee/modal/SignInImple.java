package com.kalshee.modal;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.kalshee.R;
import com.kalshee.presenter.SignInImp;
import com.kalshee.userData.UserData;
import com.kalshee.utill.NetworkClass;
import com.kalshee.utill.Utility;
import com.kalshee.view.SigninView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by eWeb_A1 on 7/7/2018.
 */

public class SignInImple implements SignInImp {

    Context mContext;
    SigninView mSigninView;
    String TAG= "SignInImple";

    public SignInImple(Context context, SigninView mView)
    {

        this.mContext= context;
        this.mSigninView= mView;

    }
    @Override
    public void setInput(String email, String name, String password, String deviceToken) {


        if(Utility.checkInternet(mContext))
        {

            hitAPI(email, name, password, deviceToken);
        }
    }

    @Override
    public void facebookInput(String email) {

    }
    private void hitAPI(String email, String name, String password, String deviceToken)
    {

        AsyncHttpClient mHttpClient= new AsyncHttpClient();
        mHttpClient.setTimeout(90000);
        mHttpClient.setResponseTimeout(90000);
        mHttpClient.setConnectTimeout(90000);
        RequestParams mParams= new RequestParams();
        mParams.add("email", email);
        mParams.add("name", name);
        mParams.add("password",password );
        mParams.add("device_token",deviceToken);
        mParams.add("device_type", "1");  /// 1 android
        mParams.add("oauth_provider","SIMPLE");

        String mURL= NetworkClass.BASE_URL+NetworkClass.SIGNUP;
        Log.e(TAG, "=========mURL======="+mURL);

        mHttpClient.post(mURL, mParams, new JsonHttpResponseHandler()
        {

            @Override
            public void onStart() {
                super.onStart();

               mSigninView.showProgressBar();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                super.onSuccess(statusCode, headers, response);


                Log.e(TAG, "==============RESPONSE==========="+response);

                if(statusCode==200) {

                    mSigninView.showSuccess(response.toString());
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);



                Log.e(TAG, "==============ERRORR=============="+responseString);
                mSigninView.showErrorr(responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Log.e(TAG, "==============ERRORR=============="+errorResponse);
                mSigninView.showErrorr(errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Log.e(TAG, "==============ERRORR=============="+errorResponse);
                mSigninView.showErrorr(errorResponse.toString());
            }

            @Override
            public void onFinish() {
                super.onFinish();

                mSigninView.hideProgressBar();
            }
        });


    }

}
