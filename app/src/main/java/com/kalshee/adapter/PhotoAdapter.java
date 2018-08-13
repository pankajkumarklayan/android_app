package com.kalshee.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kalshee.R;
import com.kalshee.fragment.PostAOfferFragment;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by eWeb_A1 on 6/11/2018.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder>
{

    private Context mContext;
    private List<File> mList;
    TypedArray BlueArrayIcon, RedArrayIcon;
    String TAG="FilterAdapter";
    PostAOfferFragment mFragment;

    public PhotoAdapter(Context context, List<File> list, PostAOfferFragment postAOfferFragment)
    {


        this.mContext= context;
        this.mList= list;
        this.mFragment= postAOfferFragment;



        BlueArrayIcon = mContext.getResources().obtainTypedArray(R.array.Blue_image);
        RedArrayIcon=mContext.getResources().obtainTypedArray(R.array.Red_image);

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView= LayoutInflater.from(mContext).inflate(R.layout.adapter_photo_layout, parent, false);

        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position)
    {

        Picasso.get().load(mList.get(position))
                .into(holder.iv_image);

       /* Glide.with(mContext)
                .load(mList.get(position))
                .into(holder.iv_image);*/

        holder.iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mFragment.removePhoto(position);

            }
        });


    }
    @Override
    public int getItemCount() {
        return mList.size();

    }
    public class MyViewHolder extends RecyclerView.ViewHolder
    {


        ImageView iv_image, iv_DeleteImage;

        public MyViewHolder(View itemView)
        {

            super(itemView);


            iv_DeleteImage=(ImageView)itemView.findViewById(R.id.iv_DeleteImage);
            iv_image= (ImageView)itemView.findViewById(R.id.iv_image);


        }
    }

}
