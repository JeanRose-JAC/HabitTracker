package com.example.habittrackerapp.model.noteViewModel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID
/**
 * Class that defines what a note item is.
 * Each note has title, description, url image, owner, and id.
 * */
@Parcelize
data class Note(var title:String = "", var description:String = "", var urlImage:String? = "", var owner:String ="", val id: String = UUID.randomUUID().toString()
): Parcelable{

}
