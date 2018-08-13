package com.kalshee.modal;

import android.content.Context;
import android.util.Log;

import com.kalshee.presenter.FilterImp;
import com.kalshee.utill.NetworkClass;
import com.kalshee.utill.Utility;
import com.kalshee.view.FilterView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by eWeb_A1 on 7/6/2018.
 */

public class FilterImplement implements FilterImp
{
    Context mContext;
    FilterView mFilterView;
    String TAG="FilterImplement";

    public FilterImplement(Context context, FilterView mView)
    {

        this.mContext= context;
        this.mFilterView= mView;

    }
    @Override
    public void setInput(String userId)
    {

        if(Utility.checkInternet(mContext))
        {

            getCategory();
        }

    }

    private void getCategory()
    {

        AsyncHttpClient mAsyncHttpClient= new AsyncHttpClient();
        mAsyncHttpClient.setTimeout(90000);
        mAsyncHttpClient.setConnectTimeout(90000);
        mAsyncHttpClient.setResponseTimeout(90000);
        String mURL= NetworkClass.BASE_URL+NetworkClass.GET_ALL_CATEGORY;
        Log.e(TAG, "===========mURL========="+mURL);

        mAsyncHttpClient.get(mURL, new JsonHttpResponseHandler()
        {


            @Override
            public void onStart() {
                super.onStart();
                mFilterView.showProgressBar();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if(statusCode==200)
                {
                    mFilterView.showSuccess(response.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                mFilterView.showErrorr(responseString);

                Log.e(TAG, "=================ERRORR=========="+responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
               mFilterView.showErrorr(errorResponse.toString());

                Log.e(TAG, "=================ERRORR=========="+errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
               mFilterView.showErrorr(errorResponse.toString());
               Log.e(TAG, "=================ERRORR=========="+errorResponse);
            }

            @Override
            public void onFinish() {
                super.onFinish();
             mFilterView.hideProgressBar();
            }
        });
    }
}
