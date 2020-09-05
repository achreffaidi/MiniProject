package com.example.miniproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.doOnTextChanged
import com.example.miniproject.component.UserCard
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        switch_theme.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        val x = userCard as UserCard
        checkBox_isLive.setOnClickListener {
            x.isLive = checkBox_isLive.isChecked
        }

        editText_name.doOnTextChanged { text, start, count, after -> x.userName = text.toString() }
        editText_location.doOnTextChanged { text, start, count, after -> x.location = text.toString() }
        editText_follower.doOnTextChanged { text, start, count, after -> x.numberOfFollowers = Integer.parseInt(text.toString()) }

        x.userName = "Achref Faidi"
        x.isLive = checkBox_isLive.isChecked
        x.location = "Tunis"
        x.numberOfFollowers = 255
        x.imageUrl ="https://static01.nyt.com/images/2020/04/22/science/22VIRUS-PETCATS1/22VIRUS-PETCATS1-videoSixteenByNineJumbo1600.jpg"
    }
}