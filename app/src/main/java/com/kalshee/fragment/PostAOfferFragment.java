package com.kalshee.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kalshee.R;
import com.kalshee.adapter.PhotoAdapter;
import com.kalshee.globalArray.GlobalArray;
import com.kalshee.userData.UserData;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static android.os.Environment.getExternalStoragePublicDirectory;

/**
 * Created by eWeb_A1 on 6/12/2018.
 */

public class PostAOfferFragment extends Fragment implements View.OnClickListener
{

    Button bt_Next;
    Fragment mFragment;
    LinearLayout mTake_photoLinear,mPhoto_layout,mGallery_layout;
    int MY_PERMISSIONS_REQUEST_CAMERA=0;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_TAKE_PHOTO_BELLOW_19 = 2;
    final int ACTIVITY_SELECT_IMAGE = 1234;
    File photoFile;
    String mCurrentPhotoPath;
    RecyclerView mPhoto_RecycleView;
    ArrayList<File> mList= new ArrayList<>();
    PhotoAdapter mPhotoAdapter;
    ImageButton iv_Camera;
    int noOfColumns;
    EditText et_Title;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View mView= inflater.inflate(R.layout.fragment_post_offer, container, false);

        XML(mView);
        return mView;
    }
    private void XML(View view)
    {


        bt_Next=(Button)view.findViewById(R.id.bt_Next);
        mTake_photoLinear=(LinearLayout)view.findViewById(R.id.mTake_photoLinear);
        mPhoto_RecycleView=(RecyclerView)view.findViewById(R.id.mPhoto_RecycleView);
        DisplayMetrics displayMetrics= getResources().getDisplayMetrics();
        float width= displayMetrics.widthPixels/ displayMetrics.density;
        noOfColumns = (int) (width / 120);

        mPhoto_RecycleView.setLayoutManager(new StaggeredGridLayoutManager(noOfColumns, StaggeredGridLayoutManager.VERTICAL));
        mPhotoAdapter= new PhotoAdapter(getActivity(), mList, PostAOfferFragment.this);
        mPhoto_RecycleView.setAdapter(mPhotoAdapter);
        iv_Camera=(ImageButton)view.findViewById(R.id.iv_Camera);
        mPhoto_layout=(LinearLayout)view.findViewById(R.id.mPhoto_layout);
        mGallery_layout=(LinearLayout)view.findViewById(R.id.mGallery_layout);
        et_Title=(EditText)view.findViewById(R.id.et_Title);


        bt_Next.setOnClickListener(this);
        mTake_photoLinear.setOnClickListener(this);
        mGallery_layout.setOnClickListener(this);
        iv_Camera.setOnClickListener(this);


    }
    @Override
    public void onClick(View v)
    {
        int version= Build.VERSION.SDK_INT;
        switch (v.getId())
        {

            case R.id.bt_Next:

                if(validation())
                {
                    GlobalArray.mImage_File.clear();
                    GlobalArray.mImage_File.addAll(mList);
                    UserData.setProductTitle(getActivity(), et_Title.getText().toString().trim());

                    mFragment= new PostFragmentTwo();
                    getFragmentManager().beginTransaction().add(R.id.mPost_Container, mFragment).addToBackStack("").commit();
                }
                break;

            case R.id.mTake_photoLinear:

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

            case R.id.iv_Camera:


              takePHOTODialog();

                break;

            case R.id.mGallery_layout:

                if(checkPermission(getActivity()))
                {

                    Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
                }
                else
                {


                }


                break;

        }
    }

    private boolean validation()
    {

        if(mList.size()==0)
        {

           showError(getString(R.string.PleaseselectAtleastonephoto));
           return  false;
        }
       else if(et_Title.getText().length()==0)
        {

            et_Title.requestFocus();
            et_Title.setError(getString(R.string.PleasEnterTitle));
            return  false;
        }
        else
        {
            return  true;
        }



    }
    private void dispatchTakePictureIntent()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile = null;
            try
            {
                photoFile = createImageFile();
            }
            catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null)
            {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),"com.kalshee.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
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
     //   super.onRequestPermissionsResult(requestCode, permissions, grantResults);


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
      //  super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_TAKE_PHOTO && resultCode ==RESULT_OK)
        {



            Bitmap mBitmap= BitmapFactory.decodeFile(mCurrentPhotoPath);
            Bitmap bitmap=adjustImageOrientation(mBitmap);

            Uri mUri= getImageUri(getContext(), bitmap);
            File mFile= new File(getRealPathFromURI(mUri));

            mList.add(mFile);
            mPhotoAdapter.notifyDataSetChanged();
            if(mList.size() >noOfColumns)
            {

                ViewGroup.LayoutParams params=mPhoto_RecycleView.getLayoutParams();
                params.height=500;
                mPhoto_RecycleView.setLayoutParams(params);

            }
            else
            {
                ViewGroup.LayoutParams params=mPhoto_RecycleView.getLayoutParams();
                params.height=250;
                mPhoto_RecycleView.setLayoutParams(params);

            }
            mPhoto_layout.setVisibility(View.VISIBLE);
            mTake_photoLinear.setVisibility(View.GONE);
            mGallery_layout.setVisibility(View.GONE);


        }
        else if(requestCode ==REQUEST_TAKE_PHOTO_BELLOW_19 && resultCode== RESULT_OK)
        {


            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");


            Bitmap bitmap=adjustImageOrientation(imageBitmap);

            Uri mUri= getImageUri(getContext(), bitmap);
            photoFile = new File(getPath(mUri));

            mList.add(photoFile);
            mPhotoAdapter.notifyDataSetChanged();
            if(mList.size() >noOfColumns)
            {

                ViewGroup.LayoutParams params=mPhoto_RecycleView.getLayoutParams();
                params.height=500;
                mPhoto_RecycleView.setLayoutParams(params);

            }
            else
            {
                ViewGroup.LayoutParams params=mPhoto_RecycleView.getLayoutParams();
                params.height=250;
                mPhoto_RecycleView.setLayoutParams(params);

            }
            mPhoto_layout.setVisibility(View.VISIBLE);
            mTake_photoLinear.setVisibility(View.GONE);
            mGallery_layout.setVisibility(View.GONE);
        }
        else if(requestCode==ACTIVITY_SELECT_IMAGE && resultCode==RESULT_OK)
        {


            Uri selectedImageUri = data.getData();
            photoFile = new File(getPath(selectedImageUri));

            mList.add(photoFile);
            mPhotoAdapter.notifyDataSetChanged();
            if(mList.size() >noOfColumns)
            {

                ViewGroup.LayoutParams params=mPhoto_RecycleView.getLayoutParams();
                params.height=500;
                mPhoto_RecycleView.setLayoutParams(params);

            }
            else
            {
                ViewGroup.LayoutParams params=mPhoto_RecycleView.getLayoutParams();
                params.height=250;
                mPhoto_RecycleView.setLayoutParams(params);

            }
            mPhoto_layout.setVisibility(View.VISIBLE);
            mTake_photoLinear.setVisibility(View.GONE);
            mGallery_layout.setVisibility(View.GONE);

        }

    }



    public void removePhoto(int position)
    {

        mList.remove(position);
        if(mList.size()==0)
        {
            mPhoto_layout.setVisibility(View.GONE);
            mTake_photoLinear.setVisibility(View.VISIBLE);
            mGallery_layout.setVisibility(View.VISIBLE);


        }
        else
        {

            if(mList.size() >noOfColumns)
            {

                ViewGroup.LayoutParams params=mPhoto_RecycleView.getLayoutParams();
                params.height=500;
                mPhoto_RecycleView.setLayoutParams(params);
                mPhotoAdapter.notifyDataSetChanged();
            }
            else
            {
                ViewGroup.LayoutParams params=mPhoto_RecycleView.getLayoutParams();
                params.height=250;
                mPhoto_RecycleView.setLayoutParams(params);
                mPhotoAdapter.notifyDataSetChanged();

            }


        }
    }
    private void showError(String msg)
    {

        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
    public Uri getImageUri(Context inContext, Bitmap inImage)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
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

private void takePHOTODialog()
{

    final Dialog mDialog= new Dialog(getActivity());
    mDialog.setTitle("Taking Photo");
    mDialog.setContentView(R.layout.dialog_take_photo);
    mDialog.setCancelable(false);


    TextView tv_camera=(TextView)mDialog.findViewById(R.id.tv_camera);
    TextView tv_gallery=(TextView)mDialog.findViewById(R.id.tv_gallery);
    TextView tv_cancel=(TextView)mDialog.findViewById(R.id.tv_cancel);

    tv_camera.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {


            mDialog.dismiss();
            if(mList.size() >=20)
            {

                showError(getString(R.string.take20photo));
            }
            else
            {
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


            }

        }
    });
    tv_gallery.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            mDialog.dismiss();

            if(mList.size() >=20)
            {
                showError(getString(R.string.take20photo));

            }
            else
            {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(i, ACTIVITY_SELECT_IMAGE);

            }


        }
    });
    tv_cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            mDialog.dismiss();
        }
    });
    mDialog.show();
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

}
