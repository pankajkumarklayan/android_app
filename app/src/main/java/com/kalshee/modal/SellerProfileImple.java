package com.kalshee.modal;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.kalshee.pojo.ProductModal;
import com.kalshee.presenter.SellerProfileImp;
import com.kalshee.userData.UserData;
import com.kalshee.utill.NetworkClass;
import com.kalshee.utill.Utility;
import com.kalshee.view.SellerProfileView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by eWeb_A1 on 7/5/2018.
 */

public class SellerProfileImple implements SellerProfileImp
{

    SellerProfileView mProfileView;
    Context mContext;
    String TAG="SellerProfileImple";

    public SellerProfileImple(Context context, SellerProfileView mView)
    {

        this.mContext= context;
        this.mProfileView= mView;


    }

    @Override
    public void setInput(String userID, String sellerId) {

        if(userID.isEmpty() || sellerId.isEmpty())
        {


        }
        else
        {

            if(Utility.checkInternet(mContext))
            {


                hitAPI(userID, sellerId);
            }
        }

    }

    private void hitAPI(String userID, String sellerId)
    {

        AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();
        mAsyncHttpClient.setTimeout(90000);
        mAsyncHttpClient.setConnectTimeout(90000);
        mAsyncHttpClient.setResponseTimeout(90000);
        RequestParams mParams = new RequestParams();
        mParams.add("user_id", userID);
        mParams.add("sellerId",sellerId);

        String mURL = NetworkClass.BASE_URL + NetworkClass.GET_SELLER_PRODUCT;
        Log.e(TAG, "============mURL======" + mURL);
        mAsyncHttpClient.post(mURL, mParams, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
 mProfileView.showProgressBar();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Log.e(TAG, "=========response=========" + response);

                if (statusCode == 200) {

                    mProfileView.showSuccess(response.toString());

                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

                Log.e(TAG, "==============ERRORR======="+responseString);
                mProfileView.showErrorr(responseString.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);


                Log.e(TAG, "==============ERRORR======="+errorResponse);
                mProfileView.showErrorr(errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Log.e(TAG, "==============ERRORR======="+errorResponse);
                mProfileView.showErrorr(errorResponse.toString());


            }

            @Override
            public void onFinish() {
                super.onFinish();
                mProfileView.hideProgressBar();
            }
        });


    }

}
