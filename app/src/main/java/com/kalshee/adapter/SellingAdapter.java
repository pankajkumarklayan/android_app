package com.kalshee.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kalshee.ProductDetailsActivity;
import com.kalshee.R;
import com.kalshee.fragment.DashboardFragment;
import com.kalshee.pojo.ProductModal;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by eWeb_A1 on 6/11/2018.
 */

public class SellingAdapter extends RecyclerView.Adapter<SellingAdapter.MyViewHolder>
{

    private Context mContext;
    private ArrayList<ProductModal>mList;
   DashboardFragment mFragment;
    public SellingAdapter(Context context, ArrayList<ProductModal>list, DashboardFragment fragment)
    {


        this.mContext= context;
        this.mList= list;
        this.mFragment= fragment;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView= LayoutInflater.from(mContext).inflate(R.layout.adapter_selling_layout, parent, false);

        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final ProductModal actor= mList.get(position);

        DisplayMetrics lDisplayMetrics = mContext.getResources().getDisplayMetrics();
        int widthPixels = lDisplayMetrics.widthPixels;
        int mWidth= widthPixels/2;

        Picasso.get()
                .load(actor.getImage())
                .resize(mWidth-2, mWidth)
                .centerCrop()
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.mProgressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.mProgressbar.setVisibility(View.GONE);
                    }
                });


        holder.tv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(actor.getActive().equalsIgnoreCase("0"))
                {

                    holder.tv_Delete.setVisibility(View.VISIBLE);
                }
                else
                {
                    holder.tv_Delete.setVisibility(View.GONE);
                    holder.tv_MarkasSold.setVisibility(View.VISIBLE);

                }

            }
        });

        holder.tv_MarkasSold.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {


                holder.tv_MarkasSold.setVisibility(View.GONE);
                mFragment.clickProduct(actor.getUser_ads_id());
            }
        });

        holder.tv_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFragment.deleteProduct(actor.getUser_ads_id());

            }
        });
        String mTitle= actor.getEng_title();
        String mDes= actor.getDescription();
        holder.tv_title.setText(mTitle.toUpperCase().substring(0,1)+mTitle.substring(1));
        holder.tv_Desc.setText(mDes.toUpperCase().substring(0,1)+mDes.substring(1));


      if(actor.getActive().equalsIgnoreCase("0"))
      {

       holder.tv_pending.setVisibility(View.VISIBLE);
      }
      else
      {
          holder.tv_pending.setVisibility(View.GONE);

      }

      holder.imageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {


              holder.tv_Delete.setVisibility(View.GONE);
              holder.tv_MarkasSold.setVisibility(View.GONE);

              Intent mIntent= new Intent(mContext, ProductDetailsActivity.class);
              mIntent.putExtra("ADD_ID", actor.getUser_ads_id());
              mIntent.putExtra("ADD_USER_ID", actor.getUser_id());
              mContext.startActivity(mIntent);
          }
      });
    }

    @Override
    public int getItemCount() {
        return mList.size();

    }
    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
         TextView tv_menu, tv_MarkasSold,tv_title,tv_Desc;
         ProgressBar mProgressbar;
         TextView tv_pending,tv_Delete;

        public MyViewHolder(View itemView)
        {

            super(itemView);


           imageView=(ImageView)itemView.findViewById(R.id.iv_image);
            tv_MarkasSold=(TextView)itemView.findViewById(R.id.tv_MarkasSold);

            tv_Desc=(TextView)itemView.findViewById(R.id.tv_Desc);
            tv_title=(TextView)itemView.findViewById(R.id.tv_title);
            tv_menu=(TextView)itemView.findViewById(R.id.tv_menu);
            mProgressbar=(ProgressBar)itemView.findViewById(R.id.mProgressbar);
            tv_pending=(TextView)itemView.findViewById(R.id.tv_pending);
            tv_Delete=(TextView)itemView.findViewById(R.id.tv_Delete);



        }
    }

}
