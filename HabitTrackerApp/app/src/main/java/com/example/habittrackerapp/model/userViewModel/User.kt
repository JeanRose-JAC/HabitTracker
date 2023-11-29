package com.example.habittrackerapp.model.userViewModel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
 class User(
    var Email:String
    ) : Parcelable{
     var FirstName:String=""
     var LastName:String=""
     var Gender: String=""
     var ProfilePicture: String =""
     var Password:String=""

    constructor(): this("")
}



