package com.kalshee.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kalshee.R;

import java.util.ArrayList;

/**
 * Created by eWeb_A1 on 6/11/2018.
 */

public class Seller_Sold_Adapter extends RecyclerView.Adapter<Seller_Sold_Adapter.MyViewHolder>
{

    private Context mContext;
    private ArrayList<Integer>mList;

    public Seller_Sold_Adapter(Context context, ArrayList<Integer>list)
    {


        this.mContext= context;
        this.mList= list;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView= LayoutInflater.from(mContext).inflate(R.layout.adapter_seller_selling_layout, parent, false);

        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        DisplayMetrics lDisplayMetrics = mContext.getResources().getDisplayMetrics();
        int widthPixels = lDisplayMetrics.widthPixels;
        int heightPixels = lDisplayMetrics.heightPixels;
        int mWidth= widthPixels/2;
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        /*Picasso.get()
                .load(mList.get(position))
                .resize(mWidth-2, mWidth)
                .centerCrop()
                .into(holder.imageView);*/



    }

    @Override
    public int getItemCount() {
        return mList.size();

    }
    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        ImageButton IB_like;

         TextView tv_menu, tv_MarkasSold;

        public MyViewHolder(View itemView)
        {

            super(itemView);



            IB_like=(ImageButton)itemView.findViewById(R.id.IB_like);
          imageView=(ImageView)itemView.findViewById(R.id.iv_image);

           


        }
    }

}
