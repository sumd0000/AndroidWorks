package com.test.androidserver

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.test.androidserver.data.AppPreferenceHelper
import com.test.androidserver.model.post.Post
import com.test.androidserver.model.post.SetFavResponse
import com.test.androidserver.network.APIService
import com.test.androidserver.network.ApiUtils
import com.test.androidserver.network.NoConnectivityException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostFullPreview: AppCompatActivity() {
    lateinit var mApiAPIService:APIService
    lateinit var postdetails:Post
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_previewfullpost)
         postdetails= intent.getSerializableExtra("postdetails") as Post
         mApiAPIService=ApiUtils.apiService
         callSetPreview();
    }

    private fun callSetPreview() {
        mApiAPIService!!.callSetPreview(postdetails.postId, AppPreferenceHelper.getInstance().getUserDetails()!!.getUser()!!.get(0).getId()!!).enqueue(object : Callback<SetFavResponse> {
            override fun onResponse(call: Call<SetFavResponse>, response: Response<SetFavResponse>) {
                if (response.isSuccessful) {

                    Toast.makeText(this@PostFullPreview, "success" , Toast.LENGTH_SHORT).show();

                }
                else
                {

                }
            }

            override fun onFailure(call: Call<SetFavResponse>, t: Throwable) {

                if (t is NoConnectivityException) {
                    t.printStackTrace()

                } else {

                }
            }
        })
    }
}