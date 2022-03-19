package com.codepath.apps.restclienttemplate.models

import org.json.JSONObject

class User {
    var name: String = ""
    var screenName: String = ""
    var publicImageUrl: String = ""
    var verified: Boolean = false

    companion object{
        fun fromJson(jsonObject: JSONObject): User{ // This jsonObject is a "user" jsonObject on its own
            val user = User()
            user.name = jsonObject.getString("name")
            user.screenName = jsonObject.getString("screen_name")
            user.publicImageUrl = jsonObject.getString("profile_image_url_https")
            user.verified = jsonObject.getBoolean("verified")

            return user

        }
    }
}