package com.kalshee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kalshee.R;
import com.kalshee.pojo.CityModal;

import java.util.ArrayList;

/**
 * Created by eWeb_A1 on 6/18/2018.
 */

public class CityAdapter extends BaseAdapter
{

    private Context mContext;
    private ArrayList<CityModal> mList;

    public CityAdapter(Context context, ArrayList<CityModal> list)
    {

        this.mContext= context;
        this.mList= list;



    }

    @Override
    public int getCount()
    {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CityModal actor= mList.get(position);
        LayoutInflater mLayoutInflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView=mLayoutInflater.inflate(R.layout.category_text_layout, parent, false);
        TextView tv_title=(TextView)mView.findViewById(R.id.tv_title);
        tv_title.setText(actor.getEng_title());

        return mView;
    }
}
