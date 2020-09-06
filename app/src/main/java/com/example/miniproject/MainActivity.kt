package com.example.miniproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.doOnTextChanged
import com.example.miniproject.component.UserCard
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        //I had to use a companion object because the MainActivity is recreated each time i switch the NighMode
        //So this is the easiest way to keep track of the Button State
        var isFollowing :Boolean = false
    }
    private val imageUrl = "https://avatars2.githubusercontent.com/u/32805130?s=460&u=9bf0d4fd8e646e771365250d9c818abf51594b78&v=4"
    lateinit var userComponent : UserCard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        switch_theme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        userComponent = userCard as UserCard
        checkBox_isLive.setOnClickListener {
            userComponent.isLive = checkBox_isLive.isChecked
        }
        checkBox_hasImageUrl.setOnClickListener {
            if(checkBox_hasImageUrl.isChecked){
                userComponent.imageUrl = imageUrl
            }else{
                userComponent.imageUrl = null
            }
        }

        editText_name.doOnTextChanged { text,_,_,_ -> userComponent.userName = text.toString() }
        editText_location.doOnTextChanged { text, _,_,_ -> userComponent.location = text.toString() }
        editText_follower.doOnTextChanged { text, _,_,_ -> if(text.toString().isNotBlank())userComponent.numberOfFollowers = Integer.parseInt(text.toString()) else userComponent.numberOfFollowers =0}

        userComponent.adapterOnFollowButtonClick = {
            Handler().postDelayed({
                isFollowing = !isFollowing
                userComponent.isFollowing = isFollowing
            }, 1000)
        }
        userComponent.textFormater = {
            it -> formatText(it)
        }
       
    }

    private fun formatText(number :Int):String{
        if(number==1) return "1 Follower"
        return "$number Followers"
    }

    override fun onResume() {
        super.onResume()
        userComponent.userName = editText_name.text.toString()
        userComponent.isLive = checkBox_isLive.isChecked
        if(checkBox_hasImageUrl.isChecked){
            userComponent.imageUrl = imageUrl
        }else{
            userComponent.imageUrl = null
        }
        userComponent.location = editText_location.text.toString()
        userComponent.numberOfFollowers = Integer.parseInt(editText_follower.text.toString())
        userComponent.isFollowing = isFollowing
    }
}