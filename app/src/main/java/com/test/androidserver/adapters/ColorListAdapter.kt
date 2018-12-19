package com.test.androidserver.adapters

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.test.androidserver.R
import java.util.*

class ColorListAdapter(var colorList: ArrayList<String>) : RecyclerView.Adapter<ColorListAdapter.ViewHolder>() {

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v)
    {
        var color_ll=v.findViewById(R.id.color_ll) as LinearLayout
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ColorListAdapter.ViewHolder {
        // create a new view
        val inflater = LayoutInflater.from(
                parent.context)
        val v = inflater.inflate(R.layout.color_row, parent, false)

        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.color_ll.setBackgroundColor(Color.parseColor(colorList.get(position)))
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return colorList.size
    }

}

