package com.example.miniproject.component
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.miniproject.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_component.view.*

class UserCard (context: Context,attributes: AttributeSet) : ConstraintLayout(context,attributes){

    private var userNameView : TextView
    private var locationView : TextView
    private var followersView : TextView
    private var liveIndicatorView : View
    private var button : Button
    private var image : ImageView

    var adapterOnFollowButtonClick: (()->Unit)? = null

    //TextFormatter will make it easier to do Localization
    var textFormatter: ((Int)->String) = { number -> "$number Followers" } //Default Text Formatter
    var numberOfFollowers = 0 // Note: the initializer assigns the backing field directly
        set(value) {
            if (value >= 0) field = value
            updateNumberOfFollowers()
        }
    var userName : String = ""
        set(value) {
            if (value.isNotBlank()) field = value
            updateUserName()
        }
    var location : String = ""
        set(value) {
            field = value
            updateLocation()
        }
    var isLive : Boolean = false
        set(value){
            field = value
            updateIsLive()
        }
    var imageUrl : String? = null
        set(value){
            field = value
            updateImage()
        }
    var isFollowing = false
        set(value){
            field = value
            updateIsFollowing()
        }
    //Giving the ability to change ButtonText will help in supporting Localization
    var buttonTextFollow : String ="Follow"
        set(value){
            field = value
            if(!isFollowing) button.text = value
        }
    var buttonTextUnFollow : String ="Following"
        set(value){
            field = value
            if(isFollowing) button.text = value
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
            adapterOnFollowButtonClick?.invoke()
        }
        initialiseFields()
    }

    private fun initialiseFields() {
        updateIsFollowing()
        updateImage()
        updateIsLive()
        updateLocation()
        updateUserName()
        updateNumberOfFollowers()
    }

    private fun updateUserName(){
        userNameView.text = userName
    }
    private fun updateNumberOfFollowers(){
        followersView.text = textFormatter(numberOfFollowers)
    }
    private fun updateLocation(){
        if(location.isBlank()){
            locationView.visibility = TextView.GONE
        }else{
            locationView.visibility = TextView.VISIBLE
            locationView.text = location
        }
    }
    private fun updateIsLive(){
        if(isLive) {
            liveIndicatorView.visibility = View.VISIBLE
        }else{
            liveIndicatorView.visibility = View.INVISIBLE
        }
    }
    private fun updateIsFollowing(){
        if(isFollowing){
            button.text = buttonTextUnFollow
            button.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.unfollow_button_background)));
            button.setTextColor(resources.getColor(R.color.unfollow_button_text))
        }else{
            button.text = buttonTextFollow
            button.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.follow_button_background)));
            button.setTextColor(resources.getColor(R.color.follow_button_text))
        }
    }
    private fun updateImage(){
        if(imageUrl!=null){
            Picasso.get().load(imageUrl).error(R.drawable.ic_launcher_background).placeholder(R.drawable.avatar_placeholder_light).into(imageView_user);
        }else{
            Picasso.get().load(R.drawable.avatar_placeholder_light).into(imageView_user)
        }
    }

}