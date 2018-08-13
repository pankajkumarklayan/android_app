package com.kalshee.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kalshee.R;
import com.kalshee.fragment.SoldFragment;
import com.kalshee.pojo.ProductModal;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by eWeb_A1 on 6/11/2018.
 */

public class SoldAdapter extends RecyclerView.Adapter<SoldAdapter.MyViewHolder>
{

    private Context mContext;
    private ArrayList<ProductModal>mList;
    SoldFragment mFragment;

    public SoldAdapter(Context context, ArrayList<ProductModal>list, SoldFragment mSoldFragment)
    {


        this.mContext= context;
        this.mList= list;
        this.mFragment= mSoldFragment;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView= LayoutInflater.from(mContext).inflate(R.layout.adapter_sold_layout, parent, false);

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


                holder.mMenu_layout.setVisibility(View.VISIBLE);
            }
        });

        holder.tv_rePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                holder.mMenu_layout.setVisibility(View.GONE);
                mFragment.rePostADD(actor.getUser_ads_id());
            }
        });
        holder.tv_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.mMenu_layout.setVisibility(View.GONE);
                mFragment.deletePost(actor.getUser_ads_id());
            }
        });

        String title= actor.getEng_title();
        String des=actor.getDescription();
        holder.tv_Desc.setText(des.toUpperCase().substring(0,1)+des.substring(1));
        holder.tv_title.setText(title.toUpperCase().substring(0,1)+title.substring(1));
    }

    @Override
    public int getItemCount() {
        return mList.size();

    }
    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
         TextView tv_menu,tv_rePost,tv_Delete;
         LinearLayout mMenu_layout;
         TextView tv_Desc,tv_title;
         ProgressBar mProgressbar;

        public MyViewHolder(View itemView)
        {

            super(itemView);


           imageView=(ImageView)itemView.findViewById(R.id.iv_image);
            tv_Desc=(TextView)itemView.findViewById(R.id.tv_Desc);
            tv_title=(TextView)itemView.findViewById(R.id.tv_title);
            tv_rePost=(TextView)itemView.findViewById(R.id.tv_rePost);
            tv_Delete=(TextView)itemView.findViewById(R.id.tv_Delete);

            tv_menu=(TextView)itemView.findViewById(R.id.tv_menu);
            mMenu_layout=(LinearLayout)itemView.findViewById(R.id.mMenu_layout);
            mProgressbar=(ProgressBar)itemView.findViewById(R.id.mProgressbar);


        }
    }

}
