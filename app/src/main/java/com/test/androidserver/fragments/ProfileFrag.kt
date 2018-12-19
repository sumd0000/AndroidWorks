package com.test.androidserver.fragments

import android.app.Activity
import android.content.CursorLoader
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.test.androidserver.R
import com.test.androidserver.data.AppPreferenceHelper
import com.test.androidserver.model.user.DpUploadResponse
import com.test.androidserver.model.user.UserProfile
import com.test.androidserver.network.APIService
import com.test.androidserver.network.ApiUtils
import com.test.androidserver.network.NetworkConstants.BASE_URL
import com.test.androidserver.network.NoConnectivityException
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class ProfileFrag:Fragment()
{
    lateinit var propic:CircleImageView
    lateinit var propic_up:ImageView
    private val TAKE_PICTURE = 1
    lateinit var imageUri:Uri
    var applicationContext =context
    private lateinit var mAPIService: APIService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var rootView: View = inflater.inflate(R.layout.activity_profile, container, false);
        initializeViews(rootView);
        initial();
        functionalities();
        eventLIstner();
        return rootView;
    }

    private fun eventLIstner() {
        propic_up.setOnClickListener {


            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val photo = File(Environment.getExternalStorageDirectory(), "Pic.jpg")
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(photo))
            imageUri = Uri.fromFile(photo)
            startActivityForResult(intent, TAKE_PICTURE)

        }

    }

    private fun functionalities() {
        callTogetprofiledetails();
        //Glide.with(context).load()
    }

    private fun callTogetprofiledetails() {

        mAPIService!!.callProfile(AppPreferenceHelper.getInstance().getUserDetails()!!.getUser()!!.get(0).getId()!!).enqueue(object : Callback<UserProfile> {
            override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {

                if (response.isSuccessful) {

                    var userProfile: UserProfile = UserProfile()
                    userProfile= response.body()!!
                    if(userProfile.success==1)
                    {
                        Toast.makeText(context, "" + userProfile.msg, Toast.LENGTH_SHORT).show();
                        Glide.with(context).load(BASE_URL+ userProfile.users!!.get(0).dp).into(propic)

                    }
                    else
                    {
                        Toast.makeText(context, "" + userProfile.msg, Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(context, "" + getResources().getString(R.string.somewentwrong), Toast.LENGTH_SHORT).show();

                }
            }

            override fun onFailure(call: Call<UserProfile>, t: Throwable) {

                //Toast.makeText(context, "" + getResources().getString(R.string.somewentwrong), Toast.LENGTH_SHORT).show();

                if (t is NoConnectivityException) {
                    t.printStackTrace()

                    //Toast.makeText(NextStepToSignup.this, "" + getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                    //return ResponseFromOffLineDB((Activity) mContext);
                    snackBaropen(resources.getString(R.string.no_internet))
                } else {
                    //Toast.makeText(NextStepToSignup.this, "" + getResources().getString(R.string.somewentwrong), Toast.LENGTH_SHORT).show();
                    var ss:String=t.toString()
                    Toast.makeText(context, "" + getResources().getString(R.string.somewentwrong), Toast.LENGTH_SHORT).show();

                }
            }
        })
    }

    private fun initializeViews(rootView: View) {
        propic= rootView.findViewById(R.id.propic) as CircleImageView
        propic_up=rootView.findViewById(R.id.pic_up_img) as ImageView

    }
    private fun initial() {
        mAPIService= ApiUtils.apiService
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode)
        {
            TAKE_PICTURE->
                if(resultCode==Activity.RESULT_OK)
                {
                    val selectedImage = imageUri
                    context!!.getContentResolver().notifyChange(selectedImage, null)
                    val path=File(selectedImage.path)
                    val cr = context!!.getContentResolver()
                    val bitmap: Bitmap
                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage)
                        Glide.with(context).load(bitmap).into(propic)
                        callServertoUploaddp(selectedImage,path, AppPreferenceHelper.getInstance().getUserDetails()!!.getUser()!!.get(0).getId())
                    } catch (e: Exception) {
                        Toast.makeText(context, "Failed to load", Toast.LENGTH_SHORT)
                                .show()
                        Log.e("Camera", e.toString())
                    }

                }
        }
    }

    private fun callServertoUploaddp(imguri:Uri,filepath: File, user_id: String?)
    {
       // val file =File(getRealPathFromURI(imguri));
        var ss=imguri
        //creating request body for file
        //val requestFile = RequestBody.create(MediaType.parse(context!!.getContentResolver().getType(imguri)), path);
        val descBody = RequestBody.create(MediaType.parse("text/plain"), user_id);
        //creating a call and calling the upload image method

        val requestFile =
        RequestBody.create(MediaType.parse("multipart/form-data"), filepath);

        // MultipartBody.Part is used to send also the actual file name
        val body = MultipartBody.Part.createFormData("image", filepath.getName(), requestFile);
        val user_ids = RequestBody.create(MediaType.parse("multipart/form-data"), user_id)

        val call = mAPIService.uploadImage(user_ids,body)

        //finally performing the call
        call.enqueue(object : Callback<DpUploadResponse> {
            override fun onResponse(call: Call<DpUploadResponse>, response: Response<DpUploadResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "File Uploaded Successfully...", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Some error occurred...", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<DpUploadResponse>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }
        })
        }

    private fun getRealPathFromURI(contentUri: Uri): String {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(context, contentUri, proj, null, null, null)
        val cursor = loader.loadInBackground()
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val result = cursor.getString(column_index)
        cursor.close()
        return result
    }
    private fun snackBaropen(msg: String) {
        val snackbar = Snackbar
                .make(propic, msg, Snackbar.LENGTH_INDEFINITE)

        snackbar.show()
    }
}