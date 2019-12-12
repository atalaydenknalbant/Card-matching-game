package com.example.kotlin_project1.activity.activity.data

data class UserData(val id:String?, var nickName:String?,var password: String? ,
                    var score: Long?, var highScore:Long?,var avatar:String?    ){
    constructor(): this("","","",0,0,""){

    }



}
