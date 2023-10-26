package com.example.habittrackerapp.user

import android.os.Parcelable
import kotlinx.coroutines.flow.StateFlow
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var Email:String
    ) : Parcelable{
    lateinit var FirstName:String
    lateinit var LastName:String
    lateinit var Gender: String
    lateinit var ProfilePicture: String
    lateinit var Password:String
    lateinit var PasswordConfirmation:String
}



