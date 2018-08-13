package com.kalshee.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kalshee.BaseActivity;
import com.kalshee.ProductDetailsActivity;
import com.kalshee.R;
import com.kalshee.pojo.ChatPojo;
import com.kalshee.pojo.ProductDataPojo;
import com.kalshee.pojo.UserChatPojo;
import com.kalshee.utill.NetworkClass;
import com.kalshee.utill.TimeShow;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by eWeb_A1 on 7/11/2018.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyViewHolder> {


    private Context mContext;
    private ArrayList<UserChatPojo> mList;

    public ChatListAdapter(Context context, ArrayList<UserChatPojo> list)
    {

        this.mContext= context;
        this.mList= list;


    }

    public void setmList( ArrayList<UserChatPojo> list)
    {

        this.mList= list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView= LayoutInflater.from(mContext).inflate(R.layout.adapter_chat_list, parent, false);

        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        final UserChatPojo actor= mList.get(position);
        try {
            holder.tv_title.setText(actor.getName());
        }
        catch (Exception e)
        {
            Log.e("Mith", "==========="+e.getMessage());
        }

        String senderName=actor.getSenderName();
        holder.tv_chat.setText(senderName.toUpperCase().substring(0,1)+senderName.substring(1)+": "+actor.getMsg());

        holder.tv_dateTime.setText(TimeShow.showTime(actor.getDateTime()));
        Picasso.get().load(actor.getProductImage())
                .resize(55, 55)
                .error(R.mipmap.app_logo)
                .into(holder.iv_ProductImage);
        holder.mLinaer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




              Intent  mIntent= new Intent(mContext, BaseActivity.class);

                ProductDataPojo mPojo= new ProductDataPojo();
                mPojo.setADD_USER_ID(actor.getmAdd_UserId());
                mPojo.setADD_ID(actor.getmAddId());
                mPojo.setP_NAME(actor.getName());
                mPojo.setDEVICE_TOKEN(actor.getmDeviceID());
                mPojo.setPRICE(actor.getPrice());
                mPojo.setPRODUCT_PICTURE(actor.getProductImage());
                mPojo.setSENDER_ID(actor.getSenderID());
                mIntent.putExtra("DATA", mPojo);
                mIntent.putExtra("NAME", "CHAT");
                mContext.startActivity(mIntent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title, tv_chat,tv_dateTime;
        LinearLayout mLinaer;
        ImageView iv_ProductImage;

        public MyViewHolder(View itemView) {
            super(itemView);


            tv_title=(TextView)itemView.findViewById(R.id.tv_title);
            tv_chat=(TextView)itemView.findViewById(R.id.tv_chat);
            mLinaer= (LinearLayout)itemView.findViewById(R.id.mLinaer);
            iv_ProductImage=(ImageView)itemView.findViewById(R.id.iv_ProductImage);
            tv_dateTime=(TextView)itemView.findViewById(R.id.tv_dateTime);
        }
    }
}
