package com.test.androidserver.initial

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.test.androidserver.MainActivity
import com.test.androidserver.R
import com.test.androidserver.data.AppData
import com.test.androidserver.data.AppPreferenceHelper
import com.test.androidserver.model.UserModel
import com.test.androidserver.network.APIService
import com.test.androidserver.network.ApiUtils
import com.test.androidserver.network.NoConnectivityException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var signup_link:TextView
    lateinit var et_email:EditText
    lateinit var et_password:EditText
    lateinit var signin_btn:Button
    private lateinit var mAPIService: APIService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initializeViews();
        initials();
        eventListner();
    }

    private fun eventListner() {
        signup_link.setOnClickListener{
            val intent = Intent(applicationContext, SignupActivity::class.java)
            startActivity(intent)
        }
        signin_btn.setOnClickListener {

            mAPIService!!.callLogin(et_email.text.toString(),et_password.text.toString()).enqueue(object : Callback<UserModel> {
                override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {

                    if (response.isSuccessful) {

                        var userModel:UserModel= UserModel()
                        userModel= response.body()!!
                        if(userModel.getSuccess()==1)
                        {
                            Toast.makeText(this@LoginActivity, "" + userModel.getMsg(), Toast.LENGTH_SHORT).show();
                            AppPreferenceHelper.getInstance().saveUserDetails(userModel)
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                        }
                        else
                        {
                            Toast.makeText(this@LoginActivity, "" + userModel.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(this@LoginActivity, "" + getResources().getString(R.string.somewentwrong), Toast.LENGTH_SHORT).show();

                    }
                }

                override fun onFailure(call: Call<UserModel>, t: Throwable) {

                    //Toast.makeText(this@LoginActivity, "" + getResources().getString(R.string.somewentwrong), Toast.LENGTH_SHORT).show();

                    if (t is NoConnectivityException) {
                        t.printStackTrace()

                        //Toast.makeText(NextStepToSignup.this, "" + getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                        //return ResponseFromOffLineDB((Activity) mContext);
                        snackBaropen(resources.getString(R.string.no_internet))
                    } else {
                        //Toast.makeText(NextStepToSignup.this, "" + getResources().getString(R.string.somewentwrong), Toast.LENGTH_SHORT).show();
                        var ss:String=t.toString()
                        Toast.makeText(this@LoginActivity, "" + getResources().getString(R.string.somewentwrong), Toast.LENGTH_SHORT).show();

                    }
                }
            })

        }
    }

    private fun initials() {
        mAPIService = ApiUtils.apiService
    }

    private fun initializeViews() {
        signup_link=findViewById(R.id.signup_link) as TextView
        et_email=findViewById(R.id.et_email) as EditText
        et_password=findViewById(R.id.et_password) as EditText
        signin_btn=findViewById(R.id.signin_btn) as Button
    }
    private fun snackBaropen(msg: String) {
        val snackbar = Snackbar
                .make(et_email, msg, Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY") {
                    if (checkEntry(et_email.text.toString().trim { it <= ' ' }, et_password.text.toString().trim { it <= ' ' })){
                        if (AppData.isNetworkConnected()) {
                            val intent = Intent(applicationContext, NextStepToSignup::class.java)
                            intent.putExtra("email", et_email.text.toString().trim { it <= ' ' })
                            intent.putExtra("password", et_password.text.toString().trim { it <= ' ' })
                            startActivity(intent)
                        } else {
                            snackBaropen(resources.getString(R.string.no_internet))
                        }
                    }
                }

        snackbar.show()
    }

    private fun checkEntry(email: String, password: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        if (et_email.text.toString().trim { it <= ' ' } == "" || !email.matches(emailPattern.toRegex())) {
            Toast.makeText(this, "" + resources.getString(R.string.check_email), Toast.LENGTH_SHORT).show()
            return false
        } else if (password == "" || password.length < 4) {
            Toast.makeText(this, "" + resources.getString(R.string.check_pass), Toast.LENGTH_SHORT).show()
            return false
        } else {
            return true
        }
    }
}
