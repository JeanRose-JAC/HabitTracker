package com.example.habittrackerapp.noteInput

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.R


/**
 * Displays all the elements of the StringBuilder list in a card container*/
@Composable
fun DisplayNotesList(notesList: SnapshotStateList<Note>)
{
    val navController= LocalNavController.current
    LazyColumn{
        itemsIndexed(notesList) { index, note ->
            Card(  modifier = Modifier
                .padding(16.dp),
                shape = RoundedCornerShape(7.dp))
            {

                    Row(modifier = Modifier.padding(10.dp)) {

                        //makes the image into an icon
                        AsyncImage(
                            model = note.urlImage,
                            error= painterResource(id = R.drawable.no_image_placeholder),
                            modifier = Modifier
                                .size(5.dp)
                                .padding(5.dp)
                                .clip(MaterialTheme.shapes.small),
                            contentScale = ContentScale.Crop,
                            contentDescription = null
                        )
                        Column(modifier = Modifier.clickable {
                            navController.navigate("SingleNoteScreenRoute/${index}")
                        }.fillMaxWidth()
                            .padding(10.dp))
                        {
                            Text(
                                text = note.title,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = note.description, fontFamily = FontFamily.Monospace
                            )
                            Button(onClick = {notesList.remove(note)},
                                modifier = Modifier
                                    .padding(0.dp, 0.dp, 20.dp, 0.dp)
                                    .align(Alignment.End)){
                                Text(text = "remove")
                            }
                        }
                    }

            }

            Divider(modifier = Modifier.fillMaxWidth())//Separates each item
        }
    }

}


//Taken from slide 30 AppDev2_Day_11-12-13_final
@Composable
fun <T: Any> rememberMutableStateListOf(vararg elements: T): SnapshotStateList<T> {
    return rememberSaveable(
        saver = listSaver(
            save = { stateList ->
                if (stateList.isNotEmpty()) {
                    val first = stateList.first()
                    if (!canBeSaved(first)) {
                        throw IllegalStateException("${first::class} cannot be saved. By default only types which can be stored in the Bundle class can be saved.")
                    }
                }
                stateList.toList()
            },
            restore = { it.toMutableStateList() }
        )
    ) {
        elements.toList().toMutableStateList()
    }
}

