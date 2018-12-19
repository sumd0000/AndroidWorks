package com.test.androidserver.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.androidserver.R
import com.test.androidserver.network.APIService
import com.test.androidserver.network.ApiUtils


class NotificationFrag:Fragment()
{

    private lateinit var mAPIService: APIService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var rootView: View = inflater.inflate(R.layout.activity_notification, container, false);
        initializeViews(rootView);
        initial();
        functionalities();
        eventLIstner();
        return rootView;
    }

    private fun eventLIstner() {

    }

    private fun functionalities() {

    }



    private fun initializeViews(rootView: View) {

    }
    private fun initial() {
        mAPIService= ApiUtils.apiService
    }


}