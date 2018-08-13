package com.kalshee.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.appevents.internal.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kalshee.MainActivity;
import com.kalshee.ProductDetailsActivity;
import com.kalshee.R;
import com.kalshee.adapter.ChatAdapter;
import com.kalshee.pojo.ChatPojo;
import com.kalshee.pojo.ProductDataPojo;
import com.kalshee.userData.UserData;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpRequest;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by eWeb_A1 on 6/14/2018.
 */

public class ChatFragment extends Fragment implements View.OnClickListener {


    ImageButton iv_back;
    TextView tv_title;
    RecyclerView mRecycle;

    FirebaseDatabase database;
    DatabaseReference myRef;
    ImageButton IB_send;
    String ADD_USER_ID = "", ADD_ID = "", P_NAME = "", mSender_DEVICE_TOKEN = "", mUserId = "", SENDER_ID = "", PRICE = "", PRODUCT_PICTURE = "",PROFILE_PIC="";
    EditText et_msg;
    String TAG = "ChatFragment";
    ChatAdapter mChatAdapter;
    ArrayList<ChatPojo> mList = new ArrayList<>();
    BroadcastReceiver mBroadcastReceiver;
    public static boolean mOpen = false;
    TextView tv_productPrice, tv_Pname,tv_details;
    ImageView iv_Pimage;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_chat_layout, container, false);


        //////////////////////////
        mList.clear();
        mChatAdapter = new ChatAdapter(getContext(), mList);
        mRecycle = (RecyclerView) mView.findViewById(R.id.mRecycle);
