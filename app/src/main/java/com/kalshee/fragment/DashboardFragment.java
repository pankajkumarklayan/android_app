package com.kalshee.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kalshee.R;
import com.kalshee.adapter.SellingAdapter;
import com.kalshee.modal.DashboardImplement;
import com.kalshee.pojo.ProductModal;
import com.kalshee.userData.UserData;
import com.kalshee.view.DashboardView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by eWeb_A1 on 6/11/2018.
 */

public class DashboardFragment extends Fragment implements View.OnClickListener  , DashboardView{

    private TextView tv_selling, tv_sold, tv_favorite;
    RecyclerView mRecycle;
    SellingAdapter mAdapter;
    ArrayList<ProductModal> mList = new ArrayList<ProductModal>();
    Fragment mFragment;
    ProgressDialog mProgressDialog;
    DashboardImplement mImplement;
    String TAG="DashboardFragment";
    TextView tv_noProduct;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View mView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        mImplement= new DashboardImplement(getActivity(), DashboardFragment.this);

        XML(mView);
        return mView;
    }


    @Override
    public void setMenuVisibility(boolean menuVisible)
    {
        if(menuVisible)
        {

            try {
                mImplement.setInput(UserData.getUserID(getContext()));
            }
            catch (Exception e)
            {

            }

        }
        else
        {
            Log.e(TAG, "============NOTVISIBLE=======");
        }

    }

    private void XML(View view) {

        tv_noProduct=(TextView)view.findViewById(R.id.tv_noProduct);
        tv_selling = (TextView) view.findViewById(R.id.tv_selling);
        tv_sold = (TextView) view.findViewById(R.id.tv_sold);
        tv_favorite = (TextView) view.findViewById(R.id.tv_favorite);

        tv_selling.setOnClickListener(this);
        tv_sold.setOnClickListener(this);
        tv_favorite.setOnClickListener(this);


        mRecycle = (RecyclerView) view.findViewById(R.id.mRecycle);
        mRecycle.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new SellingAdapter(getContext(), mList, DashboardFragment.this);
        mRecycle.setAdapter(mAdapter);


        tv_selling.setBackground(getResources().getDrawable(R.drawable.selling_blue_back));
        tv_selling.setTextColor(getResources().getColor(R.color.colorWhite));





    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_selling:

                getChildFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                tv_selling.setBackground(getResources().getDrawable(R.drawable.selling_blue_back));
                tv_selling.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_favorite.setTextColor(getResources().getColor(R.color.colorAccent));
                tv_sold.setTextColor(getResources().getColor(R.color.colorAccent));
               tv_sold.setBackgroundResource(0);
                tv_favorite.setBackgroundResource(0);
                mImplement.setInput(UserData.getUserID(getContext()));


                break;
            case R.id.tv_sold:
                mFragment= new SoldFragment();
                getChildFragmentManager().beginTransaction().replace(R.id.mLocalContainer, mFragment).addToBackStack("").commit();

                tv_sold.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                tv_sold.setTextColor(getResources().getColor(R.color.colorWhite));


                tv_selling.setTextColor(getResources().getColor(R.color.colorAccent));
                tv_favorite.setTextColor(getResources().getColor(R.color.colorAccent));
                tv_selling.setBackgroundResource(0);
                tv_favorite.setBackgroundResource(0);


                break;
            case R.id.tv_favorite:

                mFragment= new FavoriteFragment();
                getChildFragmentManager().beginTransaction().replace(R.id.mLocalContainer, mFragment).addToBackStack("").commit();

                tv_favorite.setBackground(getResources().getDrawable(R.drawable.favorite_blue_back));
                tv_favorite.setTextColor(getResources().getColor(R.color.colorWhite));



                tv_sold.setTextColor(getResources().getColor(R.color.colorAccent));
                tv_selling.setTextColor(getResources().getColor(R.color.colorAccent));
               tv_sold.setBackgroundResource(0);
               tv_selling.setBackgroundResource(0);

                break;
        }
    }

    @Override
    public void showSuccess(String response) {
        try {
            JSONObject mJsonObject= new JSONObject(response);
            String code = mJsonObject.getString("code");
            if (code.equalsIgnoreCase("201")) {


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
                    String is_fav= dataObject.getString("is_fav");
                    String ad_status=dataObject.getString("ad_status");
                    String active =dataObject.getString("active");

                    if(ad_status.equalsIgnoreCase("0"))
                    {
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
                            actor.setActive(active);


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
                            mList.add(actor);

                        }




                    }
                    else
                    {


                    }



                }
                if(mList.size()==0)
                {
                    tv_noProduct.setVisibility(View.VISIBLE);
                }
                else
                {
                    tv_noProduct.setVisibility(View.GONE);
                    mList.size();
                    mAdapter.notifyDataSetChanged();
                }


            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "===========ERRORR========" + e.getMessage());
        }

    }

    @Override
    public void showErrorr(String error) {

        Log.e(TAG, "===============ERRORR========="+error);
    }

    @Override
    public void showProgressBar() {

        mProgressDialog= new ProgressDialog(getActivity());
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.Pleasewait));
        mProgressDialog.show();
    }

    @Override
    public void hideProgressBar() {
        mProgressDialog.dismiss();

    }

    @Override
    public void showSoldSuccess(String response) {

        try {

            JSONObject mJsonObject= new JSONObject(response);
            String code=mJsonObject.getString("code");
            String message= mJsonObject.getString("message");
            if(code.equalsIgnoreCase("201"))
            {

                String ads_id= mJsonObject.getString("ads_id");

                for (int indx= 0; indx<mList.size(); indx++)
                {

                    String userAddId=mList.get(indx).getUser_ads_id();
                    if(ads_id.equalsIgnoreCase(userAddId))
                    {

                        mList.remove(indx);
                    }
                }

            }
            else
            {


            }

            mAdapter.notifyDataSetChanged();

            showMessage(message);
        }
        catch (Exception e)
        {
            Log.e(TAG, "=========ERROR====="+e.getMessage());
        }
    }

    @Override
    public void deleteSuccess(String response) {

        try {

            JSONObject mJsonObject= new JSONObject(response);
            String code= mJsonObject.getString("code");
            String message=mJsonObject.getString("message");

            if(code.equalsIgnoreCase("201"))
            {

                String ads_id=mJsonObject.getString("ads_id");

                for (int indx=0; indx<mList.size(); indx++)
                {

                    String userAddId= mList.get(indx).getUser_ads_id();
                    if(ads_id.equalsIgnoreCase(userAddId))
                    {

                        mList.remove(indx);
                    }
                }

                mAdapter.notifyDataSetChanged();

            }
            showMessage(message);
        }
        catch (Exception e)
        {
            Log.e(TAG, "===========ERRORR======="+e.getMessage());
        }
    }


    public void clickProduct(String addId)
    {

        mImplement.setSoldInput(addId);
    }
    public void deleteProduct(String addId)
    {
        showDeleteAlert(addId);

    }
public void showMessage(String msg)
{

    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
}
    private void showDeleteAlert(final String addId)
    {

        AlertDialog.Builder mBuilder= new AlertDialog.Builder(getActivity());
        mBuilder.setCancelable(false);
        mBuilder.setMessage(R.string.areyousureyouwanttodelete);
        mBuilder.setNegativeButton(getActivity().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        mBuilder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mImplement.deleteInput(addId);
            }
        });
        mBuilder.show();
    }
}
