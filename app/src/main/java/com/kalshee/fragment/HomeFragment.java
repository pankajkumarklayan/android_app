package com.kalshee.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kalshee.FilterActivity;
import com.kalshee.R;
import com.kalshee.adapter.HomeAdapter;
import com.kalshee.adapter.SpinnerAdapter;
import com.kalshee.globalArray.GlobalArray;
import com.kalshee.modal.HomePresImpl;
import com.kalshee.pojo.BasePojo;
import com.kalshee.pojo.ProductModal;
import com.kalshee.pojo.SpinnerModal;
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
import java.util.Collections;
import java.util.Comparator;

import cz.msebera.android.httpclient.Header;

/**
 * Created by eWeb_A1 on 6/9/2018.
 */

public class HomeFragment extends Fragment implements View.OnClickListener, HomeView {
    RecyclerView mRecycle;
    HomeAdapter mAdapter;
    ArrayList<ProductModal> mList = new ArrayList<>();
    ArrayList<SpinnerModal> mspinner_list = new ArrayList<>();
    ArrayList<ProductModal> mSearch_list = new ArrayList<>();
    ImageButton Ib_Filter;
    private final int FILTER = 100;
    String TAG = "HomeFragment";
    Spinner mSpinner;
    SpinnerAdapter spinnerAdapter;
    ImageButton mShow_spinner;
    EditText et_Search;
    ProgressDialog mProgressDialog;
    View mView;
    BroadcastReceiver mBroadcastReceiver;
    String mAddID = "", mFavorite = "";
    String mCityId = "", mSearch = "",sortBy="",minimum="",maximum="",  rentType="";
    HomePresImpl mHomePres;
    SwipeRefreshLayout swipeContainer;
    TextView tv_noProduct;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        mHomePres = new HomePresImpl(getActivity(), HomeFragment.this);
        mHomePres.setInput(UserData.getUserID(getActivity()));
        XML(mView);


        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                mAddID = intent.getStringExtra("ADD_ID");
                mFavorite = intent.getStringExtra("FAVORITE");


