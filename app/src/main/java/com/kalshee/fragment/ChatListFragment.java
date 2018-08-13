package com.kalshee.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kalshee.MainActivity;
import com.kalshee.R;
import com.kalshee.adapter.ChatListAdapter;
import com.kalshee.pojo.ChatPojo;
import com.kalshee.pojo.UserChatPojo;
import com.kalshee.userData.UserData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * Created by eWeb_A1 on 7/9/2018.
 */

public class ChatListFragment extends Fragment implements View.OnClickListener{

    FirebaseDatabase mDatabase;
    DatabaseReference myRef;
    String mUserID = "";

    HashMap<String, UserChatPojo> mList = new HashMap<String, UserChatPojo>();
    ArrayList<String> senderId_List = new ArrayList<>();
    ArrayList<String> mAdd_id_list = new ArrayList<>();

    String TAG = "ChatListFragment";
    RecyclerView mRecycle;
    ChatListAdapter mChatListAdapter;

    ////////////////////////////FORADAPTER///////////////////
    ArrayList<UserChatPojo> mFinalList = new ArrayList<>();
    ProgressBar mProgressbar;
    TextView tv_selling, tv_buy, tv_all;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_chat_list_layout, container, false);

        mUserID = UserData.getUserID(getContext());

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run()
            {

                MainActivity.mainActivity.removeNotification(3, "");
            }
        });


        XML(mView);
        init();
        return mView;
    }

    private void init() {
        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference("message");
        getData();

    }

    private void XML(View view) {

        mProgressbar = (ProgressBar) view.findViewById(R.id.mProgressbar);
        mRecycle = (RecyclerView) view.findViewById(R.id.mRecycle);
        mRecycle.setLayoutManager(new LinearLayoutManager(getContext()));

        mChatListAdapter = new ChatListAdapter(getActivity(), mFinalList);
        mRecycle.setAdapter(mChatListAdapter);


        tv_all=(TextView)view.findViewById(R.id.tv_all);
        tv_buy=(TextView)view.findViewById(R.id.tv_buy);
        tv_selling=(TextView)view.findViewById(R.id.tv_selling);


       tv_all.setOnClickListener(this);
        tv_buy.setOnClickListener(this);
        tv_selling.setOnClickListener(this);
    }
    private void getData() {


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                senderId_List.clear();
                mAdd_id_list.clear();
                mList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    ChatPojo actor = postSnapshot.getValue(ChatPojo.class);
                    String mSenderId = actor.getSenderId();
                    String mRecevingId = actor.getReceivingId();
                    String mAddId = actor.getAdd_id();
                    String mDeviceID = actor.getDeviceID();
                    String senderName = actor.getSenderName();
                    String Pname = actor.getPname();
                    String Price = actor.getProductPrice();
                    String productImage = actor.getProductImage();
                    String mDateTime = actor.getDateTime();
                    String ownerId= actor.getOwnerId();


                    if (mRecevingId.equalsIgnoreCase(mUserID)  || mSenderId.equalsIgnoreCase(mUserID))
                    {


                        SimpleDateFormat mInputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        mInputFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

                        SimpleDateFormat mOutputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        Date mDate = null;
                        try {

                            mDate = mInputFormat.parse(mDateTime);
                        } catch (Exception e) {
                            Log.e(TAG, "=========ERRORR=======" + e.getMessage());
                        }
                        String dateTime = mOutputFormat.format(mDate);


                        String msg = actor.getMessage();

                        if (senderId_List.contains(mSenderId))
                        {

                            UserChatPojo userChat = new UserChatPojo();
                            userChat.setmAddId(mAddId);
                            userChat.setmDeviceID(mDeviceID);
                            userChat.setProductImage(actor.getProductImage());

                            userChat.setDateTime(dateTime);
                            userChat.setSenderID(mSenderId);
                            userChat.setProductId(actor.getAdd_id());
                            userChat.setName(actor.getPname());
                            userChat.setSenderName(senderName);
                            userChat.setMsg(msg);
                            userChat.setSender_Device_Id(actor.getDeviceID());
                            userChat.setmAdd_UserId(actor.getAddUserId());
                            userChat.setPrice(actor.getProductPrice());
                            userChat.setOwnerId(actor.getOwnerId());
                            mList.put(mSenderId, userChat);


                        }
                        else
                        {
                            senderId_List.add(mSenderId);


                            UserChatPojo userChat = new UserChatPojo();
                            userChat.setmDeviceID(mDeviceID);
                            userChat.setmAddId(mAddId);
                            userChat.setProductImage(actor.getProductImage());
                            userChat.setDateTime(dateTime);


                            userChat.setSenderID(mSenderId);
                            userChat.setProductId(actor.getAdd_id());
                            userChat.setName(actor.getPname());
                            userChat.setSenderName(senderName);
                            userChat.setMsg(msg);
                            userChat.setSender_Device_Id(actor.getDeviceID());
                            userChat.setmAdd_UserId(actor.getAddUserId());
                            userChat.setPrice(actor.getProductPrice());
                            userChat.setOwnerId(actor.getOwnerId());
                            mList.put(mSenderId, userChat);
                        }
                    }
                    else
                    {


                    }

                }


                senderId_List.remove(mUserID);
                mFinalList.clear();
                for (int indx = 0; indx < senderId_List.size(); indx++) {

                    UserChatPojo actor2 = new UserChatPojo();
                    actor2 = mList.get(senderId_List.get(indx));
                    mFinalList.add(actor2);


                }
                mProgressbar.setVisibility(View.GONE);
                mChatListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.tv_all:
                mChatListAdapter.setmList(mFinalList);
                break;
            case R.id.tv_buy:


                    ArrayList<UserChatPojo> mBuy_list= new ArrayList<>();
                for (int indx=0; indx<mFinalList.size(); indx++)
                {

                    String ownerId=mFinalList.get(indx).getOwnerId();
                    if(ownerId.equalsIgnoreCase(mUserID))
                    {


                    }
                    else
                    {
                        mBuy_list.add(mFinalList.get(indx));
                    }
                }

                mChatListAdapter.setmList(mBuy_list);

                break;
            case R.id.tv_selling:

                ArrayList<UserChatPojo> mSelling_list= new ArrayList<>();
                  for (int indx=0; indx<mFinalList.size(); indx++)
                  {

                      String ownerId=mFinalList.get(indx).getOwnerId();
                      if(ownerId.equalsIgnoreCase(mUserID))
                      {

                        mSelling_list.add(mFinalList.get(indx));
                      }
                  }

                  mChatListAdapter.setmList(mSelling_list);

                break;
        }
    }
}
