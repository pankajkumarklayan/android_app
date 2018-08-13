package com.kalshee;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
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
import com.kalshee.fragment.ForgotPasswordFragment;
import com.kalshee.fragment.SignUpFragment;
import com.kalshee.modal.LoginImple;
import com.kalshee.userData.UserData;
import com.kalshee.utill.NetworkClass;
import com.kalshee.utill.Utility;
import com.kalshee.view.LoginView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener ,LoginView{


    Fragment mFragment;
    public static LoginActivity mLoginActivity;
    EditText etEmail, etPassword;
    Button bt_login, bt_facebookLogin;
    TextView tv_ForgotPassword, tv_CreateAccount;
    String TAG = "LoginActivity";
    ProgressDialog mProgressDialog;
    LoginImple mLoginImple;
   CallbackManager callbackManager;
   String mDeviceID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginActivity = this;

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( LoginActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                mDeviceID = instanceIdResult.getToken();
                Log.e("newToken",mDeviceID);

            }
        });


        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

      //  String hashKey= printKeyHash(LoginActivity.this);
      //  Log.e(TAG, "=======hashKey====="+hashKey);
        mLoginImple= new LoginImple(LoginActivity.this, this);

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>()
                {
                    @Override
                    public void onSuccess(LoginResult loginResult)
                    {

                        LoginManager.getInstance().logOut();

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        // Application code
                                        try {

                                            LoginManager.getInstance().logOut();

                                            Log.i("Response", response.toString());

                                            try {
                                                String profilePicUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");

                                            }
                                            catch (Exception e)
                                            {

                                            }


                                            String email = response.getJSONObject().getString("email");
                                            String firstName = response.getJSONObject().getString("first_name");
                                            String lastName = response.getJSONObject().getString("last_name");


                                            mLoginImple.facebookInput(email, firstName,mDeviceID);

                                        } catch (JSONException e) {
                                            e.printStackTrace();

                                            Log.e(TAG, "============================FACEBOOK====Errrorr==================" + e.getMessage());
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
                        // App code
                    Log.e(TAG, "=====onCancel========");
                    showMessage("OnCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        showMessage("onError");
                        Log.e(TAG, "==========FACEBOOKERRORR========"+exception);
                    }
                });

        XML();
    }

    private void XML() {

        tv_CreateAccount = (TextView) findViewById(R.id.tv_CreateAccount);
        tv_CreateAccount.setOnClickListener(this);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_login.setOnClickListener(this);


        tv_ForgotPassword = (TextView) findViewById(R.id.tv_ForgotPassword);

        bt_facebookLogin=(Button)findViewById(R.id.bt_facebookLogin);




        tv_ForgotPassword.setOnClickListener(this);
        bt_facebookLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.bt_facebookLogin:
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("basic_info","email"));
                break;

            case R.id.tv_CreateAccount:
                mFragment = new SignUpFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.mContainer, mFragment).addToBackStack("").commit();
                break;
            case R.id.bt_login:

                if (validtaion()) {


                    hideKeyboard(v);



                    if(Utility.checkInternet(LoginActivity.this))
                    {
                        mLoginImple.setInput(etEmail.getText().toString(), etPassword.getText().toString(), mDeviceID);
                    }
                    else
                    {

                        Toast.makeText(LoginActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }


                }
                break;
            case R.id.tv_ForgotPassword:
                mFragment = new ForgotPasswordFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.mContainer, mFragment).addToBackStack("").commit();
                break;

        }
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 1) {

            super.onBackPressed();
        } else {
            super.onBackPressed();
        }

    }

    private boolean validtaion() {


        if (etEmail.getText().toString().trim().length() == 0) {

            etEmail.requestFocus();
            etEmail.setError(getString(R.string.checkemail));

            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString().trim()).matches()) {

            etEmail.requestFocus();
            etEmail.setError(getString(R.string.checkemailvalidation));
            return false;

        } else if (etPassword.getText().toString().trim().length() == 0) {

            etPassword.requestFocus();
            etPassword.setError(getString(R.string.checkpassword));
            return false;
        }

        return true;
    }



    private void showMessage(String msg) {

        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();

    }

    private void hideKeyboard(View mView) {


        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(mView.getWindowToken(), 0);
    }

    private void showAlert()
    {


        final AlertDialog.Builder mBuilder= new AlertDialog.Builder(LoginActivity.this);
        mBuilder.setMessage(getResources().getString(R.string.AccountnotActivite));
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();
            }
        });

        mBuilder.show();



    }

    @Override
    public void showSuccess(String response) {


        try {

            JSONObject mJsonObject= new JSONObject(response);
            String code = mJsonObject.getString("code");
            String message = mJsonObject.getString("message");

            if (code.equalsIgnoreCase("201")) {

                String name = mJsonObject.getString("name");
                String email = mJsonObject.getString("email");
                String user_id = mJsonObject.getString("user_id");
                String status=mJsonObject.getString("status");
                String profile_image= mJsonObject.getString("profile_image");


                String created_date = mJsonObject.getString("created_date");
                SimpleDateFormat mInput= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  //2018-06-18 19:48:24
                Date mDate= null;
                SimpleDateFormat mOutPut=new SimpleDateFormat("MMM-yyyy");

                try {

                    mDate= mInput.parse(created_date);

                }
                catch (Exception e)
                {
                    Log.e(TAG, "===========ERRORR======"+e.getMessage());
                }
                String JoinDate= mOutPut.format(mDate);


                UserData.setProfilePic(LoginActivity.this, profile_image);
                UserData.setJoinDate(LoginActivity.this, JoinDate);
                UserData.setCheckStatus(LoginActivity.this, status);
                if(status.equalsIgnoreCase("0"))
                {


                    showAlert();

                }
                else
                {

                    showErrorr(message);
                    UserData.setEMAIL(LoginActivity.this, email);
                    UserData.setNAME(LoginActivity.this, name);
                    UserData.setUSER_id(LoginActivity.this, user_id);

                    Intent mIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mIntent);
                    finish();
                }




            }
            else if (message.contains("The email field must contain a unique value.")) {


                showMessage(getString(R.string.ThisEmailisalreadyexits));

            } else {

                showMessage(message);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showErrorr(String error) {

    }

    @Override
    public void showProgressBar() {
        mProgressDialog = new ProgressDialog(LoginActivity.this);
        mProgressDialog.setMessage(getString(R.string.Pleasewait));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void hideProgressBar() {

        mProgressDialog.dismiss();
    }

    @Override
    public void showValidation(String response) {

    }

    @Override
    public void showFacebookResponse(String response) {


        Log.e(TAG, "========FACEBOOK====RESPONSE======="+response);

        try {

            JSONObject mJsonObject= new JSONObject(response);
            String code= mJsonObject.getString("code");
            String message=mJsonObject.getString("message");
            if(code.equalsIgnoreCase("201"))
            {
                String user_id=mJsonObject.getString("user_id");
                String name=mJsonObject.getString("name");
                String email=mJsonObject.getString("email");
               String  profile_image= mJsonObject.getString("profile_image");
               String created_date= mJsonObject.getString("created_date");

                SimpleDateFormat mInput= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  //2018-06-18 19:48:24
                Date mDate= null;
                SimpleDateFormat mOutPut=new SimpleDateFormat("MMM-yyyy");


                try {

                    mDate= mInput.parse(created_date);

                }
                catch (Exception e)
                {
                    Log.e(TAG, "===========ERRORR======"+e.getMessage());
                }
                String JoinDate= mOutPut.format(mDate);


                UserData.setProfilePic(LoginActivity.this, profile_image);
                UserData.setJoinDate(LoginActivity.this, JoinDate);
                UserData.setCheckStatus(LoginActivity.this, "1");


                showErrorr(message);
                UserData.setEMAIL(LoginActivity.this, email);
                UserData.setNAME(LoginActivity.this, name);
                UserData.setUSER_id(LoginActivity.this, user_id);

                Intent mIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(mIntent);
                finish();
            }
            else
            {

                showMessage(message);
            }


        }
        catch (Exception e)
        {
            Log.e(TAG, "===========ERRORR========="+e.getMessage());
        }
    }
    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
