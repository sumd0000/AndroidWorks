package com.test.androidserver.initial

import android.content.Context
import android.os.Bundle

import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.test.androidserver.R

class AppBar : Fragment() {

    var toolbar: Toolbar? = null
    var activity: AppCompatActivity? = null
    var activeActionBar: ActionBar? = null
        private set
    internal lateinit var cancel_tv: TextView


    override fun onAttach(context: Context?) {
        super.onAttach(context)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout_texts for this fragment
        activity = getActivity() as AppCompatActivity

        val view = inflater.inflate(R.layout.activity_app_bar, container, false)
        toolbar = view.findViewById(R.id.toolbar)
        cancel_tv = view.findViewById<View>(R.id.cancel) as TextView
        activity!!.setSupportActionBar(toolbar)
        activeActionBar = activity!!.supportActionBar

        activeActionBar!!.setDisplayShowTitleEnabled(false)
        activeActionBar!!.setIcon(R.drawable.poem)
        setHasOptionsMenu(true)
        return view
    }

    /* public void setAppbarTitle(String title){
        if(null != actionBar){
            actionBar.setTitle(Html.fromHtml("<font color=\"#ffffff\">" + title));

        }
    }*/
    fun setBackEnabled(option: Boolean?) {
        if (null != activeActionBar) {
            activeActionBar!!.setIcon(R.drawable.poem)
            activeActionBar!!.setDisplayHomeAsUpEnabled(option!!)
            //actionBar.setIcon(R.drawable.arrow);

        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //return super.onOptionsItemSelected(item);

        when (item!!.itemId) {

            android.R.id.home -> {
                getActivity()!!.finish()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }

    }
}
