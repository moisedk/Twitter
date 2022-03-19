package com.codepath.apps.restclienttemplate.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codepath.apps.restclienttemplate.R
import com.codepath.apps.restclienttemplate.TwitterApplication
import com.codepath.apps.restclienttemplate.TwitterClient
import com.codepath.apps.restclienttemplate.models.Tweet
import com.codepath.apps.restclienttemplate.models.adapters.EndlessRecyclerViewScrollListener
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
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    private var currentMinId = ""
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
        val layoutManager = LinearLayoutManager(this)
        rvTweets.layoutManager = layoutManager
        adapter = RecyclerAdapter(tweets)
        rvTweets.adapter = adapter

        scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi()
            }
        }

        populateHomeTimeline()

        rvTweets.addOnScrollListener(scrollListener)

    }

    private fun loadNextDataFromApi() {
        client.getHomeTimeline(object: JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.d(TAG, "onFailure: called from loadNextDataFromApi()")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                Log.d(TAG, "onSuccess: called from loadNextDataFromApi()")
                val newTweets: List<Tweet> = Tweet.fromJsonArray(json!!.jsonArray)
                val currentSize = tweets.size
                tweets.addAll(newTweets)
                adapter.notifyItemRangeInserted(currentSize, tweets.size)
            }

        }, id = currentMinId) // This ensure we get older tweets, since we are scrolling down.
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
                    // Get the smallest ID of all the tweets returned from this response.
                    currentMinId = json.jsonArray.getJSONObject(json.jsonArray.length() - 1).getString("id_str")
                    tweets.addAll(Tweet.fromJsonArray(json.jsonArray))
                    adapter.notifyItemRangeInserted(0, json.jsonArray.length())
                    scRefresher.isRefreshing = false
                } catch (e: JSONException){
                    Log.e(TAG, "onSuccess: Exception: $e")
                }
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.compose)
            startActivity(Intent(this, ComposeActivity::class.java))
        return true
    }
}