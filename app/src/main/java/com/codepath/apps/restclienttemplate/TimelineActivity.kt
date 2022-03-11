package com.codepath.apps.restclienttemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
private const val TAG = "TimelineActivity"
class TimelineActivity : AppCompatActivity() {
    private lateinit var client: TwitterClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)

        client = TwitterApplication.getRestClient(this)
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

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                Log.e(TAG, "onSuccess: request succeeded $json")
            }

        })

    }
}