//        mRecycle.setLayoutManager(new LinearLayoutManager(getContext()));

        LinearLayoutManager manager= new LinearLayoutManager(getActivity());
        manager.setStackFromEnd(true);
        manager.setReverseLayout(false);
        mRecycle.setLayoutManager(manager);
        mRecycle.setAdapter(mChatAdapter);





        PROFILE_PIC= UserData.getProfilePic(getActivity());

        ProductDataPojo actor = (ProductDataPojo) getArguments().getSerializable("DATA");
        ADD_USER_ID = actor.getADD_USER_ID();
        ADD_ID = actor.getADD_ID();
        P_NAME = actor.getP_NAME();
        mSender_DEVICE_TOKEN = actor.getDEVICE_TOKEN();
        SENDER_ID = actor.getSENDER_ID();
        PRICE = actor.getPRICE();
        PRODUCT_PICTURE = actor.getPRODUCT_PICTURE();

        mUserId = UserData.getUserID(getActivity());





        database = FirebaseDatabase.getInstance();
         myRef = database.getReference("message");

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String mType = intent.getStringExtra("TYPE");

                if (mType.equalsIgnoreCase("chat")) {


                    String addId = intent.getStringExtra("addId");
                    String userName = intent.getStringExtra("userName");

                    if (addId.equalsIgnoreCase(ADD_ID)) {


                    }

                }

            }
        };

        getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter("CHAT"));
        XML(mView);



        return mView;
    }


    @Override
    public void onStart() {
        super.onStart();

        mOpen = true;
    }

    @Override
    public void onStop() {
        super.onStop();

        mOpen = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mOpen = false;
        try {

            getActivity().unregisterReceiver(mBroadcastReceiver);
        } catch (Exception e) {
            Log.e(TAG, "===========ERRORR=========" + e.getMessage());
        }
    }

    private void XML(View view) {

        et_msg = (EditText) view.findViewById(R.id.et_msg);
       IB_send = (ImageButton) view.findViewById(R.id.IB_send);
        iv_back = (ImageButton) view.findViewById(R.id.iv_back);
        tv_title = (TextView) view.findViewById(R.id.tv_title);

        iv_Pimage = (ImageView) view.findViewById(R.id.iv_Pimage);
        tv_Pname = (TextView) view.findViewById(R.id.tv_Pname);
        tv_productPrice = (TextView) view.findViewById(R.id.tv_productPrice);
        tv_details=(TextView)view.findViewById(R.id.tv_details);

        Picasso.get().load(PRODUCT_PICTURE)
                .resize(60, 60)
                .into(iv_Pimage);

        tv_title.setText(P_NAME);
        tv_Pname.setText(P_NAME);
        tv_productPrice.setText(getResources().getString(R.string.price) + " " +PRICE);
        iv_back.setOnClickListener(this);

        IB_send.setOnClickListener(this);

        tv_details.setOnClickListener(this);



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                mList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    ChatPojo actor = postSnapshot.getValue(ChatPojo.class);
                    String mSenderId = actor.getSenderId();
                    String mRecevingId = actor.getReceivingId();
                    String mAddId = actor.getAdd_id();
                    String mDeviceID = actor.getDeviceID();
                    String msg = actor.getMessage();
                    String addID = actor.getAdd_id();
                    String mDateTime = actor.getDateTime();


                    if (addID.equalsIgnoreCase(ADD_ID) && (mSenderId.equalsIgnoreCase(SENDER_ID) || mRecevingId.equalsIgnoreCase(SENDER_ID))) {


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

                        ChatPojo mPojo = new ChatPojo();
                        if (mSenderId.equalsIgnoreCase(mUserId)) {
                            String receivingMsg = actor.getMessage();
                            mPojo.setDateTime(dateTime);
                            mPojo.setReceiverMsg(receivingMsg);
                            mPojo.setReceiverProfile(PROFILE_PIC);

                            mList.add(mPojo);
                            Log.e(TAG, "========msg=========" + msg);

                        }
                        else if (mRecevingId.equalsIgnoreCase(mUserId))
                        {
                            mPojo.setDateTime(dateTime);
                            mPojo.setSenderMsg(actor.getMessage());
                            mPojo.setSenderProfile(PRODUCT_PICTURE);
                            mList.add(mPojo);

                        }

                    }


                }

                if (mList.size() == 0) {

                }
                else
                {
                    LinearLayoutManager manager= new LinearLayoutManager(getActivity());
                    manager.setStackFromEnd(true);
                    manager.setReverseLayout(false);
                    mRecycle.setLayoutManager(manager);
                    mChatAdapter.setmList(mList);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.tv_details:
                Intent mIntent= new Intent(getActivity(), ProductDetailsActivity.class);
                mIntent.putExtra("ADD_ID", ADD_ID);
                mIntent.putExtra("ADD_USER_ID", ADD_USER_ID);
                startActivity(mIntent);

                break;
            case R.id.iv_back:

                getActivity().onBackPressed();
                break;
            case R.id.IB_send:


                if (et_msg.getText().toString().trim().length() == 0) {


                }
                else
                {


                    Calendar mCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                    Date currentLocalTime = mCalendar.getTime();
                    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    mFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                    String localTime = mFormat.format(currentLocalTime);

                    Log.e(TAG, "==========localTime===" + localTime);


                    String msg = et_msg.getText().toString().trim();
                    ChatPojo actor = new ChatPojo();
                    actor.setDeviceID(mSender_DEVICE_TOKEN);

                    if (mUserId.equalsIgnoreCase(ADD_USER_ID))
                    {
                        actor.setReceivingId(SENDER_ID);


                    }
                    else
                    {

                        actor.setReceivingId(ADD_USER_ID);
                    }
                    actor.setOwnerId(ADD_USER_ID);
                    actor.setSenderId(mUserId);
                    actor.setAdd_id(ADD_ID);

                    actor.setMessage(et_msg.getText().toString().trim());
                    actor.setDateTime(localTime);
                    actor.setPname(P_NAME);
                    actor.setSenderName(UserData.getNAME(getActivity()));
                    actor.setAddUserId(ADD_USER_ID);
                    actor.setProductPrice(PRICE);
                    actor.setProductImage(PRODUCT_PICTURE);

                    myRef.push().setValue(actor);
                    et_msg.setText("");

                    String refreshedToken = mSender_DEVICE_TOKEN;
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(refreshedToken);


                    sendMessage(jsonArray, "New Message From " + UserData.getNAME(getActivity()), msg, "Http:\\google.com", msg, ADD_ID);

                 break;
                }




        }
    }

    public void sendMessage(final JSONArray recipients, final String title, final String body,
                            final String icon, final String message, final String addId) {


        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    JSONObject root = new JSONObject();
                    JSONObject notification = new JSONObject();
                    notification.put("body", message);
                    notification.put("title", title);
                    notification.put("icon", BitmapFactory.decodeResource(getResources(), R.mipmap.app_logo));
                    notification.put("curretChildId", mSender_DEVICE_TOKEN);////here i send sender deviceId instead to child id

                    JSONObject data = new JSONObject();

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("title", message);
                    jsonObject.put("notification_type", "Chat");//19
                    jsonObject.put("senderID", mUserId);
                    jsonObject.put("userName", title);
                    jsonObject.put("addId", addId);
                    jsonObject.put("curretChildId", mUserId);

                    data.put("message", jsonObject);
                    data.put("curretChildId", mUserId);

                    root.put("notification", notification);
                    root.put("data", data);
                    root.put("registration_ids", recipients);

                    String result = postToFCM(root.toString());
                    Log.d("Main Activity", "Result: " + result);
                    return result;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                try {

                    JSONObject resultJson = new JSONObject(result);
                    if (resultJson != null)
                    {
                        int success, failure;
                        success = resultJson.getInt("success");
                        failure = resultJson.getInt("failure");
                    }
                    else
                    {

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    String postToFCM(String bodyString) throws IOException {

        OkHttpClient mClient = new OkHttpClient();

        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, bodyString);
        Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/fcm/send")
                .post(body)
                .addHeader("Authorization", "key=AIzaSyCFjkK2vrJyDu4WE_wrJiEpOFGl703RKWI")
                .build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }


}
