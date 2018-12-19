package com.test.androidserver.initial

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.test.androidserver.MainActivity
import com.test.androidserver.R
import com.test.androidserver.data.AppPreferenceHelper

class SplashScreenActivity : AppCompatActivity() {
    internal var permissionsRequired = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    private val txtPermissions: TextView? = null
    private val btnCheckPermissions: Button? = null
    private var permissionStatus: SharedPreferences? = null
    private var sentToSettings = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        Handler().postDelayed({
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkPermissions()
            }
        },
                SPLASH_TIME_OUT.toLong()
        )
    }

    private fun checkPermissions() {
        permissionStatus = getSharedPreferences("permissionStatus", Context.MODE_PRIVATE)

        if (ActivityCompat.checkSelfPermission(this@SplashScreenActivity, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this@SplashScreenActivity, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@SplashScreenActivity, permissionsRequired[0]) || ActivityCompat.shouldShowRequestPermissionRationale(this@SplashScreenActivity, permissionsRequired[1])) {
                //Show Information about why you need the permission
                val builder = AlertDialog.Builder(this@SplashScreenActivity)
                builder.setTitle("Need Permissions")
                builder.setMessage("This app needs Location permission.")
                builder.setPositiveButton("Grant") { dialog, which ->
                    dialog.cancel()
                    ActivityCompat.requestPermissions(this@SplashScreenActivity, permissionsRequired, PERMISSION_CALLBACK_CONSTANT)
                }
                builder.setNegativeButton("Cancel") { dialog, which ->
                    dialog.cancel()
                    finish()
                }
                builder.show()
            } else if (permissionStatus!!.getBoolean(permissionsRequired[0], false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                val builder = AlertDialog.Builder(this@SplashScreenActivity)
                builder.setTitle("Need Multiple Permissions")
                builder.setMessage("This app needs Location permission.")
                builder.setPositiveButton("Grant") { dialog, which ->
                    dialog.cancel()
                    sentToSettings = true
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivityForResult(intent, REQUEST_PERMISSION_SETTING)
                    Toast.makeText(baseContext, "Go to Permissions to Grant Location", Toast.LENGTH_LONG).show()
                }
                builder.setNegativeButton("Cancel") { dialog, which ->
                    dialog.cancel()
                    finish()
                }
                builder.show()
            } else {
                //just request the permission
                ActivityCompat.requestPermissions(this@SplashScreenActivity, permissionsRequired, PERMISSION_CALLBACK_CONSTANT)
            }

            //txtPermissions.setText("Permissions Required");

            val editor = permissionStatus!!.edit()
            editor.putBoolean(permissionsRequired[0], true)
            editor.commit()
        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            //check if all permissions are granted
            var allgranted = false
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true
                } else {
                    allgranted = false
                    break
                }
            }

            if (allgranted) {
                proceedAfterPermission()
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this@SplashScreenActivity, permissionsRequired[0]) || ActivityCompat.shouldShowRequestPermissionRationale(this@SplashScreenActivity, permissionsRequired[1])) {
                //txtPermissions.setText("Permissions Required");
                Toast.makeText(this, "Permissions Required", Toast.LENGTH_SHORT).show()
                val builder = AlertDialog.Builder(this@SplashScreenActivity)
                builder.setTitle("Need Permissions")
                builder.setMessage("This app needs Location permission.")
                builder.setPositiveButton("Grant") { dialog, which ->
                    dialog.cancel()
                    ActivityCompat.requestPermissions(this@SplashScreenActivity, permissionsRequired, PERMISSION_CALLBACK_CONSTANT)
                }
                builder.setNegativeButton("Cancel") { dialog, which ->
                    dialog.cancel()
                    finish()
                }
                builder.show()
            } else {
                Toast.makeText(baseContext, "Unable to get Permission", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(this@SplashScreenActivity, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission()
            }
        }
    }

    private fun proceedAfterPermission() {
        if(AppPreferenceHelper.getInstance().getUserDetails()==null)
        {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
        else
        {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onPostResume() {
        super.onPostResume()
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(this@SplashScreenActivity, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission()
            }
        }
    }

    companion object {

        var SPLASH_TIME_OUT = 1000
        private val PERMISSION_CALLBACK_CONSTANT = 100
        private val REQUEST_PERMISSION_SETTING = 101
    }

}
