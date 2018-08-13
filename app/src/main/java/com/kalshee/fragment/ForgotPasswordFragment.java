package com.kalshee.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.kalshee.LoginActivity;
import com.kalshee.R;
import com.kalshee.userData.UserData;
import com.kalshee.utill.NetworkClass;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by eWeb_A1 on 6/9/2018.
 */

public class ForgotPasswordFragment extends Fragment implements View.OnClickListener
{

    ImageButton ibBack;
    EditText etEmail;
    Button bt_submit;
    Fragment mFragment;
    ProgressDialog mProgressDialog;
    String TAG="ForgotPasswordFragment";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View mView= inflater.inflate(R.layout.fragment_forgot_password, container, false);

        XML(mView);
        return mView;
    }

    private void XML(View view)
    {

        ibBack=(ImageButton)view.findViewById(R.id.ibBack);
        ibBack.setOnClickListener(this);

        etEmail=(EditText)view.findViewById(R.id.etEmail);
        etEmail.requestFocus();
        bt_submit=(Button)view.findViewById(R.id.bt_submit);
        bt_submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {

            case R.id.ibBack:
                LoginActivity.mLoginActivity.onBackPressed();
                break;

            case R.id.bt_submit:

                if(validation())
                {

                    hideKeyboard(v);
                    hitAPI();

                }
                break;

        }
    }


private boolean validation()
{

    if(etEmail.getText().toString().trim().length()==0)
    {

        etEmail.requestFocus();
        etEmail.setError(getResources().getString(R.string.checkemail));

        return false;
    }
    else if(!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString().trim()).matches())
    {
        etEmail.requestFocus();
        etEmail.setError(getResources().getString(R.string.checkemailvalidation));
        return false;

    }


    return true;
}


private void hitAPI()
{

    AsyncHttpClient mAsyncHttpClient= new AsyncHttpClient();
    RequestParams mParams= new RequestParams();
    mParams.add("email",etEmail.getText().toString().trim() );


    String mURL= NetworkClass.BASE_URL+NetworkClass.FORGOT_PASSWORD;
    Log.e(TAG, "===============PARMS======"+mParams);

    mAsyncHttpClient.post(mURL, mParams, new JsonHttpResponseHandler()
    {


        @Override
        public void onStart() {
            super.onStart();

            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(getString(R.string.Pleasewait));
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);

            mProgressDialog.dismiss();
            Log.e(TAG, "==========response===="+response);

            if(statusCode==200)
            {


                try
                {

                    String code=response.getString("code");
                    String message= response.getString("message");
                    if(code.equalsIgnoreCase("201"))
                    {


                        UserData.setEMAIL(getActivity(), etEmail.getText().toString().trim());
                        showErrorr(message);
                        mFragment= new ResetPasswordFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mContainer, mFragment).addToBackStack("").commit();


                    }
                    else
                    {

                        showErrorr(message);
                    }



                }
                catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "============ERRORR========="+e.getMessage());
                }
            }

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);

            Log.e(TAG, "=================ERRORR=========="+responseString);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);

            Log.e(TAG, "=================ERRORR=========="+errorResponse);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);

            Log.e(TAG, "=================ERRORR=========="+errorResponse);
        }

        @Override
        public void onFinish() {
            super.onFinish();
            mProgressDialog.dismiss();
        }
    });

}
    private void showErrorr(String msg) {

        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

    }

    private void hideKeyboard(View mView) {


        InputMethodManager manager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(mView.getWindowToken(), 0);
    }
}
