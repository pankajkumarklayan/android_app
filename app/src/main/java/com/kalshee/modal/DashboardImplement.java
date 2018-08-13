package com.kalshee.modal;

import android.content.Context;
import android.util.Log;

import com.kalshee.presenter.DashBoardImp;
import com.kalshee.utill.NetworkClass;
import com.kalshee.utill.Utility;
import com.kalshee.view.DashboardView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by eWeb_A1 on 7/5/2018.
 */

public class DashboardImplement implements DashBoardImp {

    Context mContext;
    DashboardView mDashboardView;
    String TAG="DashboardImplement";

    public DashboardImplement(Context context, DashboardView mView)
    {
        this.mContext= context;
        this.mDashboardView= mView;


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

    @Override
    public void setSoldInput(String productId) {


        if(Utility.checkInternet(mContext))
        {

            hitSoldApi(productId);

        }
    }

    @Override
    public void deleteInput(String addID) {

        if(Utility.checkInternet(mContext))
        {

            deleteHitApi(addID);
        }
    }

    private void deleteHitApi(String userID)
    {

        AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();
        mAsyncHttpClient.setTimeout(90000);
        mAsyncHttpClient.setConnectTimeout(90000);
        mAsyncHttpClient.setResponseTimeout(90000);
        RequestParams mParams = new RequestParams();
        mParams.add("ads_id",userID);

        String mURL = NetworkClass.BASE_URL + NetworkClass.DELETE_POST;
        Log.e(TAG, "============mURL======" + mURL);
        mAsyncHttpClient.post(mURL, mParams, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                mDashboardView.showProgressBar();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Log.e(TAG, "=========response=========" + response);

                if (statusCode == 200) {

                    mDashboardView.deleteSuccess(response.toString());

                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

                mDashboardView.showErrorr(responseString.toString());
                Log.e(TAG, "======ERRORR========="+responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                mDashboardView.showErrorr(errorResponse.toString());
                Log.e(TAG, "======ERRORR========="+errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                mDashboardView.showErrorr(errorResponse.toString());
                Log.e(TAG, "======ERRORR========="+errorResponse);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mDashboardView.hideProgressBar();
            }
        });


    }

    private void hitAPI(String userID)
    {

        AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();
        mAsyncHttpClient.setTimeout(90000);
        mAsyncHttpClient.setConnectTimeout(90000);
        mAsyncHttpClient.setResponseTimeout(90000);
        RequestParams mParams = new RequestParams();
        mParams.add("sellerId",userID);

        String mURL = NetworkClass.BASE_URL + NetworkClass.GET_SELLER_PRODUCT;
        Log.e(TAG, "============mURL======" + mURL);
        mAsyncHttpClient.post(mURL, mParams, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                mDashboardView.showProgressBar();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Log.e(TAG, "=========response=========" + response);

                if (statusCode == 200) {

                    mDashboardView.showSuccess(response.toString());

                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

                mDashboardView.showErrorr(responseString.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                mDashboardView.showErrorr(errorResponse.toString());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                mDashboardView.showErrorr(errorResponse.toString());
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mDashboardView.hideProgressBar();
            }
        });


    }
    private void hitSoldApi(String ads_id)
    {

        AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();
        mAsyncHttpClient.setTimeout(90000);
        mAsyncHttpClient.setConnectTimeout(90000);
        mAsyncHttpClient.setResponseTimeout(90000);
        RequestParams mParams = new RequestParams();
        mParams.add("ads_id",ads_id);

        String mURL = NetworkClass.BASE_URL + NetworkClass.MARK_AS_SOLD;
        Log.e(TAG, "============mURL======" + mURL);
        mAsyncHttpClient.post(mURL, mParams, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                mDashboardView.showProgressBar();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Log.e(TAG, "=========response=========" + response);

                if (statusCode == 200) {

                    mDashboardView.showSoldSuccess(response.toString());

                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

                mDashboardView.showErrorr(responseString.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                mDashboardView.showErrorr(errorResponse.toString());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                mDashboardView.showErrorr(errorResponse.toString());
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mDashboardView.hideProgressBar();
            }
        });


    }

}
