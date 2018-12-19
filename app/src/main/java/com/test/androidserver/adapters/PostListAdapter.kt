package com.test.androidserver.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.chinalwb.are.render.AreTextView
import com.test.androidserver.PostFullPreview
import com.test.androidserver.R
import com.test.androidserver.data.AppPreferenceHelper
import com.test.androidserver.interfaces.UpdateViewForAllAdapter
import com.test.androidserver.model.post.AllPostModel
import com.test.androidserver.model.post.Post
import com.test.androidserver.network.APIService
import com.test.androidserver.network.NetworkConstants.BASE_URL

class PostListAdapter(var context: Context?, var allPostModel: AllPostModel, var mAPIService: APIService) : RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    var updateViewForAllAdapterobserver:UpdateViewForAllAdapter?=null
    fun addOvserver(updateViewForAllAdapterobserver:UpdateViewForAllAdapter)
    {
        this.updateViewForAllAdapterobserver=updateViewForAllAdapterobserver
    }
    init {

    }
    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v)
    {
        var user_dp=v.findViewById(R.id.user_dp) as ImageView
        var user_name=v.findViewById(R.id.user_name) as TextView
        var user_loc=v.findViewById(R.id.user_loc) as TextView
        var post_txt=v.findViewById(R.id.post_txt) as AreTextView
        var post_bg=v.findViewById(R.id.post_bg) as LinearLayout
        var preview_btn=v.findViewById(R.id.preview_btn) as Button
        var favourite=v.findViewById(R.id.favourite) as ImageView
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): PostListAdapter.ViewHolder {
        // create a new view
        val inflater = LayoutInflater.from(
                parent.context)
        val v = inflater.inflate(R.layout.post_row, parent, false)

        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var rootObj:Post=allPostModel.post.get(position)
        Glide.with(holder.itemView.context).load(BASE_URL+rootObj.userDp).into(holder.user_dp)
        holder.user_name.setText(rootObj.userName)
        holder.user_loc.setText(rootObj.userLoc)
        //holder.post_txt.setText(rootObj.postDesc)
        holder.post_txt.fromHtml(rootObj.postDesc)

        holder.preview_btn.setOnClickListener {

            val intent = Intent(context,PostFullPreview::class.java)
            intent.putExtra("postdetails",allPostModel.post.get(position))
            context!!.startActivity(intent)
        }

        holder.favourite.setOnClickListener {
            var post_id:String=allPostModel.post.get(0).postId
            var fav_id: String? =AppPreferenceHelper.getInstance().getUserDetails()!!.getUser()!!.get(0).getId()
            if (holder.favourite.drawable.getConstantState() == context!!.getResources().getDrawable( R.drawable.favourite_grey).getConstantState())
            {
                holder.favourite.setImageResource(R.drawable.favourite_red)
                //callSetFavourite("update");
                updateViewForAllAdapterobserver!!.callSetFavourite(post_id,fav_id,"update");
            }
            else
            {
                holder.favourite.setImageResource(R.drawable.favourite_grey)
                updateViewForAllAdapterobserver!!.callSetFavourite(post_id,fav_id,"remove");
            }
        }
    }

    /*private fun callSetFavourite( type: String) {
        var post_id:String=allPostModel.post.get(0).postId
        var fav_id: String? =AppPreferenceHelper.getInstance().getUserDetails()!!.getUser()!!.get(0).getId()

        mAPIService!!.callSetFavourite(post_id,fav_id,type).enqueue(object : Callback<SetFavResponse> {
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
                    snackBaropen(resources.getString(R.string.no_internet))
                } else {
                    //Toast.makeText(NextStepToSignup.this, "" + getResources().getString(R.string.somewentwrong), Toast.LENGTH_SHORT).show();
                    var ss:String=t.toString()
                    Toast.makeText(context, "" + context.getResources().getString(R.string.somewentwrong), Toast.LENGTH_SHORT).show();

                }
            }
        })
        
    }*/

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return allPostModel.post.size
    }

    public fun suman()
    {
        Toast.makeText(context, "Its calling ", Toast.LENGTH_SHORT).show()
    }

}
