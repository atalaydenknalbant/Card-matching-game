package com.example.kotlin_project1.activity.activity.data

data class UserData(var email:String?, var nickName:String?,var pw: String? ,
                    var score: Long?, var highScore:Long?,var avatar:String?    ){
    constructor(): this("","","",0,0,""){

    }



}
