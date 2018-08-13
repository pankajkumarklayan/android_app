package com.kalshee.modal;

import android.content.Context;
import android.util.Log;

import com.kalshee.presenter.DashBoardImp;
import com.kalshee.presenter.SoldViewImp;
import com.kalshee.utill.NetworkClass;
import com.kalshee.utill.Utility;
import com.kalshee.view.DashboardView;
import com.kalshee.view.SoldView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by eWeb_A1 on 7/5/2018.
 */

public class SoldViewmplement implements SoldViewImp {

    Context mContext;
    SoldView mDashboardView;
    String TAG = "DashboardImplement";

    public SoldViewmplement(Context context, SoldView mView) {
        this.mContext = context;
        this.mDashboardView = mView;


    }

    @Override
    public void setInput(String userID) {
        if (userID.equalsIgnoreCase("")) {


        } else {


            if (Utility.checkInternet(mContext)) {

                hitAPI(userID);
            }
        }

    }

    @Override
    public void deleteInput(String addId) {
        if (Utility.checkInternet(mContext)) {
            deleteHitApi(addId);
        }
    }

    @Override
    public void rePostInput(String addId) {

        if (Utility.checkInternet(mContext)) {
            rePostHitApi(addId);
        }
    }


    private void hitAPI(String userID) {

        AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();
        mAsyncHttpClient.setTimeout(90000);
        mAsyncHttpClient.setConnectTimeout(90000);
        mAsyncHttpClient.setResponseTimeout(90000);
        RequestParams mParams = new RequestParams();
        mParams.add("sellerId", userID);

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
                Log.e(TAG, "==============ERRORR==========" + responseString);
                mDashboardView.showErrorr(responseString.toString());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e(TAG, "==============ERRORR==========" + errorResponse);
                mDashboardView.showErrorr(errorResponse.toString());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e(TAG, "==============ERRORR==========" + errorResponse);
                mDashboardView.showErrorr(errorResponse.toString());
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mDashboardView.hideProgressBar();
            }
        });


    }

    private void rePostHitApi(String userID) {

        AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();
        mAsyncHttpClient.setTimeout(90000);
        mAsyncHttpClient.setConnectTimeout(90000);
        mAsyncHttpClient.setResponseTimeout(90000);
        RequestParams mParams = new RequestParams();
        mParams.add("ads_id", userID);

        String mURL = NetworkClass.BASE_URL + NetworkClass.RE_POST_API;
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

                    mDashboardView.rePostSuccess(response.toString());

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

    private void deleteHitApi(String userID) {

        AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();
        mAsyncHttpClient.setTimeout(90000);
        mAsyncHttpClient.setConnectTimeout(90000);
        mAsyncHttpClient.setResponseTimeout(90000);
        RequestParams mParams = new RequestParams();
        mParams.add("ads_id", userID);

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
