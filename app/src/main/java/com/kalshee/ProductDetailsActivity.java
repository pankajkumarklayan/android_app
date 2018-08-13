package com.kalshee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kalshee.adapter.SmallPhotoAdapter;
import com.kalshee.pojo.ProductDataPojo;
import com.kalshee.userData.UserData;
import com.kalshee.utill.NetworkClass;
import com.kalshee.utill.TimeShow;
import com.kalshee.utill.TouchableWrapper;
import com.kalshee.utill.TouchableWrapperListener;
import com.kalshee.utill.Utility;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ProductDetailsActivity extends AppCompatActivity implements TouchableWrapperListener, View.OnClickListener, OnMapReadyCallback {


    private ImageView iv_largeImage;
    private ImageButton IB_back, Ib_share, IB_favourite;
    private RecyclerView mRecyclerView;
    String TAG = "ProductDetailsActivity";
    String addUser_Id = "", addID = "";
    ProgressDialog mProgressDialog;
    SmallPhotoAdapter mPhotoAdapter;
    List<String> mPhoto_List = new ArrayList<>();
    ProgressBar mProgressbar;
    String mUSER_id = "";
    TextView tv_title, tv_city, tv_postedDay, tv_categoryName, tv_city2, tv_price, tv_Desc, tv_Name,tv_Title;
    GoogleMap mGoogleMap;
    TouchableWrapper touchableMap;
    SupportMapFragment mapFragment;
    Toolbar mToolbar;
    ImageButton back;
    ImageView iv_profileImage;
    RelativeLayout mProfile_Relative;
    String ADD_USER_ID="";
   LinearLayout mChat_layout;

   TextView tv_chat;
    Intent mIntent=null;
    String mAddId="", mAdd_UserId="",Sender_Device_Id="",mprice="";
    String image="";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        mUSER_id = UserData.getUserID(getApplicationContext());
        Log.e(TAG, "=========mUSER_id=========" + mUSER_id);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        XML();
        initToolbar();


        if (Utility.checkInternet(ProductDetailsActivity.this))
        {

            addID = getIntent().getStringExtra("ADD_ID");
            ADD_USER_ID=getIntent().getStringExtra("ADD_USER_ID");

            hitAPI();
        }



    }

    private void initToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Ib_share = (ImageButton)toolbar.findViewById(R.id.Ib_share);
        back = (ImageButton) toolbar.findViewById(R.id.back);
        tv_Name = (TextView) findViewById(R.id.tv_Name);
        IB_favourite = (ImageButton)toolbar.findViewById(R.id.IB_favourite);
        tv_Title=(TextView)toolbar.findViewById(R.id.tv_Title);


        AppBarLayout appBarLayout = (AppBarLayout)findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {


                if (verticalOffset == 0)
                {

                    // Fully expanded

                    IB_favourite.setVisibility(View.VISIBLE);
                    Ib_share.setVisibility(View.VISIBLE);

                    tv_Title.setVisibility(View.GONE);
                    back.setBackgroundColor(Color.parseColor("#20000000"));

                    if(mUSER_id.equalsIgnoreCase(ADD_USER_ID))
                    {
                        IB_favourite.setVisibility(View.INVISIBLE);
                        mChat_layout.setVisibility(View.GONE);

                    }
                    else
                    {
                        IB_favourite.setVisibility(View.VISIBLE);
                        mChat_layout.setVisibility(View.VISIBLE);

                    }
                }
                else
                {


                    // Not fully expanded or collapsed

                    IB_favourite.setVisibility(View.GONE);
                    Ib_share.setVisibility(View.GONE);
                    back.setBackgroundResource(0);
                    tv_Title.setVisibility(View.VISIBLE);


                    if(mUSER_id.equalsIgnoreCase(ADD_USER_ID))
                    {
                        IB_favourite.setVisibility(View.INVISIBLE);
                        mChat_layout.setVisibility(View.GONE);

                    }
                    else
                    {
                        IB_favourite.setVisibility(View.VISIBLE);
                        mChat_layout.setVisibility(View.VISIBLE);

                    }
                }
            }
        });

        IB_favourite.setOnClickListener(this);
        back.setOnClickListener(this);
        Ib_share.setOnClickListener(this);




    }
    private void XML()
    {

        tv_chat=(TextView)findViewById(R.id.tv_chat);
        iv_largeImage = (ImageView) findViewById(R.id.iv_largeImage);
        Display metrics=getWindowManager().getDefaultDisplay();
        int width=metrics.getWidth();

        touchableMap = (TouchableWrapper) findViewById(R.id.touchableMap);
        touchableMap.setListener(this);


        mRecyclerView = (RecyclerView) findViewById(R.id.mRecycle);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mPhotoAdapter = new SmallPhotoAdapter(ProductDetailsActivity.this, mPhoto_List);
        mRecyclerView.setAdapter(mPhotoAdapter);
        mProgressbar = (ProgressBar) findViewById(R.id.mProgressbar);

        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_city = (TextView) findViewById(R.id.tv_city);
        tv_postedDay = (TextView) findViewById(R.id.tv_postedDay);
        tv_categoryName = (TextView) findViewById(R.id.tv_categoryName);
        tv_city2 = (TextView) findViewById(R.id.tv_city2);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_Desc = (TextView) findViewById(R.id.tv_Desc);
        iv_profileImage = (ImageView) findViewById(R.id.iv_profileImage);
        mChat_layout=(LinearLayout)findViewById(R.id.mChat_layout);

        mProfile_Relative=(RelativeLayout)findViewById(R.id.mProfile_Relative);
        mProfile_Relative.setOnClickListener(this);

        mChat_layout.setOnClickListener(this);



    }
    private void hitAPI() {

        AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();
        mAsyncHttpClient.setConnectTimeout(90000);
        mAsyncHttpClient.setResponseTimeout(90000);
        mAsyncHttpClient.setTimeout(90000);

        RequestParams mParams = new RequestParams();
        mParams.add("user_id", UserData.getUserID(ProductDetailsActivity.this));
        mParams.add("ads_id", addID);

        String mURL = NetworkClass.BASE_URL + NetworkClass.GET_SINGLE_PRODUCT_DETAILS;
        Log.e(TAG, "===========mURL====" + mURL);

        Log.e(TAG, "============mParams=========" + mParams);
        mAsyncHttpClient.post(mURL, mParams, new JsonHttpResponseHandler() {


            @Override
            public void onStart() {
                super.onStart();
                mProgressDialog = new ProgressDialog(ProductDetailsActivity.this);
                mProgressDialog.setMessage("Please wait");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Log.e(TAG, "===========response=========" + response);


                if (statusCode == 200) {

                    try {

                        String code = response.getString("code");
                        if (code.equalsIgnoreCase("201")) {



                            JSONArray data = response.getJSONArray("data");
                            for (int indx = 0; indx < data.length(); indx++)
                            {
                                JSONObject mDataObject = data.getJSONObject(indx);
                                 mAddId = mDataObject.getString("user_ads_id");
                                mAdd_UserId = mDataObject.getString("user_id");
                                String category_id = mDataObject.getString("category_id");
                                String eng_cityName = mDataObject.getString("eng_cityName");
                                String ar_cityName = mDataObject.getString("ar_cityName");
                                String eng_title = mDataObject.getString("eng_title");
                                String ar_title = mDataObject.getString("ar_title");
                                String item_type = mDataObject.getString("item_type");
                                String property_type = mDataObject.getString("property_type");
                                String description = mDataObject.getString("description");
                                String price = mDataObject.getString("price");

                                String ad_status = mDataObject.getString("ad_status");
                                String active = mDataObject.getString("active");
                                String is_fav = mDataObject.getString("is_fav");
                                String update_date = mDataObject.getString("update_date");
                                String name = mDataObject.getString("name");
                                String profile_image = mDataObject.getString("profile_image");
                                String lat= mDataObject.getString("lat");
                                String lng =mDataObject.getString("lng");
                                 Sender_Device_Id= mDataObject.getString("device_token");


                                if(lat.equalsIgnoreCase("") || lng.equalsIgnoreCase(""))
                                {


                                }
                                else
                                {

                                    try {
                                       LatLng mLatLng= new LatLng(Double.valueOf(lat), Double.valueOf(lng));
                                        mGoogleMap.addMarker(new MarkerOptions().position(mLatLng)
                                                .title(eng_cityName));
                                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(mLatLng));
                                    }
                                    catch (Exception e)
                                    {
                                        Log.e(TAG, "===========ERRORR======="+e.getMessage());
                                    }



                                }


                                JSONArray adsImageArray=mDataObject.getJSONArray("adsImage");
                                for (int child=0; child<adsImageArray.length(); child++)
                                {
                                    JSONObject mImageObject= adsImageArray.getJSONObject(child);
                                    if(child==0)
                                    {

                                        image= mImageObject.getString("image");
                                    }

                                }


                                UserData.setSellerImage(ProductDetailsActivity.this, profile_image);
                                UserData.setSellerName(ProductDetailsActivity.this, name);
                                UserData.setSellerId(ProductDetailsActivity.this, mAdd_UserId);



                                Log.e(TAG, "===========profile_image======"+NetworkClass.BASE_URL + profile_image);

                                Picasso.get() .load(NetworkClass.BASE_URL + profile_image)
                                        .error(R.mipmap.noimage)
                                        .resize(50, 50)
                                        .into(iv_profileImage, new Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                            public void onError(Exception e) {

                                            }
                                        });



                                tv_Title.setText(eng_title.toUpperCase());

                                tv_Name.setText(name.substring(0, 1).toUpperCase() + name.substring(1));

                                tv_postedDay.setText(TimeShow.showTime(update_date));
                                tv_Desc.setText(description);
                                String eng_categoryName = "", ar_categoryName = "", mSt_ItemType = "", mStProItem = "";
                                if (mDataObject.has("eng_categoryName")) {
                                    eng_categoryName = mDataObject.getString("eng_categoryName");

                                }
                                if (mDataObject.has("ar_categoryName")) {
                                    ar_categoryName = mDataObject.getString("ar_categoryName");

                                }

                                if (item_type.equalsIgnoreCase("")) {


                                } else if (item_type.equalsIgnoreCase("1")) {
                                    mSt_ItemType = "NEW";


                                } else if (item_type.equalsIgnoreCase("2")) {

                                    mSt_ItemType = "USED";
                                }

                                if (property_type.equalsIgnoreCase("")) {


                                } else if (property_type.equalsIgnoreCase("1")) {
                                    mStProItem = "BUY";

                                } else if (property_type.equalsIgnoreCase("2")) {

                                    mStProItem = "SELL";

                                }

                                if (price.length() == 11)
                                {

                                    tv_price.setTextSize(12);
                                    String[] separated = price.split("\\.");
                                     mprice = separated[0];

                                    tv_price.setText(getResources().getString(R.string.price) + " " + mprice);
                                }
                                else
                                {
                                    String[] separated = price.split("\\.");
                                     mprice = separated[0];
                                    tv_price.setTextSize(10);
                                    tv_price.setText(getResources().getString(R.string.price) + " " + mprice);
                                }


                                tv_title.setText(eng_title.substring(0, 1).toUpperCase() + eng_title.substring(1));
                                tv_city.setText(eng_cityName.substring(0, 1).toUpperCase() + eng_cityName.substring(1));
                                tv_city2.setText(eng_cityName.substring(0, 1).toUpperCase() + eng_cityName.substring(1));
                                try {
                                    tv_categoryName.setText(eng_categoryName.substring(0, 1).toUpperCase() + eng_categoryName.substring(1) + " " + mStProItem + " " + mSt_ItemType);

                                }
                                catch (Exception e)
                                {
                                    Log.e(TAG, "================ERROR======"+e.getMessage());
                                }

                                if (is_fav.equalsIgnoreCase("0"))
                                {
                                    IB_favourite.setImageResource(R.mipmap.favrate);


                                }
                                else
                                {

                                    IB_favourite.setImageResource(R.mipmap.blue_favourite);


                                }
                                Display metrics=getWindowManager().getDefaultDisplay();
                                int width=metrics.getWidth();




                                Picasso.get()
                                        .load(image)
                                        .resize(0, width)
                                        .into(iv_largeImage, new Callback() {
                                            @Override
                                            public void onSuccess() {
                                                mProgressbar.setVisibility(View.GONE);
                                            }

                                            @Override
                                            public void onError(Exception e) {
                                                mProgressbar.setVisibility(View.GONE);
                                            }
                                        });

                                JSONArray adsImage = mDataObject.getJSONArray("adsImage");
                                for (int child = 0; child < adsImage.length(); child++) {
                                    JSONObject mImageObject = adsImage.getJSONObject(child);
                                    String smallImage = mImageObject.getString("image");

                                    mPhoto_List.add(smallImage);

                                }

                            }

                            mPhotoAdapter.notifyDataSetChanged();

                        }
                    } catch (Exception e) {
                        Log.e(TAG, "=============ERRORR===========" + e.getMessage());
                    }


                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

                Log.e(TAG, "===============ERRORR=============" + responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Log.e(TAG, "===============ERRORR=============" + errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Log.e(TAG, "===============ERRORR=============" + errorResponse);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mProgressDialog.dismiss();
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.mChat_layout:

                mIntent= new Intent(ProductDetailsActivity.this, BaseActivity.class);

                ProductDataPojo actor= new ProductDataPojo();
                actor.setADD_USER_ID(mAdd_UserId);
                actor.setADD_ID(mAddId);
                actor.setP_NAME(tv_Title.getText().toString());
                actor.setDEVICE_TOKEN(Sender_Device_Id);
                actor.setPRICE( mprice);
                actor.setPRODUCT_PICTURE(image);
                actor.setSENDER_ID(mUSER_id);
                mIntent.putExtra("DATA", actor);
                mIntent.putExtra("NAME", "CHAT");
                startActivity(mIntent);




                break;


            case R.id.Ib_share:
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,"Test");
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);

                    break;
            case R.id.back:
                onBackPressed();
                break;

            case R.id.IB_favourite:

                if(Utility.checkInternet(ProductDetailsActivity.this))
                {


                    likeApi();
                }
                break;
            case R.id.mProfile_Relative:

                 mIntent= new Intent(ProductDetailsActivity.this, BaseActivity.class);
                mIntent.putExtra("NAME", "SELLER_PROFILE");
                startActivity(mIntent);

                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        this.mGoogleMap = googleMap;
        this.mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
        this.mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        this.mGoogleMap.getUiSettings().setAllGesturesEnabled(false);
        this.mGoogleMap.getUiSettings().setCompassEnabled(false);
        this.mGoogleMap.getUiSettings().setRotateGesturesEnabled(false);
        this.mGoogleMap.getUiSettings().setScrollGesturesEnabled(false);
        this.mGoogleMap.getUiSettings().setZoomGesturesEnabled(false);
        this.mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
    }

    @Override
    public void onTouchStart() {


    }

    @Override
    public void onTouchEnd() {

    }
    public void likeApi()
    {

        AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();
        mAsyncHttpClient.setTimeout(90000);
        mAsyncHttpClient.setConnectTimeout(90000);
        mAsyncHttpClient.setResponseTimeout(90000);

        RequestParams mParams= new RequestParams();
        mParams.add("user_id",UserData.getUserID(ProductDetailsActivity.this) );
        mParams.add("ads_id",addID );



        String mURL = NetworkClass.BASE_URL + NetworkClass.ADD_TO_FAVOURITE;
        Log.e(TAG, "==============URL==========" + mURL);
        mAsyncHttpClient.post(mURL, mParams,new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();

                mProgressDialog= new ProgressDialog(ProductDetailsActivity.this);
                mProgressDialog.setMessage(getResources().getString(R.string.Pleasewait));
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Log.e(TAG, "==========response=======" + response);
                if (statusCode == 200)
                {


                    try
                    {
                        String code = response.getString("code");
                        String message=response.getString("message");

                        if (message.contains("Favorite success."))
                        {


                            IB_favourite.setImageResource(R.mipmap.blue_favourite);
                            Intent mIntent= new Intent();
                            mIntent.putExtra("FAVORITE", "1");
                            mIntent.putExtra("ADD_ID", addID);
                            mIntent.setAction("ADDFAVORITE");
                            sendBroadcast(mIntent);//HomeFragment

                        }
                        else
                        {

                            IB_favourite.setImageResource(R.mipmap.favrate);

                            Intent mIntent= new Intent();
                            mIntent.putExtra("FAVORITE", "0");
                            mIntent.putExtra("ADD_ID", addID);
                            mIntent.setAction("ADDFAVORITE");
                            sendBroadcast(mIntent);//HomeFragment
                        }

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

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

    public void setImage(String murl)
    {


        Display metrics=getWindowManager().getDefaultDisplay();
        int width=metrics.getWidth();

        Picasso.get()
                .load(murl)
                .resize(0, width)
                .into(iv_largeImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        mProgressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        mProgressbar.setVisibility(View.GONE);
                    }
                });
    }

}
