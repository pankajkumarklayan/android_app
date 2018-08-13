package com.kalshee.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kalshee.fragment.AccountFragment;
import com.kalshee.fragment.ChatFragment;
import com.kalshee.fragment.ChatListFragment;
import com.kalshee.fragment.DashboardFragment;
import com.kalshee.fragment.HomeFragment;
import com.kalshee.fragment.PostAOfferFragment;

import java.util.ArrayList;

/**
 * Created by eWeb_A1 on 6/9/2018.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter
{

    ArrayList <Fragment> mList;

    public ViewPagerAdapter(FragmentManager fm,ArrayList <Fragment> list)
    {
        super(fm);
        this.mList= list;




    }

    @Override
    public Fragment getItem(int position)
    {
        if(position==0)
        {

            return  new HomeFragment();

        }
        else if(position ==1)
        {
            return new DashboardFragment();

        }
        else if(position==2)
        {

            return  new PostAOfferFragment();
        }
        else if(position==3)
        {

            return  new ChatListFragment();
        }
        else if(position==4)
        {

            return  new AccountFragment();
        }

        return null;
    }



    @Override
    public int getCount() {
        return mList.size();
    }
}
