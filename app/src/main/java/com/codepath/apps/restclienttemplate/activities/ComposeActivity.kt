package com.codepath.apps.restclienttemplate.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.codepath.apps.restclienttemplate.R
import com.codepath.apps.restclienttemplate.TwitterApplication
import com.codepath.apps.restclienttemplate.TwitterClient
import com.codepath.apps.restclienttemplate.models.Tweet
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

private const val TAG: String = "ComposeActivity"

class ComposeActivity : AppCompatActivity() {
    private lateinit var etCompose: EditText
    private lateinit var tvCharCount: TextView
    private lateinit var btnTweet: Button
    private lateinit var client: TwitterClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)
        btnTweet = findViewById(R.id.btnTweet)
        tvCharCount = findViewById(R.id.tvCharCount)
        tvCharCount.text = getString(R.string.char_count, "0")
        client = TwitterApplication.getRestClient(this)
        etCompose = findViewById(R.id.etCompose)
        etCompose.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val wordCount = p0.toString().length
                tvCharCount.text = getString(R.string.char_count, wordCount.toString())
                btnTweet.isEnabled = wordCount <= 280
            }

        })
        btnTweet.setOnClickListener {
            val tweetContent = etCompose.text.toString()
            when {
                tweetContent.isEmpty() -> Toast
                    .makeText(
                        this,
                        "You can't send out an empty tweet, dickhead",
                        Toast.LENGTH_SHORT
                    )
                    .show()
                tweetContent.length > 140 -> Toast
                    .makeText(
                        this,
                        "You are tweeting too much text, chill out man",
                        Toast.LENGTH_SHORT
                    )
                    .show()
                else -> client.publishTweet(tweetContent, object : JsonHttpResponseHandler() {
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        response: String?,
                        throwable: Throwable?
                    ) {
                        Log.d(TAG, "onFailure: $statusCode")
                    }

                    override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                        Log.d(TAG, "onSuccess: $statusCode")
                        val tweet = Tweet.fromJson(json.jsonObject)
                        val intent = Intent()
                        intent.putExtra("TWEET", tweet)
                        setResult(RESULT_OK, intent)
                        finish()
                    }
                })
            }
        }

    }
}