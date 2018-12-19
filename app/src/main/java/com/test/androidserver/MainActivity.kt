package com.test.androidserver

import android.os.Bundle
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.test.androidserver.fragments.CreatePostFrag
import com.test.androidserver.fragments.FeedFrag
import com.test.androidserver.fragments.NotificationFrag
import com.test.androidserver.fragments.ProfileFrag
import com.test.androidserver.initial.AppBar
import com.test.androidserver.util.BottomNavigationViewBehavior
import com.test.androidserver.util.BottomNavigationViewHelper


class MainActivity : AppCompatActivity() {
    private val mViewPager: ViewPager? = null
    internal var mainAppBar: AppBar? = null
    internal var tabLayout: TabLayout? = null
    internal var simpleFrameLayout: FrameLayout? = null
    internal var notificationBadge: View? = null
    internal lateinit var navigation: BottomNavigationView
    internal var coordinator_layout:CoordinatorLayout?=null
    companion object {
        var context: Companion = this
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //mainAppBar = supportFragmentManager.findFragmentById(R.id.mainAppBar) as AppBar

        //mainAppBar.setBackEnabled(true);

        navigation = findViewById(R.id.navigation)
        coordinator_layout=findViewById(R.id.coordinator_layout)
        val layoutParams = navigation.getLayoutParams() as CoordinatorLayout.LayoutParams

        BottomNavigationViewHelper.disableShiftMode(navigation)
        layoutParams.behavior = BottomNavigationViewBehavior()

        val bottomNavigationMenuView = navigation.getChildAt(0) as BottomNavigationMenuView
        val v = bottomNavigationMenuView.getChildAt(2)
        val itemView = v as BottomNavigationItemView

        val badge = LayoutInflater.from(this)
                .inflate(R.layout.notification_badge, bottomNavigationMenuView, false)

        itemView.addView(badge)

        navigation.setOnNavigationItemSelectedListener { item ->
            var fragment: Fragment? = null
            when (item.itemId) {

                R.id.navigation_home ->

                    fragment = FeedFrag()
                R.id.navigation_create ->
                    fragment= CreatePostFrag()
                R.id.navigation_notification ->
                    fragment= NotificationFrag()
                R.id.navigation_profile ->
                    fragment= ProfileFrag()
            }
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, fragment)
            transaction.commit()
            true
        }

        //Manually displaying the first fragment - one time only
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, FeedFrag())
        transaction.commit()

    }

}
