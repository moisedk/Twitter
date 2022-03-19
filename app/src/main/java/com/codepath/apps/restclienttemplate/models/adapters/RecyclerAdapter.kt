package com.codepath.apps.restclienttemplate.models.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.apps.restclienttemplate.R
import com.codepath.apps.restclienttemplate.models.Tweet
import com.codepath.apps.restclienttemplate.utils.TimeFormatter

class RecyclerAdapter(private val tweets: ArrayList<Tweet>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val tvUsername: TextView = view.findViewById(R.id.tvUsername)
        private val tvTweetBody: TextView = view.findViewById(R.id.tvTweetBody)
        private val ivProfile: ImageView = view.findViewById(R.id.ivProfileImage)
        private val tvTimeStamp: TextView = view.findViewById(R.id.tvTimeStamp)
        private val tvScreenName: TextView = view.findViewById(R.id.tvScreenName)
        private val ivVerifiedBadge: ImageView = view.findViewById(R.id.ivVerifiedBadge)

        fun bind(tweet: Tweet) {
            tvUsername.text = tweet.user?.name
            tvTweetBody.text = tweet.body
            tvScreenName.text = itemView.context.getString(R.string.screen_name, tweet.user?.screenName)
            tvTimeStamp.text = itemView.context.getString(R.string.time_stamp, TimeFormatter.getTimeDifference(tweet.createdAt))
            if (tweet.user!!.verified){
                ivVerifiedBadge.setImageResource(R.drawable.verified_badge)
            }
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

}