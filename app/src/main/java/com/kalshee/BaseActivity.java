package com.kalshee;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kalshee.fragment.ChatFragment;
import com.kalshee.fragment.SellerProfileFragment;
import com.kalshee.pojo.ProductDataPojo;

public class BaseActivity extends AppCompatActivity {


    Fragment mFragment=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);


        display(getIntent().getStringExtra("NAME"));

    }

    private void display(String msg)
    {


        switch (msg)
        {

            case "SELLER_PROFILE":
                mFragment= new  SellerProfileFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.mContainer, mFragment).commit();
                break;

            case "CHAT":
               /* String mAdd_UserId=getIntent().getStringExtra("ADD_USER_ID");
                String ADD_ID= getIntent().getStringExtra("ADD_ID");
                String Pname=getIntent().getStringExtra("TITLE");
                String DEVICE_TOKEN= getIntent().getStringExtra("DEVICE_TOKEN");
                String SENDER_ID= getIntent().getStringExtra("SENDER_ID");
                String PRICE= getIntent().getStringExtra("PRICE");
                String PRODUCT_PICTURE= getIntent().getStringExtra("PRODUCT_PICTURE");
                String ADD_USER_ID =getIntent().getStringExtra("ADD_USER_ID");
                 Bundle mBundle= new Bundle();
                mBundle.putString("ADD_USER_ID", mAdd_UserId);
                mBundle.putString("ADD_ID",ADD_ID );
                mBundle.putString("P_NAME",Pname);
                mBundle.putString("DEVICE_TOKEN",DEVICE_TOKEN);
                mBundle.putString("SENDER_ID", SENDER_ID);
                mBundle.putString("PRICE", PRICE);
                mBundle.putString("PRODUCT_PICTURE",PRODUCT_PICTURE);
                */


                ProductDataPojo actor= (ProductDataPojo) getIntent().getSerializableExtra("DATA");
                mFragment= new ChatFragment();
                Bundle mBundle= new Bundle();
                mBundle.putSerializable("DATA", actor);
                mFragment.setArguments(mBundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.mContainer, mFragment).commit();

                break;
        }


    }

}
