package com.example.habittrackerapp.noteInput

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Note(var title:String, var description:String, var urlImage:String?): Parcelable
