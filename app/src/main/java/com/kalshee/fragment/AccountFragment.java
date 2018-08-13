package com.kalshee.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kalshee.FilterActivity;
import com.kalshee.R;
import com.kalshee.SettingActivity;
import com.kalshee.userData.UserData;
import com.kalshee.utill.NetworkClass;
import com.kalshee.utill.Utility;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

import static android.app.Activity.RESULT_OK;
import static android.os.Environment.getExternalStoragePublicDirectory;

/**
 * Created by eWeb_A1 on 6/13/2018.
 */

public class AccountFragment extends Fragment implements View.OnClickListener
{

    ImageButton IB_Setting;
    final   int SETTING_INTENT=100;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_TAKE_PHOTO_BELLOW_19 = 2;
    ImageButton Ib_camera;
    String mCurrentPhotoPath;
    ImageView iv_largeImage, profile_image;
    TextView tv_Name,tv_Joined, tv_Email;
    File photoFile;
    String TAG="AccountFragment";
    ProgressDialog mProgressDialog;
    ProgressBar mProgressbar;
    int MY_PERMISSIONS_REQUEST_CAMERA=0;

    View mView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

         mView= inflater.inflate(R.layout.fragment_account_layout, container, false);
        XML(mView);
        return mView;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        tv_Name=(TextView)mView.findViewById(R.id.tv_Name);
        String name= UserData.getNAME(getActivity());
        tv_Name.setText(name.substring(0,1).toUpperCase()+name.substring(1));

    }
    private void XML(View view)
    {

        mProgressbar=(ProgressBar)view.findViewById(R.id.mProgressbar);
        IB_Setting=(ImageButton)view.findViewById(R.id.IB_Setting);
        Ib_camera=(ImageButton)view.findViewById(R.id.Ib_camera);
        iv_largeImage=(ImageView)view.findViewById(R.id.iv_largeImage);
        profile_image=(ImageView)view.findViewById(R.id.profile_image);
        String mUrl= NetworkClass.BASE_URL+UserData.getProfilePic(getActivity());
        tv_Joined=(TextView)view.findViewById(R.id.tv_Joined);
        String Join_date= UserData.getJoinDate(getActivity());
        tv_Joined.setText(getResources().getString(R.string.joined)+" "+Join_date.substring(0,1).toUpperCase()+Join_date.substring(1));

        tv_Email=(TextView)view.findViewById(R.id.tv_Email);
        tv_Email.setText(UserData.getEMAIL(getActivity()));


      Picasso.get().load(mUrl)
              .placeholder(R.mipmap.noimage)
              .error(R.mipmap.noimage)
              .into(iv_largeImage, new Callback() {
                  @Override
                  public void onSuccess() {
                      mProgressbar.setVisibility(View.GONE);
                  }

                  @Override
                  public void onError(Exception e) {
                      mProgressbar.setVisibility(View.GONE);
                  }
              });



        Picasso.get().load(mUrl)
                .placeholder(R.mipmap.noimage)
                .error(R.mipmap.noimage)
                .into(profile_image, new Callback() {
                    @Override
                    public void onSuccess() {
                        mProgressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        mProgressbar.setVisibility(View.GONE);
                    }
                });
        Log.e(TAG, "=============PROFILE=URL======"+mUrl);





        Ib_camera.setOnClickListener(this);
        IB_Setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        switch (v.getId())
        {

            case R.id.IB_Setting:
                Intent mIntent= new Intent(getContext(), SettingActivity.class);
                startActivityForResult(mIntent, SETTING_INTENT);
                break;

            case R.id.Ib_camera:



                int version= Build.VERSION.SDK_INT;



                if(checkPermission(getActivity()))
                {

                    if(version >=21)
                    {
                        dispatchTakePictureIntent();

                    }
                    else
                    {
                        Intent mCamera= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(mCamera, REQUEST_TAKE_PHOTO_BELLOW_19);


                    }
                }
                else
                {


                }



                break;

        }
    }
    private void dispatchTakePictureIntent()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
         File mLocalphotoFile = null;
            try
            {
                mLocalphotoFile = createImageFile();
            }
            catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (mLocalphotoFile != null)
            {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),"com.kalshee.android.fileprovider", mLocalphotoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException
    {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK)
        {


            setPic();
            if(Utility.checkInternet(getActivity()))
            {

                hitAPI();
            }


        }
        else if(requestCode== REQUEST_TAKE_PHOTO_BELLOW_19 && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            iv_largeImage.setImageBitmap(adjustImageOrientation(imageBitmap));
            profile_image.setImageBitmap(adjustImageOrientation(imageBitmap));



           // Uri mUri= getImageUri(getContext(), imageBitmap);
          //  photoFile = new File(getPath(mUri));


            Bitmap bitmap2=adjustImageOrientation(imageBitmap);

            Uri mUri= getImageUri(getContext(), bitmap2);
            photoFile= new File(getRealPathFromURI(mUri));
        }

    }
    public Uri getImageUri(Context inContext, Bitmap inImage)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =             cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }
    private void setPic()
    {
        // Get the dimensions of the View
        int targetW = iv_largeImage.getWidth();
        int targetH = iv_largeImage.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        iv_largeImage.setImageBitmap(adjustImageOrientation(bitmap));
        profile_image.setImageBitmap(adjustImageOrientation(bitmap));


        Bitmap bitmap2=adjustImageOrientation(bitmap);

        Uri mUri= getImageUri(getContext(), bitmap2);
        photoFile= new File(getRealPathFromURI(mUri));



    }
    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };

        //This method was deprecated in API level 11
        //Cursor cursor = managedQuery(contentUri, proj, null, null, null);

        CursorLoader cursorLoader = new CursorLoader(getActivity(),
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int column_index =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    private Bitmap adjustImageOrientation(Bitmap image) {
        ExifInterface exif;
        try {
            exif = new ExifInterface(mCurrentPhotoPath);
            int exifOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            int rotate = 0;
            switch (exifOrientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
            }

            if (rotate != 0) {
                int w = image.getWidth();
                int h = image.getHeight();

                // Setting pre rotate
                Matrix mtx = new Matrix();
                mtx.preRotate(rotate);

                // Rotating Bitmap & convert to ARGB_8888, required by tess
                image = Bitmap.createBitmap(image, 0, 0, w, h, mtx, false);

            }
        } catch (IOException e) {
            return null;
        }
        return image.copy(Bitmap.Config.ARGB_8888, true);
    }

    private void hitAPI()
    {

        AsyncHttpClient mAsyncHttpClient= new AsyncHttpClient();
        mAsyncHttpClient.setTimeout(90000);
        mAsyncHttpClient.setResponseTimeout(90000);
        mAsyncHttpClient.setConnectTimeout(90000);

        RequestParams mParams= new RequestParams();
        mParams.add("user_id", UserData.getUserID(getActivity()));
        mParams.add("name", UserData.getNAME(getActivity()));

        try
        {
            mParams.put("userProfile", photoFile);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.e(TAG, "==========ERRORR========="+e.getMessage());
        }

        String mURL= NetworkClass.BASE_URL+NetworkClass.UPDATE_PROFILE;
        Log.e(TAG, "===================URL======"+mURL);
        Log.e(TAG, "============PARM========="+mParams);

        mAsyncHttpClient.post(mURL, mParams, new JsonHttpResponseHandler()
        {

            @Override
            public void onStart() {
                super.onStart();
                mProgressDialog= new ProgressDialog(getActivity());
                mProgressDialog.setMessage(getActivity().getResources().getString(R.string.UpdateProfile));
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                mProgressDialog.dismiss();
                Log.e(TAG, "=========response========"+response);

                if(statusCode==200)
                {
                    try {
                        String code= response.getString("code");
                        String message=response.getString("message");
                        String profile_image= response.getString("profile_image");

                        UserData.setProfilePic(getActivity(), profile_image);
                        showMessge(message);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e(TAG, "===============ERRORR==========="+errorResponse);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mProgressDialog.dismiss();
            }
        });


    }

    private void showMessge(String meg)
    {

        Toast.makeText(getActivity(), meg, Toast.LENGTH_SHORT).show();
    }



    public boolean checkPermission(final Context context)
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
            {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {

                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CAMERA);

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CAMERA);


                }
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
    //    super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if(requestCode==MY_PERMISSIONS_REQUEST_CAMERA )
        {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {

                int version= Build.VERSION.SDK_INT;

                if(version >=21)
                {
                    dispatchTakePictureIntent();

                }
                else
                {
                    Intent mCamera= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(mCamera, REQUEST_TAKE_PHOTO_BELLOW_19);


                }

            }
            else
            {

                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();

            }
        }

    }
}
