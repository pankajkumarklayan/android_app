package com.kalshee.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kalshee.ProductDetailsActivity;
import com.kalshee.R;
import com.kalshee.fragment.HomeFragment;
import com.kalshee.pojo.ProductModal;
import com.kalshee.userData.UserData;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by eWeb_A1 on 6/11/2018.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<ProductModal>mList;
    HomeFragment mHomeFragment;
    String TAG="HomeAdapter";
    String mUserId="";

    public HomeAdapter(Context context, ArrayList<ProductModal> list, HomeFragment homeFragment)
    {
        this.mContext= context;
        this.mList= list;
        this.mHomeFragment= homeFragment;
        this.mUserId= UserData.getUserID(context);

    }
    public void setmList(ArrayList<ProductModal>list)
    {

        this.mList= list;
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView= LayoutInflater.from(mContext).inflate(R.layout.adapter_home_screen, parent, false);

        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {


        final ProductModal actor= mList.get(position);

        DisplayMetrics lDisplayMetrics = mContext.getResources().getDisplayMetrics();
        int widthPixels = lDisplayMetrics.widthPixels;
        int mWidth= widthPixels/2;
        int width= mWidth-50;
        Log.e(TAG, "========actor.getImage()==============="+actor.getImage());


        Picasso.get()
                .load(actor.getImage())
                .resize(0, width)
                .error(R.mipmap.banner7)
                .into(holder.imageView, new Callback()
                {
                    @Override
                    public void onSuccess()
                    {
                        holder.mProgressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.mProgressbar.setVisibility(View.GONE);
                    }


                });


        String title= actor.getEng_title();
        holder.tv_title.setText(title.substring(0,1).toUpperCase()+title.substring(1));

        String mPrice=mContext.getResources().getString(R.string.price)+ " " + actor.getPrice();
        if(mPrice.length()==11)
        {

            holder.tv_price.setTextSize(12);
            holder.tv_price.setText(mPrice);

        }
        else
        {
            holder.tv_price.setTextSize(10);
            holder.tv_price.setText(mPrice);
        }



        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mIntent= new Intent(mContext, ProductDetailsActivity.class);
                mIntent.putExtra("ADD_ID", actor.getUser_ads_id());
                mIntent.putExtra("ADD_USER_ID", actor.getUser_id());
                mContext.startActivity(mIntent);

            }
        });


        if(actor.getIs_fav().equalsIgnoreCase("1"))
        {
            holder.IB_like.setImageResource(R.mipmap.blue_favourite);

        }
        else
        {

            holder.IB_like.setImageResource(R.mipmap.favrate);
        }

        holder.IB_like.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                if(actor.getIs_fav().equalsIgnoreCase("0"))
                {
                    holder.IB_like.setImageResource(R.mipmap.blue_favourite);

                }
                else
                {

                    holder.IB_like.setImageResource(R.mipmap.favrate);
                }


                mHomeFragment.likeApi(actor,actor.getUser_ads_id());

            }
        });



        if(mUserId.equalsIgnoreCase(actor.getUser_id()))
        {


            holder.IB_like.setVisibility(View.GONE);
        }
        else
        {
            holder.IB_like.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public int getItemCount() {
        return mList.size();

    }
    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView tv_title, tv_price;
        ImageButton IB_like;
        ProgressBar mProgressbar;


        public MyViewHolder(View itemView)
        {

            super(itemView);

            mProgressbar=(ProgressBar)itemView.findViewById(R.id.mProgressbar);
          imageView=(ImageView)itemView.findViewById(R.id.iv_image);
            tv_title=(TextView)itemView.findViewById(R.id.tv_title);
            tv_price=(TextView)itemView.findViewById(R.id.tv_price);
            IB_like=(ImageButton)itemView.findViewById(R.id.IB_like);

        }
    }

}
