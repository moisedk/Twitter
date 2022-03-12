package com.codepath.apps.restclienttemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codepath.apps.restclienttemplate.models.Tweet
import com.codepath.apps.restclienttemplate.models.adapters.RecyclerAdapter
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

private const val TAG = "TimelineActivity"
class TimelineActivity : AppCompatActivity() {
    private lateinit var scRefresher: SwipeRefreshLayout
    private lateinit var rvTweets: RecyclerView
    private lateinit var adapter: RecyclerAdapter
    private lateinit var client: TwitterClient
    var tweets = ArrayList<Tweet>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)
        client = TwitterApplication.getRestClient(this)
        scRefresher = findViewById(R.id.scRefresher)
        scRefresher.setOnRefreshListener {
            populateHomeTimeline()
        }

        // Configure the refreshing colors
        scRefresher.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light)
        rvTweets = findViewById(R.id.rvTweets)
        rvTweets.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerAdapter(tweets)
        rvTweets.adapter = adapter

        populateHomeTimeline()

    }

    private fun populateHomeTimeline(){
        client.getHomeTimeline(object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "onFailure: request failed! $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.e(TAG, "onSuccess: request succeeded $json")
                try {
                    adapter.clear()
                    tweets.addAll(Tweet.fromJsonArray(json.jsonArray))
                    adapter.notifyItemRangeInserted(0, json.jsonArray.length())
                    scRefresher.isRefreshing = false
                } catch (e: JSONException){
                    Log.e(TAG, "onSuccess: $e", )
                }
            }

        })

    }
}