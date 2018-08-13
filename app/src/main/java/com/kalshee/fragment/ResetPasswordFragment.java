package com.kalshee.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
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

public class ResetPasswordFragment extends Fragment implements View.OnClickListener {

    ImageButton ibBack;
    EditText et_otp, et_newPassword, et_ConPassword;
    Button bt_submit;
    String TAG="ResetPasswordFragment";
    ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_resetpassword, container, false);

        XML(mView);
        return mView;
    }

    private void XML(View view) {
        ibBack = (ImageButton) view.findViewById(R.id.ibBack);
        ibBack.setOnClickListener(this);

        et_newPassword = (EditText) view.findViewById(R.id.et_newPassword);
        et_ConPassword = (EditText) view.findViewById(R.id.et_ConPassword);
        et_otp = (EditText) view.findViewById(R.id.et_otp);
        et_otp.requestFocus();
        bt_submit = (Button) view.findViewById(R.id.bt_submit);
        bt_submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

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

    private boolean validation() {

        if (et_otp.getText().toString().trim().length() == 0) {

            et_otp.requestFocus();
            et_otp.setError(getString(R.string.PleaseenterOTp));
            return false;
        }
        else if (et_newPassword.getText().toString().trim().length() == 0)
        {

            et_otp.clearFocus();
            et_newPassword.requestFocus();
            et_newPassword.setError(getString(R.string.PleaseenterNewpassword));
            return false;
        } else if (et_ConPassword.getText().toString().trim().length() == 0) {


            et_ConPassword.requestFocus();
            et_ConPassword.setError(getString(R.string.Confirmpassword));

            return false;
        }

        return true;
    }


    private void hitAPI()
    {


        AsyncHttpClient mAsyncHttpClient= new AsyncHttpClient();
        RequestParams mParams= new RequestParams();
        mParams.add("email", UserData.getEMAIL(getActivity()));
        mParams.add("otp", et_otp.getText().toString().trim());
        mParams.add("password", et_ConPassword.getText().toString().trim());

        String mURL= NetworkClass.BASE_URL+NetworkClass.RESET_PASSWORD;
        Log.e(TAG, "==============URL========="+mURL);
        Log.e(TAG, "==============PARMS======"+mParams);
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

                Log.e(TAG, "==========response=========="+response);


                if(statusCode==200)
                {


                    try
                    {
                        String code=response.getString("code");
                          String message=response.getString("message");

                          if(code.equalsIgnoreCase("201"))
                          {
                              showErrorr(message);


                              getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                          }
                          else
                          {
                              showErrorr(message);

                          }


                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

                mProgressDialog.dismiss();

               Log.e(TAG, "================ERRORR=============="+responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Log.e(TAG, "================ERRORR=============="+errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Log.e(TAG, "================ERRORR=============="+errorResponse);
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
