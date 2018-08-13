package com.kalshee.modal;

import android.content.Context;
import android.util.Log;

import com.kalshee.presenter.FavouriteViewImp;
import com.kalshee.utill.NetworkClass;
import com.kalshee.utill.Utility;
import com.kalshee.view.FavouriteView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by eWeb_A1 on 7/5/2018.
 */

public class FavouriteViewImple implements FavouriteViewImp
{


    Context mContext;
    FavouriteView mFavouriteView;
    String TAG="FavouriteViewImple";

    public FavouriteViewImple(Context context, FavouriteView mView)
    {
        this.mContext= context;
        this.mFavouriteView= mView;


    }

    @Override
    public void setInput(String userID) {
        if(userID.equalsIgnoreCase(""))
        {



        }
        else
        {


            if(Utility.checkInternet(mContext))
            {

                hitAPI(userID);
            }
        }

    }
    private void hitAPI(String userID)
    {

        AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();
        mAsyncHttpClient.setTimeout(90000);
        mAsyncHttpClient.setConnectTimeout(90000);
        mAsyncHttpClient.setResponseTimeout(90000);
        RequestParams mParams = new RequestParams();
        mParams.add("user_id",userID);

        String mURL = NetworkClass.BASE_URL + NetworkClass.GET_FAVORITE;
        Log.e(TAG, "============mURL======" + mURL);
        mAsyncHttpClient.post(mURL, mParams, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                mFavouriteView.showProgressBar();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Log.e(TAG, "=========response=========" + response);

                if (statusCode == 200) {

                    mFavouriteView.showSuccess(response.toString());

                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

                mFavouriteView.showErrorr(responseString.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                mFavouriteView.showErrorr(errorResponse.toString());
                Log.e(TAG, "=============ERRORR========"+errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                mFavouriteView.showErrorr(errorResponse.toString());
                Log.e(TAG, "=============ERRORR========"+errorResponse);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mFavouriteView.hideProgressBar();
            }
        });


    }

}
