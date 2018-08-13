package com.kalshee;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.kalshee.adapter.ViewPagerAdapter;
import com.kalshee.fragment.AccountFragment;
import com.kalshee.fragment.ChatListFragment;
import com.kalshee.fragment.DashboardFragment;
import com.kalshee.fragment.HomeFragment;
import com.kalshee.fragment.PostAOfferFragment;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity
{

    private String TAG="MainActivity2";
    private AHBottomNavigationAdapter navigationAdapter;
    private int[] tabColors;
    // UI
    public static AHBottomNavigationViewPager viewPager;
   public static AHBottomNavigation bottomNavigation;
   ViewPagerAdapter mViewPagerAdapter;
   public  static   MainActivity2 mainActivity2;
   public static boolean mOpen=false;

   ArrayList<Fragment> mList= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity2= this;




        viewPager();
        UI();


    }
    @Override
    protected void onStart()
    {
        super.onStart();
        mOpen= true;
        // Check if user is signed in (non-null) and update UI accordingly.



    }
    private void viewPager()
    {

        mList.add(new HomeFragment());
        mList.add(new DashboardFragment());
        mList.add(new PostAOfferFragment());
        mList.add(new ChatListFragment());
        mList.add(new AccountFragment());

        viewPager = (AHBottomNavigationViewPager) findViewById(R.id.view_pager);
        mViewPagerAdapter= new ViewPagerAdapter(getSupportFragmentManager(), mList);

            viewPager.setAdapter(mViewPagerAdapter);
            viewPager.setCurrentItem(0);



    }
    public void UI()
    {

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        tabColors = getApplicationContext().getResources().getIntArray(R.array.tab_colors);
        navigationAdapter = new AHBottomNavigationAdapter(this,R.menu.bottom_navigation_menu_5);
        navigationAdapter.setupWithBottomNavigation(bottomNavigation, tabColors);
        // Set background color
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#0E6AB3"));
        // Disable the translation inside the CoordinatorLayout
        bottomNavigation.setBehaviorTranslationEnabled(false);
        // Change colors
        bottomNavigation.setAccentColor(Color.parseColor("#ffffff"));
        bottomNavigation.setInactiveColor(Color.parseColor("#70ffffff"));
        // Force to tint the drawable (useful for font with icon for example)
        bottomNavigation.setForceTint(true);
        bottomNavigation.setTranslucentNavigationEnabled(true);
        // Manage titles
        //  bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        //bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);
        // Use colored navigation with circle reveal effect
        // bottomNavigation.setColored(true);

        // Set current item programmatically
        bottomNavigation.setCurrentItem(0);

        // Customize notification (title, background, typeface)
        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));


        // OR
        AHNotification notification = new AHNotification.Builder()
                .setText("1")
                // .setBackgroundColor(ContextCompat.getColor(DemoActivity.this, R.color.color_notification_back))
                //  .setTextColor(ContextCompat.getColor(DemoActivity.this, R.color.color_notification_text))
                .build();
       // bottomNavigation.setNotification(notification, 0);
        // Enable / disable item & set disable color
        bottomNavigation.enableItemAtPosition(0);
        // bottomNavigation.disableItemAtPosition(1);
        bottomNavigation.setItemDisableColor(Color.parseColor("#3A000000"));

// Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener()
        {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected)
            {
                viewPager.setCurrentItem(position);
                // Do something cool here...
                return true;
            }
        });
        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener()
        {
            @Override
            public void onPositionChange(int y) {
                // Manage the new y position
            }
        });


      //  showNotification(3, "56");

    }




}