                Log.e(TAG, "========mBroadcastReceiver=========");

            }
        };

        getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter("ADDFAVORITE"));

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();


        if (mAddID.equalsIgnoreCase("") || mFavorite.equalsIgnoreCase("")) {

        } else {
            update(mAddID, mFavorite);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


        getActivity().unregisterReceiver(mBroadcastReceiver);
    }

    public void update(String addId, String favorite) {

        for (int indx = 0; indx < mList.size(); indx++) {

            String mUserAddID = mList.get(indx).getUser_ads_id();
            if (mUserAddID.equalsIgnoreCase(addId)) {

                mList.get(indx).setIs_fav(favorite);
                break;
            }

        }

        mAdapter.notifyDataSetChanged();


    }

    private void XML(View view) {


        tv_noProduct=(TextView)view.findViewById(R.id.tv_noProduct);

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setRefreshing(true);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                et_Search.setText("");
                mSpinner.setSelection(0);

                mList.clear();
                mAdapter.notifyDataSetChanged();
                mHomePres.setInput(UserData.getUserID(getActivity()));
            }
        });


        mRecycle = (RecyclerView) view.findViewById(R.id.mRecycle);
        mRecycle.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));
        mAdapter = new HomeAdapter(getActivity(), mList, HomeFragment.this);
        mRecycle.setAdapter(mAdapter);


        Ib_Filter = (ImageButton) view.findViewById(R.id.Ib_Filter);
        Ib_Filter.setOnClickListener(this);
        mSpinner = (Spinner) view.findViewById(R.id.mSpinner);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position == 0) {
                    mAdapter.setmList(mList);

                    mCityId = "";
                } else {


                    mSearch_list.clear();
                    mCityId = mspinner_list.get(position).getCity_id();
                    Log.e(TAG, "=========mCityId======" + mCityId);


                    if (mSearch.equalsIgnoreCase("")) {
                        for (int indx = 0; indx < mList.size(); indx++) {

                            if (mList.get(indx).getCity_id().contains(mCityId)) {

                                mSearch_list.add(mList.get(indx));

                            }

                        }

                    } else {

                        for (int indx = 0; indx < mList.size(); indx++) {

                            if (mList.get(indx).getCity_id().contains(mCityId) && mList.get(indx).getEng_title().toLowerCase().contains(mSearch.toLowerCase())) {

                                mSearch_list.add(mList.get(indx));

                            }

                        }
                    }


                    mAdapter.setmList(mSearch_list);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mShow_spinner = (ImageButton) view.findViewById(R.id.mShow_spinner);
        mShow_spinner.setOnClickListener(this);


        et_Search = (EditText) view.findViewById(R.id.et_Search);
        et_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                mSearch = s.toString();
                if (mSearch.length() == 0) {

                    mAdapter.setmList(mList);
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_Search.getWindowToken(), 0);

                    mSearch = "";
                } else {

                    mSearch_list.clear();

                    for (int indx = 0; indx < mList.size(); indx++) {


                        if (mCityId.equalsIgnoreCase("")) {
                            if (mList.get(indx).getEng_title().toLowerCase().contains(mSearch.toLowerCase())) {


                                mSearch_list.add(mList.get(indx));


                            }

                        } else {

                            if (mList.get(indx).getEng_title().toLowerCase().contains(mSearch.toLowerCase()) && mList.get(indx).getCity_id().equalsIgnoreCase(mCityId)) {


                                mSearch_list.add(mList.get(indx));


                            }

                        }


                    }
                    if(mSearch_list.size()==0)
                    {

                        tv_noProduct.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        tv_noProduct.setVisibility(View.GONE);
                    }

                    mAdapter.setmList(mSearch_list);


                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.Ib_Filter:

                Intent mIntent = new Intent(getActivity(), FilterActivity.class);
                mIntent.putExtra("MINIMUM",minimum);
                mIntent.putExtra("MAXIMUM", maximum);
                mIntent.putExtra("RENTYPE", rentType);
                mIntent.putExtra("SORTBY", sortBy);
                startActivityForResult(mIntent, FILTER);

                break;

            case R.id.mShow_spinner:
                mSpinner.performClick();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILTER && data != null)
        {


            mList.clear();
            mAdapter.setmList(mList);
            Log.e(TAG, "==========onActivityResult======");
             minimum = data.getStringExtra("MINIMUM");
             maximum = data.getStringExtra("MAXIMUM");
             rentType = data.getStringExtra("RENTYPE");
            sortBy = data.getStringExtra("SORTBY");



            if (minimum.equalsIgnoreCase("") && maximum.equalsIgnoreCase("") && sortBy.equalsIgnoreCase("")
                    && rentType.equalsIgnoreCase("") && GlobalArray.mSelect_Filter.size() == 0)
            {

                swipeContainer.setRefreshing(true);
                mList.clear();
                mAdapter.notifyDataSetChanged();
                mHomePres.setInput(UserData.getUserID(getActivity()));
            }
            else
            {
                StringBuilder mCategoryId = new StringBuilder();

                for (int indx = 0; indx < GlobalArray.mSelect_Filter.size(); indx++) {
                    if (indx == 0) {
                        mCategoryId.append(GlobalArray.mSelect_Filter.get(indx));
                    } else {
                        mCategoryId.append("," + GlobalArray.mSelect_Filter.get(indx));
                    }


                }

                Log.e(TAG, "=====mCategoryId========" + mCategoryId.toString());
                mHomePres.onFilterInput(mCategoryId.toString(), minimum, maximum, rentType, UserData.getUserID(getActivity()));
            }

        }
        else
        {


        }
    }


    private void hideSoftKeyboard(View view) {

        InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }


    public void likeApi(final ProductModal actor, String addId) {

        mHomePres.onLikeClick(UserData.getUserID(getActivity()), addId);

    }


    @Override
    public void showSuccess(String response) {

        try {
            swipeContainer.setRefreshing(false);
            JSONObject mJsonObject = new JSONObject(response);

            String code = mJsonObject.getString("code");
            if (code.equalsIgnoreCase("201"))
            {


                mList.clear();
                JSONArray data = mJsonObject.getJSONArray("data");
                for (int indx = 0; indx < data.length(); indx++)
                {

                    ProductModal actor = new ProductModal();
                    JSONObject dataObject = data.getJSONObject(indx);
                    String user_ads_id = dataObject.getString("user_ads_id");
                    String user_id = dataObject.getString("user_id");
                    String category_id = dataObject.getString("category_id");
                    String city_id = dataObject.getString("city_id");
                    String eng_title = dataObject.getString("eng_title");
                    String ar_title = dataObject.getString("ar_title");
                    String description = dataObject.getString("description");
                    String price = dataObject.getString("price");
                    String is_fav = dataObject.getString("is_fav");
                    String active = dataObject.getString("active");
                    String ad_status = dataObject.getString("ad_status");

                    if (ad_status.equalsIgnoreCase("1"))
                    {


                    }
                    else
                    {
                        if (active.equalsIgnoreCase("0"))
                        {

                            Log.e(TAG, "=======================PENDING PRODUCT==============");

                        }
                        else
                        {
                            String image = "";
                            JSONArray adsImage = dataObject.getJSONArray("adsImage");
                            for (int child = 0; child < adsImage.length(); child++) {
                                JSONObject mImageObject = adsImage.getJSONObject(child);
                                if (child == 0) {

                                    image = mImageObject.getString("image");
                                }

                            }


                            if (image.equalsIgnoreCase(""))
                            {

                                Log.e(TAG, "=================IMAGE NOT FOUND=======");

                            }
                            else
                            {

                                actor.setUser_ads_id(user_ads_id);
                                actor.setUser_id(user_id);
                                actor.setCategory_id(category_id);
                                actor.setCity_id(city_id);
                                actor.setEng_title(eng_title);
                                actor.setAr_title(ar_title);
                                actor.setDescription(description);

                                if (price.contains(".")) {

                                    String[] mpriceArr = price.split("\\.");
                                    String mPrice = mpriceArr[0];

                                    actor.setPrice(mPrice);
                                } else {
                                    actor.setPrice(price);
                                }

                                actor.setImage(image);
                                actor.setIs_fav(is_fav);
                                mList.add(actor);


                            }


                        }

                    }

                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        tv_noProduct.setVisibility(View.GONE);
                        mList.size();
                        mAdapter.notifyDataSetChanged();

                    }
                });///////////////

            }
            else
            {
                tv_noProduct.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "===========ERRORR========" + e.getMessage());
        }
    }


    @Override
    public void showErrorr(String error) {

        Log.e(TAG, "==============ERRORR=========" + error);

    }

    @Override
    public void showSpinner(String Response) {

    }

    @Override
    public void showLikeSuccess(String mAddID, String Response) {
        try {

            JSONObject mJsonObject = new JSONObject(Response);
            String message = mJsonObject.getString("message");

            if (message.contains("Favorite success.")) {

                for (int indx = 0; indx < mList.size(); indx++) {

                    String addID = mList.get(indx).getUser_ads_id();
                    if (mAddID.equalsIgnoreCase(addID)) {

                        mList.get(indx).setIs_fav("1");

                    }


                }


            } else {
                for (int indx = 0; indx < mList.size(); indx++) {

                    String addID = mList.get(indx).getUser_ads_id();
                    if (mAddID.equalsIgnoreCase(addID)) {

                        mList.get(indx).setIs_fav("0");

                    }


                }
            }

        } catch (Exception e) {
            Log.e(TAG, "============ERRORR=========" + e.getMessage());
        }

    }

    @Override
    public void showCityResponse(String Response) {
        mspinner_list.clear();
        try {

            JSONObject mJsonObject = new JSONObject(Response);

            String code = mJsonObject.getString("code");
            if (code.equalsIgnoreCase("201")) {


                Gson mGson = new Gson();
                BasePojo<ArrayList<SpinnerModal>> mPojo = mGson.fromJson(mJsonObject.toString(), new TypeToken<BasePojo<ArrayList<SpinnerModal>>>() {
                }.getType());
                mspinner_list = mPojo.getData();

                SpinnerModal actor = new SpinnerModal();
                actor.setEng_title("City");
                mspinner_list.add(0, actor);
                Log.e(TAG, "=========mspinner_list===" + mspinner_list);


                spinnerAdapter = new SpinnerAdapter(getActivity(), mspinner_list);
                mSpinner.setAdapter(spinnerAdapter);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void showProgressBar() {

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(getString(R.string.Pleasewait));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void hideProgressBar() {
        mProgressDialog.dismiss();

    }

    @Override
    public void showFilterResponse(String response) {

        Log.e(TAG, "===========Response=======" + response);
        try {
            swipeContainer.setRefreshing(false);
            JSONObject mJsonObject = new JSONObject(response);

            String code = mJsonObject.getString("code");
            if (code.equalsIgnoreCase("201"))
            {

                String image = "";
                mList.clear();
                JSONArray data = mJsonObject.getJSONArray("data");
                for (int indx = 0; indx < data.length(); indx++) {

                    ProductModal actor = new ProductModal();
                    JSONObject dataObject = data.getJSONObject(indx);
                    String user_ads_id = dataObject.getString("user_ads_id");
                    String user_id = dataObject.getString("user_id");
                    String category_id = dataObject.getString("category_id");
                    String city_id = dataObject.getString("city_id");
                    String eng_title = dataObject.getString("eng_title");
                    String ar_title = dataObject.getString("ar_title");
                    String description = dataObject.getString("description");
                    String price = dataObject.getString("price");
                    String is_fav = dataObject.getString("is_fav");
                    String active = dataObject.getString("active");
                    String ad_status = dataObject.getString("ad_status");
                    String updated_date=dataObject.getString("update_date");
                    String created_date= dataObject.getString("created_date");


                    if(updated_date.equalsIgnoreCase("null") || updated_date.equalsIgnoreCase(""))
                    {


                        updated_date=created_date;
                    }

                    if (ad_status.equalsIgnoreCase("1"))
                    {


                    }
                    else
                    {
                        if (active.equalsIgnoreCase("0")) {

                            Log.e(TAG, "=======================PENDING PRODUCT==============");

                        } else {

                            JSONArray adsImage = dataObject.getJSONArray("adsImage");
                            for (int child = 0; child < adsImage.length(); child++) {
                                JSONObject mImageObject = adsImage.getJSONObject(child);
                                if (child == 0) {

                                    image = mImageObject.getString("image");
                                }

                            }


                            if (image.equalsIgnoreCase("")) {

                                Log.e(TAG, "=================IMAGE NOT FOUND=======");

                            } else {

                                actor.setUser_ads_id(user_ads_id);
                                actor.setUser_id(user_id);
                                actor.setCategory_id(category_id);
                                actor.setCity_id(city_id);
                                actor.setEng_title(eng_title);
                                actor.setAr_title(ar_title);
                                actor.setDescription(description);

                                if (price.contains(".")) {

                                    String[] mpriceArr = price.split("\\.");
                                    String mPrice = mpriceArr[0];

                                    actor.setPrice(mPrice);
                                } else {
                                    actor.setPrice(price);
                                }

                                actor.setImage(image);
                                actor.setIs_fav(is_fav);
                                actor.setUpdated_date(updated_date);
                                mList.add(actor);


                            }


                        }

                    }

                }


                if(sortBy.equalsIgnoreCase("LowtoHigh"))
                {
                    Collections.sort(mList, new Comparator<ProductModal>() {
                        @Override
                        public int compare(ProductModal m1, ProductModal m2)
                        {

                            return Integer.valueOf(m1.getPrice()).compareTo(Integer.parseInt(m2.getPrice()));


                        }
                    });


                }
                else if(sortBy.equalsIgnoreCase("HightoLow"))
                {
                    Collections.sort(mList, new Comparator<ProductModal>() {
                        @Override
                        public int compare(ProductModal m1, ProductModal m2)
                        {

                            return Integer.valueOf(m2.getPrice()).compareTo(Integer.parseInt(m1.getPrice()));


                        }
                    });

                }
                else if(sortBy.equalsIgnoreCase("Latest"))
                {
                    Collections.sort(mList, new Comparator<ProductModal>() {
                        @Override
                        public int compare(ProductModal o1, ProductModal o2) {

                            return o1.getUpdated_date().compareTo(o2.getUpdated_date());

                        }
                    });


                }
                else if(sortBy.equalsIgnoreCase("Oldest"))
                {
                    Collections.sort(mList, new Comparator<ProductModal>() {
                        @Override
                        public int compare(ProductModal o1, ProductModal o2) {

                            return o1.getUpdated_date().compareTo(o2.getUpdated_date());
                        }
                    });

                }



                tv_noProduct.setVisibility(View.GONE);
                mList.size();
                mAdapter.setmList(mList);


            }
            else
            {
                mList.clear();
                mAdapter.setmList(mList);
                tv_noProduct.setVisibility(View.VISIBLE);


            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "===========ERRORR========" + e.getMessage());
        }


    }


}




