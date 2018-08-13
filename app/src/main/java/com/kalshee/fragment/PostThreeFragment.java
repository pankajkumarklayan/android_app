package com.kalshee.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kalshee.MainActivity;
import com.kalshee.R;
import com.kalshee.adapter.CityAdapter;
import com.kalshee.globalArray.GlobalArray;
import com.kalshee.pojo.BasePojo;
import com.kalshee.pojo.CityModal;
import com.kalshee.userData.UserData;
import com.kalshee.utill.NetworkClass;
import com.kalshee.utill.Utility;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by eWeb_A1 on 6/12/2018.
 */

public class PostThreeFragment extends Fragment implements View.OnClickListener {


    Button bt_postItem;
    ImageButton iv_back;
    EditText et_price;
    Spinner mCity_Spinner;
    String TAG = "PostThreeFragment";
    ProgressDialog mProgressDialog;
    ArrayList<CityModal> mSpinner_List = new ArrayList<>();
    CityAdapter mCity_Adapter;
    int mSpinnerPosition = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_post_three, container, false);

        XML(mView);
        if (Utility.checkInternet(getContext()))
        {

            getCategory();
        }
        return mView;
    }

    private void XML(View view)
    {

        iv_back = (ImageButton) view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);


        bt_postItem = (Button) view.findViewById(R.id.bt_postItem);
        bt_postItem.setOnClickListener(this);

        et_price = (EditText) view.findViewById(R.id.et_price);


        mCity_Spinner = (Spinner) view.findViewById(R.id.mCity_Spinner);
        mCity_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0)
                {

                    mSpinnerPosition = position;
                }
                else
                {
                    mSpinnerPosition = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.iv_back:
                Fragment mFragment = new PostFragmentTwo();
                getFragmentManager().beginTransaction().add(R.id.mPost_Container, mFragment).addToBackStack("").commit();
                break;

            case R.id.bt_postItem:
                if (validation()) {


                    addPost();
                }

                break;
        }
    }

    private void getCategory() {

        AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();
        mAsyncHttpClient.setTimeout(90000);
        mAsyncHttpClient.setResponseTimeout(90000);
        mAsyncHttpClient.setConnectTimeout(90000);
        RequestParams mParams = new RequestParams();

        String mUrl = NetworkClass.BASE_URL + NetworkClass.GET_ALL_CITY;
        Log.e(TAG, "============mURL==========" + mUrl);

        mAsyncHttpClient.get(mUrl, new JsonHttpResponseHandler() {


            @Override
            public void onStart() {
                super.onStart();
                mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setCancelable(false);
                mProgressDialog.setMessage(getString(R.string.Pleasewait));
                mProgressDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Log.e(TAG, "======response======" + response);
                if (statusCode == 200) {

                    mSpinner_List.clear();
                    Gson mGson = new Gson();
                    BasePojo<ArrayList<CityModal>> mPojo = mGson.fromJson(response.toString(), new TypeToken<BasePojo<ArrayList<CityModal>>>() {
                    }.getType());
                    mSpinner_List = mPojo.getData();

                    CityModal actor = new CityModal();
                    actor.setEng_title(getString(R.string.chooseyourlocation));

                    mSpinner_List.add(0, actor);
                    mCity_Adapter = new CityAdapter(getActivity(), mSpinner_List);
                    mCity_Spinner.setAdapter(mCity_Adapter);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mProgressDialog.dismiss();
            }
        });


    }

    private boolean validation() {

        if (et_price.getText().toString().trim().length() == 0) {
            showErrorr(getString(R.string.PLeaseenterPrice));
            return false;
        } else if (mSpinnerPosition == 0) {

            showErrorr(getString(R.string.PleaseselectCity));
            return false;
        } else {

            return true;
        }


    }

    private void showErrorr(String msg) {

        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

    }

    private void addPost() {

        AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();
        mAsyncHttpClient.setTimeout(90000);
        mAsyncHttpClient.setResponseTimeout(90000);
        mAsyncHttpClient.setConnectTimeout(90000);
        RequestParams mParams = new RequestParams();
        mParams.add("user_id", UserData.getUserID(getActivity()));
        mParams.add("category_id", UserData.getCategoryId(getActivity()));
        mParams.add("city_id", mSpinner_List.get(mSpinnerPosition).getCity_id());
        mParams.add("eng_title", UserData.getProductTitle(getActivity()));
        mParams.add("ar_title", "");
        mParams.add("item_type", UserData.getItemType(getActivity()));
        mParams.add("property_type", UserData.getPropertyType(getActivity()));
        mParams.add("description", UserData.getDESCRIPTION(getActivity()));
        mParams.add("price", et_price.getText().toString());
        mParams.add("address", mSpinner_List.get(mSpinnerPosition).getEng_title());
        mParams.add("lat", mSpinner_List.get(mSpinnerPosition).getLat());
        mParams.add("lng", mSpinner_List.get(mSpinnerPosition).getLat());




        if(GlobalArray.mImage_File.size()==1)
        {
            try
            {

                Log.e(TAG, "=========mImage_File=========="+GlobalArray.mImage_File.get(0));
                mParams.put(" uploadAdsImages[]", GlobalArray.mImage_File.get(0));

            } catch (Exception e) {
                Log.e(TAG, "==============ERRORR============" + e.getMessage());
            }

        }
        else
        {
            File[] mFiles = new File[GlobalArray.mImage_File.size()];
            for (int indx = 0; indx < GlobalArray.mImage_File.size(); indx++)
            {

                try {

                    mFiles[indx] = GlobalArray.mImage_File.get(indx);
                    Log.e(TAG, "========= GlobalArray.mImage_File.get(indx))====" + GlobalArray.mImage_File.get(indx));
                } catch (Exception e) {
                    Log.e(TAG, "==============ERRORR============" + e.getMessage());
                }


            }
            try
            {

                mParams.put(" uploadAdsImages[]", mFiles);

            } catch (Exception e) {
                Log.e(TAG, "==============ERRORR============" + e.getMessage());
            }



        }


        String mUrl = NetworkClass.BASE_URL + NetworkClass.ADD_POST;
        Log.e(TAG, "============mURL==========" + mUrl);
        Log.e(TAG, "=============PARMS===========" + mParams);

        mAsyncHttpClient.post(mUrl, mParams, new JsonHttpResponseHandler() {


            @Override
            public void onStart() {
                super.onStart();
                mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setCancelable(false);
                mProgressDialog.setMessage(getString(R.string.Pleasewait));
                mProgressDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Log.e(TAG, "======response======" + response);
                if (statusCode == 200) {

                    try {
                        String code = response.getString("code");
                        String message = response.getString("message");

                        if (code.equalsIgnoreCase("201")) {

                            MainActivity.mainActivity.viewPager.setCurrentItem(0);
                        }
                        else
                        {


                        }

                        showErrorr(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e(TAG, "========errorResponse======" + errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

                Log.e(TAG, "========errorResponse======" + responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Log.e(TAG, "========errorResponse======" + errorResponse);


            }

            @Override
            public void onFinish() {
                super.onFinish();
                mProgressDialog.dismiss();
            }
        });


    }


}
