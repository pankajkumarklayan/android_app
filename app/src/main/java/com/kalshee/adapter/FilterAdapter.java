package com.kalshee.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kalshee.FilterActivity;
import com.kalshee.R;
import com.kalshee.pojo.FilterModal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eWeb_A1 on 6/11/2018.
 */

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MyViewHolder>
{

    private Context mContext;
    private List<FilterModal> mList;
    TypedArray BlueArrayIcon, RedArrayIcon;
    String TAG="FilterAdapter";

    public FilterAdapter(Context context, List<FilterModal>list)
    {


        this.mContext= context;
        this.mList= list;



        BlueArrayIcon = mContext.getResources().obtainTypedArray(R.array.Blue_image);
        RedArrayIcon=mContext.getResources().obtainTypedArray(R.array.Red_image);

    }

    public void setmList(ArrayList<FilterModal> list)
    {
        this.mList= list;
        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView= LayoutInflater.from(mContext).inflate(R.layout.adapter_filter_layout, parent, false);

        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final FilterModal actor= mList.get(position);
    //    holder.imageView.setImageResource(actor.getBlueimage());
        holder.tv_title.setText(actor.getEng_title());
        Log.e(TAG, "=====NMAE========="+actor.getName());

        if(actor.getTextColor().equalsIgnoreCase("Black"))
        {
            holder.tv_title.setTextColor(mContext.getResources().getColor(R.color.colorAccent));

        }





        holder.mChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if(isChecked)
                {

                  //  holder.imageView.setImageResource(actor.getRedImage());
                    holder.tv_title.setTextColor(mContext.getResources().getColor(R.color.text_color));
                    ((FilterActivity)mContext).setCheck(isChecked, actor,position);
                }
                else
                {
                  //  holder.imageView.setImageResource(actor.getBlueimage());
                    holder.tv_title.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                    ((FilterActivity)mContext).setCheck(isChecked, actor, position);

                }
            }
        });

        if(actor.isCheck())
        {

       //     holder.imageView.setImageResource(actor.getRedImage());
            holder.tv_title.setTextColor(mContext.getResources().getColor(R.color.text_color));

        }
        else
        {

         //   holder.imageView.setImageResource(actor.getBlueimage());
            holder.tv_title.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        }

      /*  holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mpos=position+1;

                Toast.makeText(mContext, ""+mpos, Toast.LENGTH_SHORT).show();

            }
        });*/

    }
    @Override
    public int getItemCount() {
        return mList.size();

    }
    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView tv_title;
        CheckBox mChecked;
        LinearLayout mLayout;


        public MyViewHolder(View itemView)
        {

            super(itemView);


         //   imageView=(ImageView)itemView.findViewById(R.id.iv_image);
            tv_title=(TextView)itemView.findViewById(R.id.tv_title);
          mChecked=(CheckBox)itemView.findViewById(R.id.mChecked);
            mLayout=(LinearLayout)itemView.findViewById(R.id.mLayout);







        }
    }

}
