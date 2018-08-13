package com.kalshee;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by eWeb_A1 on 12/1/2017.
 */

public class TimeShow
{

    private static String mTimeago;

    public static String showTime(String mTime)
    {



        try
        {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date past = format.parse(mTime);
            Date now = new Date();
            long seconds= TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
            long minutes= TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
            long hours= TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
            long days= TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());

            if(seconds<1)
            {
                System.out.println(seconds+" seconds ago");
               // mTimeago=seconds+" seconds ago";
                mTimeago= "Just now";


            }
            else if(seconds<60)
            {

                mTimeago=""+seconds+" seconds ago";
            }
            else if(minutes <1)
            {
                System.out.println(minutes+" minutes ago");
                mTimeago=minutes+" minutes ago";

            }

            else if(minutes<60)
            {
                System.out.println(minutes+" minutes ago");
                mTimeago=minutes+" minutes ago";

            }
            else if(hours<24)
            {
                System.out.println(hours+" hours ago");

                mTimeago=hours+"  hours ago";
            }
            else
            {
                System.out.println(days+" days ago");

                mTimeago=days+" days ago";
            }
        }
        catch (Exception j){
            j.printStackTrace();
            Log.e("", "========ERRORR========"+j.getMessage());
        }


        return  mTimeago;
    }



    public static void main(String[] args)
    {


        try
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss");
            Date past = format.parse("2016.02.05 AD at 23:59:30");
            Date now = new Date();
            long seconds= TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
            long minutes= TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
            long hours= TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
            long days= TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());


            if(seconds<60)
            {
                System.out.println(seconds+" seconds ago");
            }
            else if(minutes<60)
            {
                System.out.println(minutes+" minutes ago");
            }
            else if(hours<24)
            {
                System.out.println(hours+" hours ago");
            }
            else
            {
                System.out.println(days+" days ago");
            }
        }
        catch (Exception j){
            j.printStackTrace();
        }
    }
}