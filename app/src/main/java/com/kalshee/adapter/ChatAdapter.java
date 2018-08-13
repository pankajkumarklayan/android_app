package com.kalshee.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kalshee.R;
import com.kalshee.TimeShow;
import com.kalshee.pojo.ChatPojo;
import com.kalshee.utill.NetworkClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by eWeb_A1 on 7/11/2018.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {


    private Context mContext;
    private ArrayList<ChatPojo> mList;
    String TAG="ChatAdapter";

    public ChatAdapter(Context context, ArrayList<ChatPojo> list)
    {

        this.mContext= context;
        this.mList= list;


    }

    public void setmList(ArrayList<ChatPojo> list)
    {
        this.mList=list;
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView= LayoutInflater.from(mContext).inflate(R.layout.adapter_chat_layout, parent, false);

        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        ChatPojo actor= mList.get(position);



        String  senderMsg=actor.getSenderMsg();
        if(senderMsg.equalsIgnoreCase(""))
        {
            holder.id_mLeftLayout.setVisibility(View.GONE);
        }
        else
        {

            holder.id_mLeftLayout.setVisibility(View.VISIBLE);
            holder.tv_Left.setText(senderMsg);
            holder.tv_senderTime.setText(TimeShow.showTime(actor.getDateTime()));

            Log.e(TAG, "========Sender======="+actor.getSenderProfile());
            Picasso.get().load(actor.getSenderProfile())
                    .error(R.mipmap.noimage)
                    .resize(35, 35)
                    .into(holder.iv_SenderPic);
        }

        String receivingMsg= actor.getReceiverMsg();
        if(receivingMsg.equalsIgnoreCase(""))
        {

            holder.id_RightLayout.setVisibility(View.GONE);
        }
        else
        {
            holder.id_RightLayout.setVisibility(View.VISIBLE);
            holder.tv_right.setText(receivingMsg);
            holder.tv_ReceiverTime.setText(TimeShow.showTime(actor.getDateTime()));




            if(actor.getReceiverProfile().equalsIgnoreCase(""))
            {
                holder.iv_receiver.setImageResource(R.mipmap.noimage);

            }
            else
            {
                Picasso.get().load(NetworkClass.BASE_URL+actor.getReceiverProfile())
                        .error(R.mipmap.noimage)
                        .resize(35, 35)
                        .into(holder.iv_receiver);
            }

        }



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_Left, tv_right,tv_senderTime, tv_ReceiverTime;
        RelativeLayout id_mLeftLayout, id_RightLayout;
        ImageView iv_SenderPic, iv_receiver;

        public MyViewHolder(View itemView) {
            super(itemView);


            tv_right=(TextView)itemView.findViewById(R.id.tv_right);
            tv_Left=(TextView)itemView.findViewById(R.id.tv_Left);
            id_mLeftLayout=(RelativeLayout)itemView.findViewById(R.id.id_mLeft);
            id_RightLayout=(RelativeLayout)itemView.findViewById(R.id.id_RightLayout);

            tv_ReceiverTime=(TextView)itemView.findViewById(R.id.tv_ReceiverTime);
            tv_senderTime=(TextView)itemView.findViewById(R.id.tv_senderTime);


            iv_SenderPic=(ImageView)itemView.findViewById(R.id.iv_SenderPic);
            iv_receiver=(ImageView)itemView.findViewById(R.id.iv_receiver);
        }
    }
}
