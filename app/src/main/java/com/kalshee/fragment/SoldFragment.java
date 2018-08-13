package com.kalshee.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.kalshee.R;
import com.kalshee.adapter.SellingAdapter;
import com.kalshee.adapter.SoldAdapter;
import com.kalshee.modal.SoldViewmplement;
import com.kalshee.pojo.ProductModal;
import com.kalshee.userData.UserData;
import com.kalshee.view.SoldView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by eWeb_A1 on 6/12/2018.
 */

public class SoldFragment extends Fragment implements  SoldView
{

    ArrayList<ProductModal>mList= new ArrayList<>();
    RecyclerView mRecycle;
    SoldAdapter mAdapter;
    ProgressDialog mProgressDialog;
    String TAG= "SoldFragment";
    SoldViewmplement mSoldViewmplement;
    TextView tv_noProduct;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View mView= inflater.inflate(R.layout.fragment_sold_screen, container , false);

        mSoldViewmplement= new SoldViewmplement(getContext(), SoldFragment.this);
        mSoldViewmplement.setInput(UserData.getUserID(getContext()));
        XML(mView);

        return mView;
    }

    private void XML(View view) {

        tv_noProduct=(TextView)view.findViewById(R.id.tv_noProduct);


        mRecycle = (RecyclerView) view.findViewById(R.id.mRecycle);
        mRecycle.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new SoldAdapter(getContext(), mList, SoldFragment.this);
        mRecycle.setAdapter(mAdapter);





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

                    if(ad_status.equalsIgnoreCase("1"))
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

        Log.e(TAG, "==============ERRORR======="+error);
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
    public void deleteSuccess(String response)
    {

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
    @Override
    public void rePostSuccess(String response)
    {

        try {

            JSONObject mJsonObject= new JSONObject(response);
            String code=mJsonObject.getString("code");
            String message=mJsonObject.getString("message");
            if(code.equalsIgnoreCase("201"))
            {

                String ads_id= mJsonObject.getString("ads_id");

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
            Log.e(TAG, "===========ERROrr======"+e.getMessage());
        }


    }
    public void deletePost(String addId)
    {

        showDeleteAlert(addId);
    }
    public void rePostADD(String addId)
    {
        mSoldViewmplement.rePostInput(addId);

    }

    private void showMessage(String msg)
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
                mSoldViewmplement.deleteInput(addId);
            }
        });
        mBuilder.show();
    }
}
