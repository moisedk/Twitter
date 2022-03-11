package com.codepath.apps.restclienttemplate.models.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.apps.restclienttemplate.R
import com.codepath.apps.restclienttemplate.models.Tweet

class RecyclerAdapter(private val tweets: ArrayList<Tweet>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val tvUsername: TextView = view.findViewById(R.id.tvUsername)
        private val tvTweetBody: TextView = view.findViewById(R.id.tvTweetBody)
        private val ivProfile: ImageView = view.findViewById(R.id.ivProfileImage)

        fun bind(tweet: Tweet) {
            tvUsername.text = tweet.user?.name
            tvTweetBody.text = tweet.body
            Glide.with(itemView.context)
                .load(tweet.user?.publicImageUrl)
                .into(ivProfile)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tweet = tweets[position]
        holder.bind(tweet)
    }


    override fun getItemCount() = tweets.size

    fun clear() {
        tweets.clear()
        notifyDataSetChanged()
    }

    // Add a list of items -- change to type used
    fun addAll(tweetList: List<Tweet>) {
        tweets.addAll(tweetList)
        notifyDataSetChanged()
    }
}