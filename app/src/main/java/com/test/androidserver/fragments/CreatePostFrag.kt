package com.test.androidserver.fragments

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.chinalwb.are.AREditor
import com.test.androidserver.R
import com.test.androidserver.ShowTextViewActivity
import com.test.androidserver.ShowTextViewActivity.HTML_TEXT
import com.test.androidserver.data.AppPreferenceHelper
import com.test.androidserver.model.CreatePostModel
import com.test.androidserver.network.APIService
import com.test.androidserver.network.ApiUtils
import com.test.androidserver.network.NoConnectivityException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CreatePostFrag: Fragment()
{
    lateinit var arEditor: AREditor
    lateinit var areditor_title:AREditor
    lateinit var preview:ImageView
    lateinit var create_btn:Button
    private lateinit var mAPIService: APIService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var rootView: View = inflater.inflate(R.layout.activity_createpost, container, false);
        initializeViews(rootView);
        initial()
        clickEvents()
        return rootView
}

    private fun clickEvents() {
        preview.setOnClickListener {
            var html = ""
            html = this.arEditor.html
            val intent = Intent(activity, ShowTextViewActivity::class.java)
            intent.putExtra(HTML_TEXT, html)
            startActivity(intent)
        }
        create_btn.setOnClickListener {

            callserverToCreatePost();
        }
    }

    private fun callserverToCreatePost() {
       var user_id:String= AppPreferenceHelper.getInstance().getUserDetails()!!.getUser()!!.get(0).getId()!!
        var user_name:String=AppPreferenceHelper.getInstance().getUserDetails()!!.getUser()!!.get(0).getFullName()!!;
        //var user_dp:String= AppPreferenceHelper.getInstance().getUserDetails()!!.getUser()!!.get(0).getDp()!!;
        var user_loc:String=AppPreferenceHelper.getInstance().getUserDetails()!!.getUser()!!.get(0).getCity()!!+" "+AppPreferenceHelper.getInstance().getUserDetails()!!.getUser()!!.get(0).getCountry();

        var title_html = ""
        title_html = this.areditor_title.html

        var post_title:String=title_html

        var postdesc_html = ""
        postdesc_html = this.arEditor.html
        var post_desc:String=postdesc_html
        var language_id:String="2"
        var favourites:String="4"
        var views:String="5"
        var comment_count="88"
        var post_date="444"
        var priority="5"

        mAPIService!!.callCreatePost(user_id,user_name,"sdaod",user_loc,post_title,post_desc,language_id,favourites,views,comment_count,post_date,priority).enqueue(object : Callback<CreatePostModel> {
            override fun onResponse(call: Call<CreatePostModel>, response: Response<CreatePostModel>) {

                if (response.isSuccessful) {

                    var createPostmodel: CreatePostModel = CreatePostModel()
                    createPostmodel= response.body()!!
                    if(createPostmodel.success==1)
                    {
                        Toast.makeText(activity, "" + createPostmodel.msg, Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(activity, "" + createPostmodel.msg, Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(activity, "" + getResources().getString(R.string.somewentwrong), Toast.LENGTH_SHORT).show();
                }
            }

            override fun onFailure(call: Call<CreatePostModel>, t: Throwable) {

                //Toast.makeText(this@LoginActivity, "" + getResources().getString(R.string.somewentwrong), Toast.LENGTH_SHORT).show();

                if (t is NoConnectivityException) {
                    t.printStackTrace()
                    snackBaropen(resources.getString(R.string.no_internet))
                } else {
                    var ss:String=t.toString()
                    if(context!=null) {
                        Toast.makeText(activity, "" + getResources().getString(R.string.somewentwrong), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })
    }
    private fun snackBaropen(msg: String) {
        val snackbar = Snackbar
                .make(create_btn, msg, Snackbar.LENGTH_INDEFINITE)

        snackbar.show()
    }
    private fun initializeViews(rootView: View) {
        arEditor = rootView.findViewById(R.id.areditor)
        preview=rootView.findViewById(R.id.preview)
        areditor_title=rootView.findViewById(R.id.areditor_title)
        create_btn=rootView.findViewById(R.id.create_btn)
    }

    private fun initial() {
        mAPIService=ApiUtils.apiService
        areditor_title.mAre.setHint("Title here")
        areditor_title.toolbarSizeSmall()

    }
}
