package com.kalshee.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.kalshee.BuildConfig;
import com.kalshee.LoginActivity;
import com.kalshee.MainActivity;
import com.kalshee.R;
import com.kalshee.modal.SignInImple;
import com.kalshee.userData.UserData;
import com.kalshee.utill.NetworkClass;
import com.kalshee.utill.Utility;
import com.kalshee.view.SigninView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by eWeb_A1 on 6/8/2018.
 */

public class SignUpFragment extends Fragment implements View.OnClickListener ,SigninView
{

    RelativeLayout mTool_Relative;
    ImageButton ibBack;
    EditText etEmail, etName, etPassword;
    Button bt_Signup;
    ProgressDialog mProgressDialog;
    String TAG="SignUpFragment";
    TextView tv_PrivacyPolicy,tvHide;
    private boolean mShow= false;
    Button bt_facebookSignUp;
    CallbackManager callbackManager;
    SignInImple mSignInImple;
    String mDeviceID="";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_signup, container, false);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getActivity());

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( getActivity(),  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                mDeviceID = instanceIdResult.getToken();
                Log.e("newToken",mDeviceID);

            }
        });

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                LoginManager.getInstance().logOut();
                Log.e(TAG, "=============onSuccess==================");
                GraphRequest request=GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        try {
                            String email = response.getJSONObject().getString("email");
                            String firstName = response.getJSONObject().getString("first_name");
                            String lastName = response.getJSONObject().getString("last_name");

                            etEmail.setText(email);
                            etName.setText(firstName+" "+lastName);

                            etPassword.requestFocus();
                        }
                        catch (Exception e)
                        {
                            Log.e(TAG, "=============ERRORR==========="+e.getMessage());
                        }



                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,email,first_name,last_name,gender,picture.type(large)");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {

                Log.e(TAG, "==========onCancel==============");

            }

            @Override
            public void onError(FacebookException error) {

                Log.e(TAG, "=========onError=========");
            }
        });



        mSignInImple= new SignInImple(getActivity(), SignUpFragment.this);
        XML(mView);
        return mView;
    }

    private void XML(View view)
    {
        bt_facebookSignUp=(Button)view.findViewById(R.id.bt_facebookSignUp);
        tvHide=(TextView)view.findViewById(R.id.tvHide);
        tvHide.setOnClickListener(this);

          mTool_Relative=(RelativeLayout)view.findViewById(R.id.mTool_Relative);
          ibBack=(ImageButton)view.findViewById(R.id.ibBack);
          ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                LoginActivity.mLoginActivity.onBackPressed();
            }
        });

          etEmail=(EditText)view.findViewById(R.id.etEmail);
          etEmail.requestFocus();
          etName=(EditText)view.findViewById(R.id.etName);
         etPassword=(EditText)view.findViewById(R.id.etPassword);

        bt_Signup=(Button)view.findViewById(R.id.bt_Signup);
        bt_Signup.setOnClickListener(this);



        tv_PrivacyPolicy=(TextView)view.findViewById(R.id.tv_PrivacyPolicy);

        int mVersion= Build.VERSION.SDK_INT;

        if(mVersion >21)
        {

            tv_PrivacyPolicy.setText(getResources().getString(R.string.terms_of_service_and_privacy_policy));
        }
        else
        {
            tv_PrivacyPolicy.setTextColor(getResources().getColor(R.color.colorWhite));
            tv_PrivacyPolicy.setText("Terms of Service and Privacy Policy");

        }

        bt_facebookSignUp.setOnClickListener(this);

    }
    private boolean validation()
    {

        if(etEmail.getText().toString().trim().length()==0)
        {
            etEmail.requestFocus();
            etEmail.setError(getResources().getString(R.string.checkemail));

            return  false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString().trim()).matches())
        {

            etEmail.requestFocus();
            etEmail.setError(getString(R.string.checkemailvalidation));
            return false;
        }
        else if(etName.getText().toString().trim().length()==0)
        {

            etEmail.clearFocus();
            etName.requestFocus();
            etName.setError(getString(R.string.enterName));

            return  false;
        }
        else if(etPassword.getText().toString().trim().length()==0)
        {

            etName.clearFocus();
            etPassword.requestFocus();
            etPassword.setError(getString(R.string.pleaseenterPassword));
            return  false;
        }


        return  true;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {


            case R.id.bt_facebookSignUp:

                LoginManager.getInstance().logInWithReadPermissions(SignUpFragment.this, Arrays.asList("email"));

                break;
            case R.id.bt_Signup:

                if(validation())
                {

                    hideKeyboard(v);
                    if(Utility.checkInternet(getActivity()))
                    {
                        mSignInImple.setInput(etEmail.getText().toString(), etName.getText().toString(), etPassword.getText().toString(), mDeviceID);
                    }
                    else
                    {
                        showErrorr("Please check your internet connection");
                    }

                }
                break;

            case R.id.tvHide:

                if(mShow)
                {
                    tvHide.setText(getString(R.string.hide));

                    mShow=false;
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    if(etPassword.getText().length()==0)
                    {

                    }
                    else
                    {
                        etPassword.setSelection(etPassword.getText().length());
                    }


                }
                else
                {

                    tvHide.setText(R.string.show);
                    mShow=true;
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    if(etPassword.getText().length()==0)
                    {

                    }
                    else
                    {
                        etPassword.setSelection(etPassword.getText().length());
                    }
                }
                break;
        }
    }



    @Override
    public void showSuccess(String response) {


        Log.e(TAG, "===============RESPONSE============"+response);

        try
        {
            JSONObject mJsonObject= new JSONObject(response);

            String code= mJsonObject.getString("code");
            String message=mJsonObject.getString("message");

            if(code.equalsIgnoreCase("201"))
            {

                String name= mJsonObject.getString("name");
                String email= mJsonObject.getString("email");
                String user_id= mJsonObject.getString("user_id");



                UserData.setUSER_id(getContext(), user_id);


                            /*Intent mIntent = new Intent(getActivity(), MainActivity.class);
                            startActivity(mIntent);
                            getActivity().finish();*/

                showAlert();



            }
            else if(message.contains("The email field must contain a unique value."))
            {


                showErrorr(getString(R.string.ThisEmailisalreadyexits));

            }
            else
            {

                showErrorr(message);
            }




        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void showErrorr(String msg)
    {

        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showProgressBar() {
        mProgressDialog= new ProgressDialog(getActivity());
        mProgressDialog.setMessage(getString(R.string.Pleasewait));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void hideProgressBar() {
mProgressDialog.dismiss();
    }

    @Override
    public void showFacebookResponse(String response) {

    }

    private void hideKeyboard(View mView)
    {
        InputMethodManager manager= (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(mView.getWindowToken(), 0);
    }


    private void showAlert()
    {


        final AlertDialog.Builder mBuilder= new AlertDialog.Builder(getActivity());
        mBuilder.setMessage(getResources().getString(R.string.sendLinkMail));
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();
                LoginActivity.mLoginActivity.onBackPressed();
            }
        });

        mBuilder.show();



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
