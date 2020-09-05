package com.example.miniproject.component
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.miniproject.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_component.view.*
import java.io.InputStream
import java.lang.Exception
import java.net.URL

class UserCard (context: Context,attributes: AttributeSet) : ConstraintLayout(context,attributes){

    private var userNameView : TextView
    private var locationView : TextView
    private var followersView : TextView
    private var liveIndicatorView : View
    private var button : Button
    private var image : ImageView

    var numberOfFollowers = 0 // Note: the initializer assigns the backing field directly
        set(value) {
            if (value >= 0) field = value
            recomputeView()
        }
    var userName : String = "Artist Name" // Note: the initializer assigns the backing field directly
        set(value) {
            if (value.isNotBlank()) field = value
            recomputeView()
        }
    var location : String = "Location" // Note: the initializer assigns the backing field directly
        set(value) {
            if (value.isNotBlank()) field = value
            recomputeView()
        }
    var isLive : Boolean = false
        set(value){
            field = value
            recomputeView()
        }
    var imageUrl : String = ""
        set(value){
            field = value
            recomputeView()
        }
    var isFollowing = false
        set(value){
            field = value
            recomputeView()
        }


    init {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.user_component,this,true)

        userNameView = view.findViewById(R.id.artist_name_textview)
        locationView = view.findViewById(R.id.location_textview)
        followersView = view.findViewById(R.id.number_of_followers_textview)
        liveIndicatorView = view.findViewById(R.id.live_indicator)
        button = view.findViewById(R.id.button_follow)
        image = view.findViewById(R.id.imageView_user)

        button.setOnClickListener {
            this.isFollowing = !isFollowing
            recomputeView()
        }
        recomputeView()
    }

    fun recomputeView(){

        userNameView.text = userName
        followersView.text = numberOfFollowers.toString() + " Followers"

        if(location.isBlank()){
            locationView.visibility = TextView.GONE
        }else{
            locationView.visibility = TextView.VISIBLE
            locationView.text = location
        }

        if(isLive) {
            liveIndicatorView.visibility = View.VISIBLE
        }else{
            liveIndicatorView.visibility = View.INVISIBLE
        }

        if(isFollowing){
            button.text = "unfollow"
            button.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.unfollow_button_background)));
            button.setTextColor(resources.getColor(R.color.unfollow_button_text))
        }else{
            button.text = "follow"
            button.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.follow_button_background)));
            button.setTextColor(resources.getColor(R.color.follow_button_text))
        }

        try{
            if(imageUrl.isNotBlank()){
                Picasso.get().load("https://media.wired.com/photos/5cdefb92b86e041493d389df/191:100/w_1280,c_limit/Culture-Grumpy-Cat-487386121.jpg").placeholder(R.drawable.avatar_placeholder_light).into(imageView_user);
            }
        }catch (e:Exception){
                print(e.message)
        }

    }

}