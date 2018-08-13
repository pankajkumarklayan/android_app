package com.kalshee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kalshee.adapter.FilterAdapter;
import com.kalshee.globalArray.GlobalArray;
import com.kalshee.modal.FilterImplement;
import com.kalshee.pojo.FilterModal;
import com.kalshee.userData.UserData;
import com.kalshee.view.FilterView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, FilterView{
    List<String> mName;
    ArrayList<FilterModal> mList = new ArrayList<>();
    RecyclerView mRecycle;
    FilterAdapter mAdapter;
    ImageButton ib_Back ;
    TextView tv_resetFilter;
    RadioGroup mRadioGroup;
    RadioButton Rd_latest, Rd_Oldest, Rd_LowtoHigh, Rd_HightoLow,Rd_buy,Rd_Rent;
    EditText et_Maximum, et_Minimum;
    String TAG="FilterActivity";
    Button Bt_Apply;
    String stMinimum="",stMaximum="",stSortBy="",stRentType="";
    LinearLayout mRent_layout;
   RadioGroup mRentType_RadioGroup;
   ProgressDialog mProgressDialog;
   FilterImplement mFilterImplement;
   String minimum="",maximum="",rentType="",sortBy="";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        mFilterImplement= new FilterImplement(this, FilterActivity.this);
        mFilterImplement.setInput(UserData.getUserID(this));

        minimum = getIntent().getStringExtra("MINIMUM");
        maximum = getIntent().getStringExtra("MAXIMUM");
        rentType = getIntent().getStringExtra("RENTYPE");
        sortBy = getIntent().getStringExtra("SORTBY");
        XML();

    }
    private void XML()
    {
        Bt_Apply=(Button)findViewById(R.id.Bt_Apply);
        Bt_Apply.setOnClickListener(this);


        mList.clear();
        TypedArray blueImage = getResources().obtainTypedArray(R.array.Blue_image);
        TypedArray redImages = getResources().obtainTypedArray(R.array.Red_image);
        mName = Arrays.asList(getResources().getStringArray(R.array.Filter_type));

        mAdapter = new FilterAdapter(FilterActivity.this, mList);
        mRecycle = (RecyclerView) findViewById(R.id.mRecycle);

        DisplayMetrics displayMetrics= getResources().getDisplayMetrics();
        float width= displayMetrics.widthPixels/ displayMetrics.density;
        int noOfColumns = (int) (width / 100);




        mRecycle.setLayoutManager(new GridLayoutManager(FilterActivity.this, noOfColumns));
        mRecycle.setAdapter(mAdapter);
        mRecycle.setHasFixedSize(true);



        ib_Back=(ImageButton)findViewById(R.id.ib_Back);
        ib_Back.setOnClickListener(this);

        tv_resetFilter=(TextView)findViewById(R.id.tv_resetFilter);
        tv_resetFilter.setOnClickListener(this);


        et_Minimum=(EditText)findViewById(R.id.et_Minimum);
        et_Maximum =(EditText)findViewById(R.id.et_Maximum);

        mRadioGroup=(RadioGroup)findViewById(R.id.mRadioGroup);
        Rd_latest=(RadioButton)findViewById(R.id.Rd_latest);
        Rd_Oldest=(RadioButton)findViewById(R.id.Rd_Oldest);
        Rd_LowtoHigh=(RadioButton)findViewById(R.id.Rd_LowtoHigh);
        Rd_HightoLow=(RadioButton)findViewById(R.id.Rd_HightoLow);

        mRent_layout=(LinearLayout)findViewById(R.id.mRent_layout) ;
        mRentType_RadioGroup=(RadioGroup)findViewById(R.id.mRentType_RadioGroup);
        Rd_buy=(RadioButton)findViewById(R.id.Rd_buy);
        Rd_Rent=(RadioButton)findViewById(R.id.Rd_Rent);

        mRentType_RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int id=group.getCheckedRadioButtonId();
                switch (id)
                {
                    case R.id.Rd_buy:

                        stRentType="1";
                        break;
                    case R.id.Rd_Rent:

                        stRentType="2";
                        break;

                }

            }
        });
        mRadioGroup.setOnCheckedChangeListener(this);



        if(!minimum.equalsIgnoreCase(""))
        {

            et_Minimum.setText(minimum);

        }
         if(!maximum.equalsIgnoreCase(""))
        {

            et_Maximum.setText(maximum);
        }
        if(sortBy.equalsIgnoreCase("LowtoHigh"))
        {

            Rd_LowtoHigh.setChecked(true);


        }
        if(sortBy.equalsIgnoreCase("HightoLow"))
        {
            Rd_HightoLow.setChecked(true);

        }
        if(sortBy.equalsIgnoreCase("Latest"))
        {

            Rd_latest.setChecked(true);

        }
        if(sortBy.equalsIgnoreCase("Oldest"))
        {

            Rd_Oldest.setChecked(true);

        }
        if(rentType.equalsIgnoreCase("1"))
        {

            Rd_buy.setChecked(true);

        }
        if(rentType.equalsIgnoreCase("2"))
        {

            Rd_Rent.setChecked(true);

        }
    }
    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {

            case R.id.ib_Back:
                hideSoftKey(v);
                onBackPressed();
                break;

            case R.id.tv_resetFilter:
                hideSoftKey(v);
                ResetFilter();
                break;

            case R.id.Bt_Apply:
                hideSoftKey(v);
                onBackPressed();
                break;

        }
    }
    @Override
    public void onBackPressed()
    {


        stMinimum= et_Minimum.getText().toString().trim();
        stMaximum= et_Maximum.getText().toString().trim();

       Intent mIntent= new Intent();
       mIntent.putExtra("MINIMUM",stMinimum );
       mIntent.putExtra("MAXIMUM", stMaximum);
        mIntent.putExtra("SORTBY",stSortBy);
        mIntent.putExtra("RENTYPE",stRentType);
        mIntent.putExtra("SORTBY", stSortBy);
       setResult(100, mIntent);
        finish();


    }
    private void hideSoftKey(View view)
    {
        InputMethodManager manager=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void showProgressBar()
    {
        mProgressDialog= new ProgressDialog(FilterActivity.this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.Pleasewait));
        mProgressDialog.show();


    }
    @Override
    public void hideProgressBar() {

        mProgressDialog.hide();
    }

    @Override
    public void showErrorr(String response) {

        Log.e(TAG, "============ERRORR=========="+response);
    }

    private void showMessage(String msg)
    {

        Toast.makeText(FilterActivity.this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showSuccess(String response)
    {

        try {

            JSONObject mJsonObject= new JSONObject(response);
            String code=mJsonObject.getString("code");
            if(code.equalsIgnoreCase("201"))
            {


                JSONArray data= mJsonObject.getJSONArray("data");
                TypedArray blueImage = getResources().obtainTypedArray(R.array.Blue_image);
                TypedArray redImages = getResources().obtainTypedArray(R.array.Red_image);

                for (int indx=0; indx<data.length(); indx++)
                {

                    JSONObject dataObject= data.getJSONObject(indx);
                    String category_id=dataObject.getString("category_id");
                    String eng_title= dataObject.getString("eng_title");
                    String ar_title= dataObject.getString("ar_title");
                    String cat_slug= dataObject.getString("cat_slug");
                    String created_date=dataObject.getString("created_date");
                    String update_date=dataObject.getString("update_date");

                    FilterModal actor = new FilterModal();
                    actor.setEng_title(eng_title);
                    actor.setAr_title(ar_title);
                    actor.setCategoryId(category_id);

                    try {
                        actor.setBlueimage(blueImage.getResourceId(indx, -1));
                        actor.setRedImage(redImages.getResourceId(indx, -1));
                    }
                    catch (Exception e)
                    {
                        Log.e(TAG, "===========ERRORR====="+e.getMessage());
                    }


                    actor.setCheck(false);
                    actor.setTextColor("Black");
                    mList.add(actor);
                }


                for (int child=0; child<GlobalArray.mSelect_Filter.size(); child++)
                {

                    String cateId= GlobalArray.mSelect_Filter.get(child);
                    for (int indx=0; indx<mList.size(); indx++)
                    {

                        String mCate= mList.get(indx).getCategoryId();
                        if(mCate.equalsIgnoreCase(cateId))
                        {

                            mList.get(indx).setCheck(true);
                            mRent_layout.setVisibility(View.VISIBLE);
                        }


                    }

                }


                mAdapter.notifyDataSetChanged();
            }
            else
            {


            }

        }
        catch (Exception e)
        {
            Log.e(TAG, "=========ERRORR========="+e.getMessage());
        }

    }

    private void ResetFilter()
    {

        TypedArray blueImage = getResources().obtainTypedArray(R.array.Blue_image);
        TypedArray redImages = getResources().obtainTypedArray(R.array.Red_image);
        mName = Arrays.asList(getResources().getStringArray(R.array.Filter_type));
        GlobalArray.mSelect_Filter.clear();

        for (int indx=0; indx<mList.size(); indx++)
        {

            mList.get(indx).setTextColor("Black");
            mList.get(indx).setCheck(false);


        }
        mAdapter.notifyDataSetChanged();

        et_Maximum.setText("");
        et_Minimum.setText("");

        Rd_latest.setChecked(false);
        Rd_Oldest.setChecked(false);
        Rd_LowtoHigh.setChecked(false);
        Rd_HightoLow.setChecked(false);
        mRent_layout.setVisibility(View.GONE);



    }
    public void setCheck(boolean isChecked, FilterModal actor, int mposition)
    {



        if(isChecked)
        {


            if(GlobalArray.mSelect_Filter.contains(actor.getCategoryId()))
            {

            }
            else
            {

                GlobalArray.mSelect_Filter.clear();
                GlobalArray.mSelect_Filter.add(actor.getCategoryId());
            }

            if(actor.getEng_title().contains("Real estate"))
            {


                mRent_layout.setVisibility(View.VISIBLE);
            }

            actor.setCheck(isChecked);

          for (int indx=0; indx<mList.size(); indx++)
          {

              if(indx==mposition)
              {

                  mList.get(indx).setCheck(true);
              }
              else
              {
                  mList.get(indx).setCheck(false);
              }

          }

          mAdapter.setmList(mList);


        }
        else
        {
            Object mb= actor.getCategoryId();

            GlobalArray.mSelect_Filter.remove(mb);
            if(actor.getCategoryId().equalsIgnoreCase("1"))
            {


                mRent_layout.setVisibility(View.GONE);
            }
        }

        actor.setCheck(isChecked);


    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int id=group.getCheckedRadioButtonId();
        switch (id)
        {

            case R.id.Rd_latest:

                showErrorr("Latest");
                stSortBy="Latest";

                break;
            case R.id.Rd_Oldest:
                showErrorr("Oldest");
                stSortBy="Oldest";

                break;
            case R.id.Rd_LowtoHigh:
                showErrorr("LowtoHigh");
                stSortBy="LowtoHigh";
                break;
            case R.id.Rd_HightoLow:
                showErrorr("HightoLow");
                stSortBy="HightoLow";
                break;
            case R.id.RB_Buy:
                showErrorr("BUY");
                stRentType="1";
                break;
            case R.id.RB_rent:
                showErrorr("Rent");
                stRentType="2";
                break;
        }
    }
}
