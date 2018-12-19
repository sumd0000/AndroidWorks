package com.test.androidserver.fragments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.test.androidserver.R
import com.test.androidserver.adapters.PostListAdapter
import com.test.androidserver.interfaces.UpdateViewForAllAdapter
import com.test.androidserver.model.post.AllPostModel
import com.test.androidserver.model.post.SetFavResponse
import com.test.androidserver.network.APIService
import com.test.androidserver.network.ApiUtils
import com.test.androidserver.network.NoConnectivityException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedFrag : Fragment(),UpdateViewForAllAdapter {

    lateinit var post_list_rv:RecyclerView
    private lateinit var mAPIService: APIService
    lateinit var post_idd:String
    lateinit var fav_idd: String
    lateinit var types: String
    lateinit var main_lay:LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var rootView: View = inflater.inflate(R.layout.activity_feeds, container, false);
        initializeViews(rootView);
        initial();
        clickEvents();
        return rootView;
    }

    private fun clickEvents() {

    }

    private fun initial() {
        mAPIService=ApiUtils.apiService
        calltoGetAllPost();
    }

    private fun calltoGetAllPost() {
        mAPIService!!.getAllposts().enqueue(object : Callback<AllPostModel> {
            override fun onResponse(call: Call<AllPostModel>, response: Response<AllPostModel>) {

                if (response.isSuccessful) {

                    var allPostModel: AllPostModel = AllPostModel()
                    allPostModel= response.body()!!
                    if(allPostModel.getSuccess()==1)
                    {
                        post_list_rv.layoutManager=LinearLayoutManager(context,LinearLayout.VERTICAL,false)
                        var postListAdapter:PostListAdapter= PostListAdapter(context,allPostModel,mAPIService)
                        postListAdapter.addOvserver(this@FeedFrag)
                        post_list_rv.adapter=postListAdapter

                    }
                    else
                    {
                        Toast.makeText(context, "" + allPostModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(activity, "" + getResources().getString(R.string.somewentwrong), Toast.LENGTH_SHORT).show();
                }
            }

            override fun onFailure(call: Call<AllPostModel>, t: Throwable) {

                //Toast.makeText(this@LoginActivity, "" + getResources().getString(R.string.somewentwrong), Toast.LENGTH_SHORT).show();

                if (t is NoConnectivityException) {
                    t.printStackTrace()
                    snackBaropen(resources.getString(R.string.no_internet),"callGetAllPost")
                } else {
                    var ss:String=t.toString()
                    if(context!=null) {
                        Toast.makeText(activity, "" + getResources().getString(R.string.somewentwrong), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })
    }

    private fun initializeViews(rootView: View) {

        post_list_rv=rootView.findViewById<View>(R.id.post_list_rv) as RecyclerView
        main_lay=rootView.findViewById(R.id.main_lay) as LinearLayout
    }
    private fun snackBaropen(msg: String, service_name: String) {
        val snackbar = Snackbar
                .make(main_lay, msg, Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY") {
                    if(service_name.equals("callGetAllPost")) {
                        calltoGetAllPost();
                    }
                    else if(service_name.equals("callSetfav")) {
                        var updateViewForAllAdapterobserver:UpdateViewForAllAdapter?=null
                        updateViewForAllAdapterobserver!!.callSetFavourite(post_idd,fav_idd,types);

                    }
                }

        snackbar.show()
    }

    override fun callSetFavourite(post_id: String?, fav_id: String?, type: String?) {
        post_idd= post_id!!
        fav_idd=fav_id!!
        types=type!!
        mAPIService!!.callSetFavourite(post_id,fav_id, type!!).enqueue(object : Callback<SetFavResponse> {
            override fun onResponse(call: Call<SetFavResponse>, response: Response<SetFavResponse>) {

                if (response.isSuccessful) {

                    var favResponse:SetFavResponse= SetFavResponse()
                    favResponse= response.body()!!
                    if(favResponse.success==1)
                    {
                        Toast.makeText(context, "" + favResponse.msg, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(context, "" + favResponse.msg, Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(context, "" + context!!.getResources().getString(R.string.somewentwrong), Toast.LENGTH_SHORT).show();

                }
            }

            override fun onFailure(call: Call<SetFavResponse>, t: Throwable) {

                //Toast.makeText(this@LoginActivity, "" + getResources().getString(R.string.somewentwrong), Toast.LENGTH_SHORT).show();

                if (t is NoConnectivityException) {
                    t.printStackTrace()

                    //Toast.makeText(NextStepToSignup.this, "" + getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                    //return ResponseFromOffLineDB((Activity) mContext);
                    snackBaropen(resources.getString(R.string.no_internet), "callSetfav")
                } else {
                    //Toast.makeText(NextStepToSignup.this, "" + getResources().getString(R.string.somewentwrong), Toast.LENGTH_SHORT).show();
                    var ss:String=t.toString()
                    Toast.makeText(context, "" + context!!.getResources().getString(R.string.somewentwrong), Toast.LENGTH_SHORT).show();

                }
            }
        })

    }

    }
