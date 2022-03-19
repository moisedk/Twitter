package com.codepath.apps.restclienttemplate.models

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject

class Tweet {
    var body: String = ""
    var createdAt: String = ""
    var user: User? = null

    companion object {
        private fun fromJson(jsonObject: JSONObject): Tweet {
            val tweet = Tweet()
            tweet.body = jsonObject.getString("text")
            tweet.createdAt = jsonObject.getString("created_at")
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"))
            Log.d("Moise", "fromJson: ${jsonObject.getJSONObject("entities")}")

            return tweet
        }
        fun fromJsonArray(jsonArray: JSONArray): List<Tweet>{
            val tweets = ArrayList<Tweet>()
            for(index in 0 until jsonArray.length()){
                tweets.add(fromJson(jsonArray.getJSONObject(index)))
            }

            return tweets
        }
    }
}