package com.test.androidserver.initial

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.test.androidserver.R
import com.test.androidserver.model.SignupResponse
import com.test.androidserver.network.APIService
import com.test.androidserver.network.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NextStepToSignup : AppCompatActivity() {
    internal lateinit var email: String
    internal lateinit var password: String
    internal lateinit var fname: EditText
    internal lateinit var lname: EditText
    internal lateinit var address: EditText
    internal lateinit var city: EditText
    internal lateinit var country: EditText
    internal lateinit var postcode: EditText
    internal lateinit var ph_number: EditText
    internal lateinit var signup_btn: Button
    private var mAPIService: APIService? = null
    internal var dialog: ProgressDialog? = null
    internal lateinit var coordinatorLayout: CoordinatorLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_signup)
        initializeViews()
        init()
        eventListner()
        getIntentData()
    }

    private fun init() {
        mAPIService = ApiUtils.apiService
    }

    private fun eventListner() {

        signup_btn.setOnClickListener {
            if (checkSignup(fname.text.toString().trim { it <= ' ' }, lname.text.toString().trim { it <= ' ' }, address.text.toString().trim { it <= ' ' }, city.text.toString().trim { it <= ' ' }, country.text.toString().trim { it <= ' ' }, postcode.text.toString().trim { it <= ' ' }, ph_number.text.toString().trim { it <= ' ' })) {
                openLoader()
                val full_name = fname.text.toString().trim { it <= ' ' } + " " + lname.text.toString().trim { it <= ' ' }
                callSignupServer(full_name, address.text.toString().trim { it <= ' ' }, city.text.toString().trim { it <= ' ' }, country.text.toString().trim { it <= ' ' }, postcode.text.toString().trim { it <= ' ' }, ph_number.text.toString().trim { it <= ' ' })
            }
        }
    }

    fun openLoader() {
        dialog = ProgressDialog(this)
        dialog!!.setCancelable(false)
        //dialog.setTitle("Signing up");
        dialog!!.setMessage("Please wait...")
        dialog!!.show()
    }

    fun closeLoader() {
        if (dialog != null) {
            dialog!!.dismiss()
        }
    }

    private fun callSignupServer(full_name: String, address: String, city: String, country: String, postcode: String, ph_number: String) {

        val tsLong = System.currentTimeMillis() / 1000
        val timestamps = tsLong.toString()


        mAPIService!!.callSignup(email, password, full_name, address, city, country, postcode, ph_number,timestamps).enqueue(object : Callback<SignupResponse> {
            override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {

                if (response.isSuccessful) {

                    val sss = response.body().toString()
                    if (response.body()!!.success==1) {
                        Toast.makeText(this@NextStepToSignup, "" + resources.getString(R.string.signup_success), Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext, LoginActivity::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(this@NextStepToSignup, "" + response.body()!!.msg!!, Toast.LENGTH_SHORT).show()
                    }
                }
                else
                {
                    Toast.makeText(this@NextStepToSignup, "" + getResources().getString(R.string.somewentwrong), Toast.LENGTH_SHORT).show();


                }
            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {

                Toast.makeText(this@NextStepToSignup, "" + getResources().getString(R.string.somewentwrong), Toast.LENGTH_SHORT).show();

                /*if (t is NoConnectivityException) {
                    t.printStackTrace()

                    //Toast.makeText(NextStepToSignup.this, "" + getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                    //return ResponseFromOffLineDB((Activity) mContext);
                } else {
                    //Toast.makeText(NextStepToSignup.this, "" + getResources().getString(R.string.somewentwrong), Toast.LENGTH_SHORT).show();
                    var ss:String=t.toString()
                    Toast.makeText(this@NextStepToSignup, "" + getResources().getString(R.string.somewentwrong), Toast.LENGTH_SHORT).show();

                }*/
            }
        })
    }

    private fun snackBaropen(msg: String) {
        val snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY") {
                    //if (checkSignup(fname.text.toString().trim (' '), lname.text.toString().trim { it <= ' ' }, address.text.toString().trim { it <= ' ' }, city.text.toString().trim { it <= ' ' }, country.text.toString().trim { it <= ' ' }, postcode.text.toString().trim { it <= ' ' }, ph_number.text.toString().trim { it <= ' ' })) {
                        //openLoader()
                        val full_name = fname.text.toString().trim { it <= ' ' } + " " + lname.text.toString().trim { it <= ' ' }
                        callSignupServer(full_name, address.text.toString().trim { it <= ' ' }, city.text.toString().trim { it <= ' ' }, country.text.toString().trim { it <= ' ' }, postcode.text.toString().trim { it <= ' ' }, ph_number.text.toString().trim { it <= ' ' })
                    //}
                }

        snackbar.show()
    }

    private fun checkSignup(fname: String, lname: String, address: String, city: String, country: String, postcode: String, ph_no: String): Boolean {

        if (fname == "") {
            Toast.makeText(this, "" + resources.getString(R.string.name_blankcheck), Toast.LENGTH_SHORT).show()
            return false
        } else if (lname == "") {
            Toast.makeText(this, "" + resources.getString(R.string.name_blankcheck), Toast.LENGTH_SHORT).show()
            return false
        } else if (address == "") {
            Toast.makeText(this, "" + resources.getString(R.string.address_blankcheck), Toast.LENGTH_SHORT).show()
            return false
        } else if (city == "") {
            Toast.makeText(this, "" + resources.getString(R.string.city_blankcheck), Toast.LENGTH_SHORT).show()
            return false
        } else if (country == "") {
            Toast.makeText(this, "" + resources.getString(R.string.name_blankcheck), Toast.LENGTH_SHORT).show()
            return false
        } else if (postcode == "") {
            Toast.makeText(this, "" + resources.getString(R.string.postcode_blankcheck), Toast.LENGTH_SHORT).show()
            return false
        } else if (ph_no == "") {
            return false
        } else if (ph_no.length > 0 && ph_no.length < 10) {
            Toast.makeText(this, "" + resources.getString(R.string.phno_lengthcheck), Toast.LENGTH_SHORT).show()
            return false
        } else {
            return true
        }
    }

    private fun initializeViews() {

        fname = findViewById<View>(R.id.et_fname) as EditText
        lname = findViewById<View>(R.id.et_lname) as EditText
        address = findViewById<View>(R.id.et_address) as EditText
        city = findViewById<View>(R.id.et_city) as EditText
        country = findViewById<View>(R.id.et_country) as EditText
        postcode = findViewById<View>(R.id.et_pincode) as EditText
        ph_number = findViewById<View>(R.id.et_phno) as EditText
        signup_btn = findViewById<View>(R.id.signup_btn) as Button
        coordinatorLayout = findViewById<View>(R.id.coordinatorLayout) as CoordinatorLayout

    }

    private fun getIntentData() {

        email = intent.extras!!.getString("email")
        password = intent.extras!!.getString("password")
    }
}
