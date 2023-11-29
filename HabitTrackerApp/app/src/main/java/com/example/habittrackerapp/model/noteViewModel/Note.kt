package com.example.habittrackerapp.model.noteViewModel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Note(var title:String = "", var description:String = "", var urlImage:String? = "", var owner:String ="", val id: String = UUID.randomUUID().toString()
): Parcelable{

}
