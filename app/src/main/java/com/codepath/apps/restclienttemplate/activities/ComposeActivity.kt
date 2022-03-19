package com.codepath.apps.restclienttemplate.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.codepath.apps.restclienttemplate.R

class ComposeActivity : AppCompatActivity() {
    private lateinit var etCompose: EditText
    private lateinit var btnTweet: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)
        etCompose = findViewById(R.id.etCompose)
        btnTweet = findViewById(R.id.btnTweet)

        btnTweet.setOnClickListener {

            val tweetContent = etCompose.text.toString()
            if (tweetContent.isEmpty())
                Toast
                    .makeText(
                        this,
                        "You can't send out an empty tweet, dickhead",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            else if (tweetContent.length > 140)
                Toast
                    .makeText(
                        this,
                        "You are tweeting too much text, chill out man",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            else
                Toast.makeText(this, tweetContent, Toast.LENGTH_SHORT).show()
        }

    }
}