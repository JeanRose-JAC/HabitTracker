package com.example.habittrackerapp.model.userViewModel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * this is the user class were we keep of the different information an account in habitMinder should have
 */
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



