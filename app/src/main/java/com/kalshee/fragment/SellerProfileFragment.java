package com.kalshee.fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kalshee.R;
import com.kalshee.adapter.Seller_SellingAdapter;
import com.kalshee.modal.SellerProfileImple;
import com.kalshee.pojo.ProductModal;
import com.kalshee.userData.UserData;
import com.kalshee.utill.NetworkClass;
import com.kalshee.utill.Utility;
import com.kalshee.view.SellerProfileView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by eWeb_A1 on 6/22/2018.
 */

public class SellerProfileFragment extends Fragment implements View.OnClickListener ,SellerProfileView{

    ImageButton IBBack;
    private TextView tv_selling,tv_sold,tv_name;
    private RecyclerView recyclerView;
    private ImageView img_prf;
    ProgressBar mProgressbar;

    Seller_SellingAdapter mSellingAdapter;
    ArrayList<ProductModal> mSelling_list= new ArrayList<>();
    String TAG="SellerProfileFragment";
    ProgressDialog mProgressDialog;
    SellerProfileImple mSellerProfileImple;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_seller_profile, container, false);

        mSellerProfileImple= new SellerProfileImple(getContext(), SellerProfileFragment.this);
        mSellerProfileImple.setInput(UserData.getUserID(getActivity()),UserData.getSellerId(getActivity())) ;

        XML(mView);
        return mView;
    }

    private void XML(View view)
    {

        img_prf=(ImageView)view.findViewById(R.id.img_prf);
        tv_name=(TextView)view.findViewById(R.id.tv_name);
        mProgressbar=(ProgressBar)view.findViewById(R.id.mProgressbar);
        IBBack = (ImageButton) view.findViewById(R.id.IBBack);
        tv_selling=(TextView)view.findViewById(R.id.tv_selling);
        tv_sold=(TextView)view.findViewById(R.id.tv_sold);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mSellingAdapter= new Seller_SellingAdapter(getActivity(),mSelling_list);
        recyclerView.setAdapter(mSellingAdapter);


        String image= UserData.getSellerImage(getActivity());
        String name= UserData.getSellerName(getActivity());


        String mPUrl= NetworkClass.BASE_URL+image;
        Log.e(TAG, "=========mPUrl======="+mPUrl);
        Picasso.get().load(mPUrl)
                .placeholder(R.mipmap.logo)
                .error(R.mipmap.noimage)
                .into(img_prf, new Callback() {
                    @Override
                    public void onSuccess() {
                        mProgressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        mProgressbar.setVisibility(View.GONE);
                    }
                });

        tv_name.setText(name.substring(0,1).toUpperCase()+name.substring(1));

        IBBack.setOnClickListener(this);
        tv_selling.setOnClickListener(this);
        tv_sold.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.IBBack:

                getActivity().onBackPressed();
                break;
            case R.id.tv_selling:
                tv_selling.setBackgroundResource(R.drawable.selling_select);
                tv_selling.setTextColor(Color.WHITE);
                tv_sold.setBackgroundResource(0);
                tv_sold.setTextColor(Color.parseColor("#1565C0"));
                recyclerView.setVisibility(View.VISIBLE);
                mSellingAdapter.setmList(mSelling_list, "SELLING");

                break;
            case R.id.tv_sold:
                tv_sold.setBackgroundResource(R.drawable.sold_select);
                tv_sold.setTextColor(Color.WHITE);
                tv_selling.setBackgroundResource(0);
                tv_selling.setTextColor(Color.parseColor("#1565C0"));

                ArrayList<ProductModal> mSold_list= new ArrayList<>();
               for (int indx=0; indx<mSelling_list.size(); indx++)
               {

                   if(mSelling_list.get(indx).getAd_status().equalsIgnoreCase("0"))
                   {


                   }
                   else
                   {



                       mSold_list.add(mSelling_list.get(indx));

                   }

               }

               mSellingAdapter.setmList(mSold_list, "SOLD");

                break;

        }
    }

    @Override
    public void showSuccess(String response) {

        Log.e(TAG, "======response========="+response);
        try
        {
            JSONObject mJsonObject= new JSONObject(response);
            String code = mJsonObject.getString("code");
            if (code.equalsIgnoreCase("201")) {


                mSelling_list.clear();
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
                    String is_fav= dataObject.getString("is_fav");
                    String ad_status=dataObject.getString("ad_status");


                    JSONArray adsImage= dataObject.getJSONArray("adsImage");
                    String image="";

                    for (int child=0; child<adsImage.length(); child++)
                    {
                        JSONObject mImageObject= adsImage.getJSONObject(child);
                        if(child==0)
                        {

                            image=mImageObject.getString("image");

                        }


                    }


                    if (image.equalsIgnoreCase(""))
                    {


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
                        actor.setAd_status(ad_status);


                        if(price.contains("."))
                        {

                            String [] mpriceArr= price.split("\\.");
                            String mPrice=mpriceArr[0];

                            actor.setPrice(mPrice);
                        }
                        else
                        {
                            actor.setPrice(price);
                        }

                        actor.setImage(image);
                        actor.setIs_fav(is_fav);
                        mSelling_list.add(actor);

                    }


                }
                getActivity().runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {

                        mSelling_list.size();
                        mProgressbar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        mSellingAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(mSellingAdapter);
                    }
                });




            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "===========ERRORR========" + e.getMessage());
        }

    }

    @Override
    public void showErrorr(String error) {

        Log.e(TAG, "============ERRORR======="+error);
    }

    @Override
    public void showProgressBar() {

        mProgressDialog= new ProgressDialog(getContext());
        mProgressDialog.setMessage(getString(R.string.Pleasewait));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void hideProgressBar() {

        mProgressDialog.dismiss();
    }
}
