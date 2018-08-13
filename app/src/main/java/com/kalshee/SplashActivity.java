package com.kalshee;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.kalshee.fcm.MyFirebaseMessagingService;
import com.kalshee.userData.UserData;

public class SplashActivity extends AppCompatActivity
{

    ImageView ivImage;
    Animation mAnimation;

    String mDeviceID="";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( SplashActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                mDeviceID = instanceIdResult.getToken();
                Log.e("newToken",mDeviceID);

                UserData.setDEVICE_id(SplashActivity.this, mDeviceID);


            }
        });
        XML();


    }
    private void XML()
    {

        ivImage=(ImageView)findViewById(R.id.ivImage);
        mAnimation= AnimationUtils.loadAnimation(SplashActivity.this, R.anim.splash);
        ivImage.setAnimation(mAnimation);
        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation)
            {


            }
            @Override
            public void onAnimationEnd(Animation animation)
            {

                String mStatus= UserData.getCheckStatus(SplashActivity.this);

                if(UserData.getUserID(SplashActivity.this).equalsIgnoreCase(""))
                {
                    Intent mIntent= new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(mIntent);
                    finish();

                }
                else if(mStatus.equalsIgnoreCase("")|| mStatus.equalsIgnoreCase("0"))
                {
                    Intent mIntent= new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(mIntent);
                    finish();
                }
                else
                {
                    Intent mIntent= new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(mIntent);
                    finish();

                }




            }
            @Override
            public void onAnimationRepeat(Animation animation)
            {


            }
        });
    }
}
