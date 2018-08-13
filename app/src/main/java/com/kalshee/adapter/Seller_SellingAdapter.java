package com.kalshee.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kalshee.R;
import com.kalshee.pojo.ProductModal;
import com.kalshee.userData.UserData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by eWeb_A1 on 6/11/2018.
 */

public class Seller_SellingAdapter extends RecyclerView.Adapter<Seller_SellingAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<ProductModal> mList;
    String mType = "SELLING";
    String TAG="Seller_SellingAdapter";

    public Seller_SellingAdapter(Context context, ArrayList<ProductModal> list) {


        this.mContext = context;
        this.mList = list;

    }


    public void setmList(ArrayList<ProductModal> list, String mtype)
    {

        this.mList= list;
        this.mType= mtype;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(mContext).inflate(R.layout.adapter_seller_selling_layout, parent, false);

        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        ProductModal actor = mList.get(position);


        DisplayMetrics lDisplayMetrics = mContext.getResources().getDisplayMetrics();
        int widthPixels = lDisplayMetrics.widthPixels;
        int heightPixels = lDisplayMetrics.heightPixels;
        int mWidth = widthPixels / 2;
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        String image = actor.getImage();
        Log.e(TAG, "===========image=======" + image);
        Picasso.get()
                .load(actor.getImage())
                .resize(mWidth - 2, mWidth)
                .centerCrop()
                .error(R.mipmap.banner)
                .into(holder.imageView);

        holder.tv_title.setText(actor.getEng_title());
        holder.tv_Desc.setText(actor.getDescription());


        if (mType.equalsIgnoreCase("SELLING")) {

            if (actor.getUser_id().equalsIgnoreCase(UserData.getUserID(mContext))) {

                holder.IB_like.setVisibility(View.GONE);


            } else {

                holder.IB_like.setVisibility(View.VISIBLE);
                if (actor.getIs_fav().equalsIgnoreCase("0")) {

                    holder.IB_like.setImageResource(R.mipmap.favrate);
                } else {

                    holder.IB_like.setImageResource(R.mipmap.blue_favourite);
                }

            }


        } else {

            holder.IB_like.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {
        return mList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageButton IB_like;

        TextView tv_title, tv_Desc,tv_menu;


        public MyViewHolder(View itemView) {

            super(itemView);


            IB_like = (ImageButton) itemView.findViewById(R.id.IB_like);
            imageView = (ImageView) itemView.findViewById(R.id.iv_image);
            tv_Desc = (TextView) itemView.findViewById(R.id.tv_Desc);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_menu=(TextView)itemView.findViewById(R.id.tv_menu);


        }
    }

}
