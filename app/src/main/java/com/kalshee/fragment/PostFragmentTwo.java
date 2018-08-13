package com.kalshee.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kalshee.R;
import com.kalshee.adapter.CategoryAdapter;
import com.kalshee.pojo.BasePojo;
import com.kalshee.pojo.CategoryModal;
import com.kalshee.userData.UserData;
import com.kalshee.utill.NetworkClass;
import com.kalshee.utill.Utility;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by eWeb_A1 on 6/12/2018.
 */

public class PostFragmentTwo extends Fragment implements View.OnClickListener {

    Button bt_Next;
    ImageButton iv_back;
    Fragment mFragment;
    Spinner mCategory_spinner;
    String TAG = "PostFragmentTwo";
    ArrayList<CategoryModal> mSpinner_List = new ArrayList<>();
    CategoryAdapter mSpinnerAdapter;
    ProgressDialog mProgressDialog;
    EditText et_description;
    int mSpinnerPostion=0;
    RadioGroup Rd_itemType,RD_propertyType;
    RadioButton RB_new, RB_used, RB_rent, RB_Buy;
    LinearLayout mProperty_linear;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View mView = inflater.inflate(R.layout.fragment_post_two, container, false);

        UserData.setItemType(getActivity(), "");
        UserData.setPropertyType(getContext(), "");
        XML(mView);
        if (Utility.checkInternet(getContext())) {

            getCategory();
        }
        return mView;
    }

    private void XML(View view) {

        mProperty_linear=(LinearLayout)view.findViewById(R.id.mProperty_linear);
        bt_Next = (Button) view.findViewById(R.id.bt_Next);
        bt_Next.setOnClickListener(this);

        iv_back = (ImageButton) view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);


        et_description=(EditText)view.findViewById(R.id.et_description);
        mCategory_spinner = (Spinner) view.findViewById(R.id.mCategory_spinner);
        mCategory_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                if(position==0)
                {
                    mSpinnerPostion=0;

                }
                else
                {

                    if(mSpinner_List.get(position).getCategory_id().equalsIgnoreCase("1"))
                    {

                        mProperty_linear.setVisibility(View.VISIBLE);
                        UserData.setCategoryId(getActivity(), mSpinner_List.get(position).getCategory_id());
                        mSpinnerPostion= position;
                    }
                    else
                    {
                        mProperty_linear.setVisibility(View.GONE);
                        UserData.setCategoryId(getActivity(), mSpinner_List.get(position).getCategory_id());
                        mSpinnerPostion= position;
                    }


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Rd_itemType=(RadioGroup)view.findViewById(R.id.Rd_itemType);
        RB_new=(RadioButton)view.findViewById(R.id.RB_new);
        RB_used=(RadioButton)view.findViewById(R.id.RB_used);

        Rd_itemType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                int id= checkedId;
                switch (id)
                {

                    case R.id.RB_new:

                        RB_new.setChecked(true);
                        UserData.setItemType(getContext(), "1");
                        break;

                    case R.id.RB_used:
                        UserData.setItemType(getContext(), "2");
                        RB_used.setChecked(true);
                        break;
                }

            }
        });
        RD_propertyType=(RadioGroup)view.findViewById(R.id.RD_propertyType);
        RB_Buy=(RadioButton)view.findViewById(R.id.RB_Buy);
        RB_rent=(RadioButton)view.findViewById(R.id.RB_used);

        RD_propertyType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                int id= checkedId;
                switch (id)
                {

                    case R.id.RB_Buy:

                        RB_Buy.setChecked(true);
                        UserData.setPropertyType(getContext(), "1");
                        break;

                    case R.id.RB_rent:
                        UserData.setPropertyType(getContext(), "2");
                        RB_rent.setChecked(true);
                        break;
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_back:
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;

            case R.id.bt_Next:
                if(validation())
                {

                    UserData.setDESCRIPTION(getActivity(), et_description.getText().toString().trim());
                    mFragment = new PostThreeFragment();
                    getFragmentManager().beginTransaction().add(R.id.mPost_Container, mFragment).addToBackStack("").commit();


                }
                break;
        }
    }

    private void getCategory() {

        AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();
        mAsyncHttpClient.setTimeout(90000);
        mAsyncHttpClient.setResponseTimeout(90000);
        mAsyncHttpClient.setConnectTimeout(90000);

        String mUrl = NetworkClass.BASE_URL + NetworkClass.GET_ALL_CATEGORY;
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
                    BasePojo<ArrayList<CategoryModal>> mPojo = mGson.fromJson(response.toString(), new TypeToken<BasePojo<ArrayList<CategoryModal>>>() {
                    }.getType());
                    mSpinner_List = mPojo.getData();

                    CategoryModal actor = new CategoryModal();
                    actor.setEng_title("Select Category");

                    mSpinner_List.add(0, actor);
                    mSpinnerAdapter = new CategoryAdapter(getActivity(), mSpinner_List);
                    mCategory_spinner.setAdapter(mSpinnerAdapter);
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

    private boolean validation()
    {

        if(mSpinnerPostion==0)
        {

            showaERRor(getString(R.string.PleaseSelect_Category));
            return  false;
        }
        else if(et_description.getText().toString().trim().length()==0)
        {

            showaERRor(getString(R.string.PleaseenterDescription));
            return  false;
        }
        else if(UserData.getItemType(getActivity()).equalsIgnoreCase(""))
        {

            showaERRor(getString(R.string.pleaseselectItemType));
            return false;
        }
        else
        {
            return true;
        }



    }


    private void showaERRor(String msg)
    {


        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
