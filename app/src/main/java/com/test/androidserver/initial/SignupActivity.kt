package com.test.androidserver.initial

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.test.androidserver.R
import com.test.androidserver.data.AppData

class SignupActivity : AppCompatActivity() {
    internal lateinit var save_btn: Button
    internal lateinit var et_email: EditText
    internal lateinit var et_create_pass: EditText
    internal lateinit var et_pass_confirm: EditText
    internal lateinit var signin_txt: TextView
    internal lateinit var coordinatorLayout: CoordinatorLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        initializeViews()
        eventListner()
    }

    private fun eventListner() {
        save_btn.setOnClickListener {
           // if (checkEntry(et_email.text.toString().trim { it <= ' ' }, et_create_pass.text.toString().trim { it <= ' ' }, et_pass_confirm.text.toString().trim { it <= ' ' })) {
                    var ss:Boolean=AppData.isNetworkConnected();

                if (AppData.isNetworkConnected()) {
                    val intent = Intent(applicationContext, NextStepToSignup::class.java)
                    intent.putExtra("email", et_email.text.toString().trim { it <= ' ' })
                    intent.putExtra("password", et_pass_confirm.text.toString().trim { it <= ' ' })
                    startActivity(intent)
                } else {
                    snackBaropen(resources.getString(R.string.no_internet))
                }
            //}
        }

        signin_txt.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
        }
    }

    private fun checkEntry(email: String, create_pass: String, pass_confirm: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        if (et_email.text.toString().trim { it <= ' ' } == "" || !email.matches(emailPattern.toRegex())) {
            Toast.makeText(this, "" + resources.getString(R.string.check_email), Toast.LENGTH_SHORT).show()
            return false
        } else if (create_pass == "" || create_pass.length < 8) {
            Toast.makeText(this, "" + resources.getString(R.string.check_pass), Toast.LENGTH_SHORT).show()
            return false
        } else if (pass_confirm == "" || pass_confirm != create_pass) {
            Toast.makeText(this, "" + resources.getString(R.string.check_confirm_pass), Toast.LENGTH_SHORT).show()
            return false
        } else {
            return true
        }
    }

    private fun initializeViews() {
        save_btn = findViewById<View>(R.id.save_btn) as Button
        et_email = findViewById<View>(R.id.et_email) as EditText
        et_create_pass = findViewById<View>(R.id.et_createpass) as EditText
        et_pass_confirm = findViewById<View>(R.id.et_confirmpass) as EditText
        signin_txt = findViewById<View>(R.id.signin_txt) as TextView
        coordinatorLayout = findViewById<View>(R.id.coordinatorLayout) as CoordinatorLayout
    }

    private fun snackBaropen(msg: String) {
        val snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY") {
                    if (checkEntry(et_email.text.toString().trim { it <= ' ' }, et_create_pass.text.toString().trim { it <= ' ' }, et_pass_confirm.text.toString().trim { it <= ' ' })) {
                        if (AppData.isNetworkConnected()) {
                            val intent = Intent(applicationContext, NextStepToSignup::class.java)
                            intent.putExtra("email", et_email.text.toString().trim { it <= ' ' })
                            intent.putExtra("password", et_pass_confirm.text.toString().trim { it <= ' ' })
                            startActivity(intent)
                        } else {
                            snackBaropen(resources.getString(R.string.no_internet))
                        }
                    }
                }

        snackbar.show()
    }
}
