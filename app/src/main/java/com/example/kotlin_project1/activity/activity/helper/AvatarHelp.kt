package com.example.kotlin_project1.activity.activity.helper

import android.os.Bundle
import com.example.kotlin_project1.R
import android.widget.ImageView
class AvatarHelp {

    fun changeavatar(avatar:String?, imageview:ImageView?){
    when(avatar)
    {
        "avatar1" -> imageview!!.setImageResource(R.mipmap.ic_first_avatar)
        "avatar2" -> imageview!!.setImageResource(R.mipmap.ic_second_avatar)
        "avatar3" -> imageview!!.setImageResource(R.mipmap.ic_third_avatar)
        "avatar4" -> imageview!!.setImageResource(R.mipmap.ic_fourth_avatar)
        "avatar5" -> imageview!!.setImageResource(R.mipmap.ic_fifth_avatar)
        "avatar6" -> imageview!!.setImageResource(R.mipmap.ic_sixth_avatar)
    }
}
}