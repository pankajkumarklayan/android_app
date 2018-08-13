package com.kalshee

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.system.Os.bind
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.kalshee.fragment.ChangeFragment
import com.kalshee.fragment.ContactsUsFragment
import com.kalshee.userData.UserData
import com.kalshee.utill.NetworkClass
import com.kalshee.utill.Utility
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class SettingActivity : AppCompatActivity() ,View.OnClickListener
{


    private  lateinit var  ibBack : ImageButton
    private lateinit var  tv_LogOut: TextView
    private lateinit var tv_editPassword: TextView
   private lateinit var  firstFragment:Fragment
    private lateinit var tv_ContactUs:TextView
    private lateinit var et_ProfileName:EditText
    private lateinit var tv_edit:TextView
    private lateinit var tv_done:TextView
    internal var TAG = "AccountFragment"
    private lateinit var mProgressDialog:ProgressDialog



    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)


        XML();
    }
    private fun XML()
    {

        ibBack = findViewById(R.id.ibBack) as ImageButton
        ibBack.setOnClickListener(this)


        tv_LogOut=findViewById(R.id.tv_LogOut) as TextView
        tv_LogOut.setOnClickListener(this)


        tv_editPassword=findViewById(R.id.tv_editPassword) as TextView
        tv_editPassword.setOnClickListener(this)


        tv_ContactUs=findViewById(R.id.tv_ContactUs) as TextView
        tv_ContactUs.setOnClickListener(this)

        et_ProfileName=findViewById(R.id.et_ProfileName) as EditText

        var  name= UserData.getNAME(this);
        et_ProfileName.setText(name.toUpperCase())


        tv_edit=findViewById(R.id.tv_edit) as TextView
        tv_edit.setOnClickListener(this)


        tv_done=findViewById(R.id.tv_done) as TextView
        tv_done.setOnClickListener(this)


    }
    override fun onClick(v: View)
    {

        when(v.id)
        {

            R.id.ibBack ->{



                onBackPressed()

            }
            R.id.tv_LogOut ->
            {

              logout()
            }

            R.id.tv_editPassword ->
            {

                 firstFragment = ChangeFragment();
                supportFragmentManager.beginTransaction().replace(R.id.mContainer,  firstFragment).addToBackStack("").commit()

            }
            R.id.tv_ContactUs->
            {

                firstFragment= ContactsUsFragment()
                supportFragmentManager.beginTransaction().replace(R.id.mContainer,  firstFragment).addToBackStack("").commit()



            }

            R.id.tv_edit->{


                var mlength= et_ProfileName.text.length
                et_ProfileName.setSelection(mlength)
                et_ProfileName.isEnabled= true
                et_ProfileName.requestFocus()

                tv_edit.visibility= View.GONE
                tv_done.visibility= View.VISIBLE


            }
            R.id.tv_done->
            {
                tv_edit.visibility= View.VISIBLE
                tv_done.visibility= View.GONE
                et_ProfileName.isEnabled= false

                tv_done.hideKeyboard()
                if(Utility.checkInternet(this))
                {
                    hitAPI()

                }


            }


        }


    }
    private fun showMsg(msg :String)
    {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    override fun onBackPressed()
    {

        super.onBackPressed()



    }
    private fun logout()
    {
        UserData.Logout(this)


        val  mIntent = Intent(this, LoginActivity::class.java)
        startActivity(mIntent)
        finishAffinity();



    }

    private fun hitAPI() {

        val mAsyncHttpClient = AsyncHttpClient()
        mAsyncHttpClient.setTimeout(90000)
        mAsyncHttpClient.responseTimeout = 90000
        mAsyncHttpClient.connectTimeout = 90000

        val mParams = RequestParams()
        mParams.add("user_id", UserData.getUserID(this))
        mParams.add("name", et_ProfileName.text.toString())
        mParams.add("userProfile", "")



        val mURL = NetworkClass.BASE_URL + NetworkClass.UPDATE_PROFILE
        Log.e(TAG, "===================URL======$mURL")
        Log.e(TAG, "============PARM=========$mParams")

        mAsyncHttpClient.post(mURL, mParams, object : JsonHttpResponseHandler() {

            override fun onStart() {
                super.onStart()
                mProgressDialog = ProgressDialog(this@SettingActivity)
                mProgressDialog.setMessage(getString(R.string.UpdateProfile))
                mProgressDialog.setCancelable(false)
                mProgressDialog.show()
            }

            override fun onSuccess(statusCode: Int, headers: Array<Header>?, response: JSONObject?) {
                super.onSuccess(statusCode, headers, response)

                mProgressDialog.dismiss()
                Log.e(TAG, "=========response========" + response!!)

                if (statusCode == 200) {
                    try {
                        val code = response.getString("code")
                        val message = response.getString("message")
                        val profile_image = response.getString("profile_image")
                        val  name= response.getString("name")

                        UserData.setNAME(this@SettingActivity, name)


                        showMessge(message)
                    }
                    catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>?, throwable: Throwable, errorResponse: JSONObject?) {
                super.onFailure(statusCode, headers, throwable, errorResponse)
                Log.e(TAG, "===============ERRORR===========" + errorResponse!!)
            }

            override fun onFinish() {
                super.onFinish()
                mProgressDialog.dismiss()
            }
        })


    }

    private fun showMessge(meg: String) {

        Toast.makeText(this, meg, Toast.LENGTH_SHORT).show()
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
