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
import com.kalshee.pojo.FavoritePojo;
import com.kalshee.pojo.ProductModal;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by eWeb_A1 on 6/11/2018.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>
{

    private Context mContext;
    private ArrayList<FavoritePojo>mList;

    public FavoriteAdapter(Context context, ArrayList<FavoritePojo>list)
    {


        this.mContext= context;
        this.mList= list;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView= LayoutInflater.from(mContext).inflate(R.layout.adapter_favorite_layout, parent, false);

        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {


        FavoritePojo actor= mList.get(position);
        DisplayMetrics lDisplayMetrics = mContext.getResources().getDisplayMetrics();
        int widthPixels = lDisplayMetrics.widthPixels;
        int mWidth= widthPixels/2;




        Picasso.get()
                .load(actor.getImage())
                .resize(mWidth-2, mWidth)
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


        String mTitle= actor.getEng_title();
        String mDes= actor.getDescription();
        holder.tv_title.setText(mTitle.toUpperCase().substring(0,1)+mTitle.substring(1));
        holder.tv_Desc.setText(mDes.toUpperCase().substring(0,1)+mDes.substring(1));


    }

    @Override
    public int getItemCount() {
        return mList.size();

    }
    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
         ProgressBar mProgressbar;
         TextView tv_Desc, tv_title;
        public MyViewHolder(View itemView)
        {

            super(itemView);


           imageView=(ImageView)itemView.findViewById(R.id.iv_image);
            mProgressbar=(ProgressBar)itemView.findViewById(R.id.mProgressbar);
            tv_Desc=(TextView)itemView.findViewById(R.id.tv_Desc);
            tv_title=(TextView)itemView.findViewById(R.id.tv_title);




        }
    }

}
