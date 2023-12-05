package com.example.habittrackerapp.note

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.habittrackerapp.R
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.model.noteViewModel.NotesViewModel
import com.example.habittrackerapp.model.noteViewModel.NotesViewModelFactory


@Composable
fun ViewSingleNote(id: String,
                   notesViewModel: NotesViewModel = viewModel(factory= NotesViewModelFactory())
) {
    val navController = LocalNavController.current
    val note by notesViewModel.currentNote.collectAsState()
    notesViewModel.getNote(id)

    var imageUri by rememberSaveable {
        mutableStateOf<Uri?>(null)
    }

    Column{
        if (note.id == id) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            {
                Button(onClick = { navController.navigate(Routes.ViewList.route)},
                    modifier = Modifier
                        .padding(top=10.dp),
                    colors =  ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)) {
                    Text("View List",  modifier = Modifier.padding(start=30.dp,end=30.dp));
                }

                Button(
                    onClick = { navController.navigate("EditNoteScreenRoute/${note.id}") },
                    modifier = Modifier
                        .padding(start=10.dp,top=10.dp),
                    colors =  ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Text(text = "edit",  modifier = Modifier
                        .padding(start=40.dp,end=40.dp))
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp)
                    .padding(16.dp, top = 2.dp, 16.dp, 16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .verticalScroll(rememberScrollState())
                )
                {

//                    val imgBytes = Base64.decode(note.urlImage, 0)
//                    val image= BitmapFactory.decodeByteArray(imgBytes,0,imgBytes.size)
//                    println(image.toString())
                    println(note.urlImage)

//                    Image(
//                        bitmap = image.asImageBitmap(),
//                        contentDescription = "user image",
//                        modifier = Modifier.padding(20.dp) )

                    if(note.urlImage == "null"){
                        Image(
                            painter = painterResource(id = R.drawable.no_image_placeholder),
                            //error= painterResource(id = R.drawable.no_image_placeholder),
                            contentDescription = "user image",
                            modifier = Modifier.padding(20.dp) )
                        println("image: "  + note.urlImage)
                    }
                    else{
                        imageUri = note.urlImage?.toUri()
                        imageUri?.let { LoadImage(it) }
                    }

                    //title text field
                    Text(
                        text = note.title,
                        modifier = Modifier
                            .padding(20.dp, 0.dp)
                            .fillMaxWidth()
                    )
                    //body/content description text field
                    Text(
                        text = note.description,
                        modifier = Modifier
                            .height(250.dp)
                            .padding(20.dp, 0.dp),
                    )
                    //add remove button and edit at the top??
                }
            }
        }
    }
}

@Composable
fun LoadImage(imageUri:Uri) {
    val context = LocalContext.current
    val bitmap =  rememberSaveable {
        mutableStateOf<Bitmap?>(null)
    }

    imageUri?.let {
        if (Build.VERSION.SDK_INT < 28) {
            bitmap.value = MediaStore.Images
                .Media.getBitmap(context.contentResolver,it)

        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver,it)
            bitmap.value = ImageDecoder.decodeBitmap(source)
        }

        println(bitmap.value.toString())
        bitmap.value?.let {  btm ->
            Image(bitmap = btm.asImageBitmap(),
                contentDescription =null,
                modifier = Modifier.size(20.dp))
        }
    }


}


