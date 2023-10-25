package com.example.habittrackerapp.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var Email:String,
    var FirstName:String,
    var LastName:String,
    var Gender: String,
    var ProfilePicture: String,
    var Password:String,
    var PasswordConfirmation:String
) : Parcelable