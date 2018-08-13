package com.kalshee.fragment;

import android.app.ProgressDialog;
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

import com.kalshee.R;
import com.kalshee.adapter.FavoriteAdapter;
import com.kalshee.adapter.SoldAdapter;
import com.kalshee.modal.FavouriteViewImple;
import com.kalshee.pojo.FavoritePojo;
import com.kalshee.pojo.ProductModal;
import com.kalshee.userData.UserData;
import com.kalshee.view.FavouriteView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by eWeb_A1 on 6/12/2018.
 */

public class FavoriteFragment extends Fragment implements FavouriteView {

    ArrayList<FavoritePojo> mList = new ArrayList<>();
    RecyclerView mRecycle;
    FavoriteAdapter mAdapter;
    ProgressDialog mProgressDialog;
    String TAG = "FavoriteFragment";
    FavouriteViewImple mFavouriteViewImple;
    TextView tv_noProduct;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_favorite_screen, container, false);

        mFavouriteViewImple = new FavouriteViewImple(getContext(), FavoriteFragment.this);
        mFavouriteViewImple.setInput(UserData.getUserID(getContext()));

        XML(mView);
        return mView;
    }

    private void XML(View view) {

        tv_noProduct = (TextView) view.findViewById(R.id.tv_noProduct);
        mRecycle = (RecyclerView) view.findViewById(R.id.mRecycle);
        mRecycle.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new FavoriteAdapter(getContext(), mList);
        mRecycle.setAdapter(mAdapter);


    }

    @Override
    public void showSuccess(String response) {
        try {
            JSONObject mJsonObject = new JSONObject(response);
            String code = mJsonObject.getString("code");
            if (code.equalsIgnoreCase("201")) {


                mList.clear();
                JSONArray data = mJsonObject.getJSONArray("data");
                for (int indx = 0; indx < data.length(); indx++) {


                    FavoritePojo actor= new FavoritePojo();
                    JSONObject dataObject = data.getJSONObject(indx);
                    String ads_id = dataObject.getString("ads_id");
                    String user_id = dataObject.getString("user_id");
                    String eng_title = dataObject.getString("eng_title");
                    String ar_title = dataObject.getString("ar_title");
                    String description = dataObject.getString("description");
                    String price = dataObject.getString("price");
                    String active= dataObject.getString("active");

                    String image="";

                    JSONArray adsImage= dataObject.getJSONArray("adsImage");
                    for (int child=0; child<adsImage.length(); child++)
                    {

                        JSONObject mImagObject= adsImage.getJSONObject(child);
                        if(child==0)
                        {

                            image=mImagObject.getString("image");

                        }
                    }

                    if(image.equalsIgnoreCase(""))
                    {




                    }
                    else
                    {
                        actor.setAr_title(ar_title);
                        actor.setEng_title(eng_title);
                        actor.setImage(image);
                        actor.setDescription(description);
                        actor.setPrice(price);
                        mList.add(actor);

                    }




                }
                if (mList.size() == 0) {
                    tv_noProduct.setVisibility(View.VISIBLE);
                } else {

                    tv_noProduct.setVisibility(View.GONE);
                    mList.size();
                    mAdapter.notifyDataSetChanged();
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "===========ERRORR========" + e.getMessage());
        }

    }

    @Override
    public void showErrorr(String error) {

        Log.e(TAG, "===========ERRORR========" + error);
    }

    @Override
    public void showProgressBar() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.Pleasewait));
        mProgressDialog.show();
    }

    @Override
    public void hideProgressBar() {

        mProgressDialog.dismiss();
    }
}
