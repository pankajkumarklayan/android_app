package com.kalshee.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.kalshee.ProductDetailsActivity;
import com.kalshee.R;
import com.kalshee.fragment.PostAOfferFragment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by eWeb_A1 on 6/11/2018.
 */

public class SmallPhotoAdapter extends RecyclerView.Adapter<SmallPhotoAdapter.MyViewHolder>
{

    private Context mContext;
    private List<String> mList;
    String TAG="SmallPhotoAdapter";


    public SmallPhotoAdapter(Context context, List<String> list)
    {


        this.mContext= context;
        this.mList= list;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView= LayoutInflater.from(mContext).inflate(R.layout.adapter_small_image, parent, false);

        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position)
    {

        Picasso.get().load(mList.get(position))
                .resize(95, 95)
                .into(holder.ivImage, new Callback()
                {
                    @Override
                    public void onSuccess() {
                        holder.mProgressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.mProgressbar.setVisibility(View.GONE);
                    }
                });


        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                ((ProductDetailsActivity)mContext).setImage(mList.get(position));
            }
        });

    }
    @Override
    public int getItemCount() {
        return mList.size();

    }
    public class MyViewHolder extends RecyclerView.ViewHolder
    {


        ImageView ivImage;
        ProgressBar mProgressbar;

        public MyViewHolder(View itemView)
        {

            super(itemView);



            ivImage= (ImageView)itemView.findViewById(R.id.ivImage);
            mProgressbar=(ProgressBar)itemView.findViewById(R.id.mProgressbar);


        }
    }

}
