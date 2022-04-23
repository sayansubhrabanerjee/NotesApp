package sayan.notesapp.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import sayan.notesapp.ui.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import sayan.notesapp.R
import sayan.notesapp.data.datasource.model.Note
import sayan.notesapp.ui.theme.Green
import sayan.notesapp.ui.theme.NotesAppTheme

@AndroidEntryPoint
class NotesActivity : ComponentActivity() {

    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Screen(
                        note = Note(
                            title = "Shopping",
                            description = "Sunday",
                            isOnline = true,
                            avatar = R.drawable.ic_launcher_foreground
                        ),
                        noteViewModel = noteViewModel,
                        lifecycleScope = lifecycleScope
                    )
                }
            }
        }
    }
}

private fun insert(
    note: Note,
    noteViewModel: NoteViewModel,
    lifecycleScope: LifecycleCoroutineScope
) {
    lifecycleScope.launchWhenStarted {
        noteViewModel.insert(note)
    }
}

private fun onlineStatus(item: Note, index: Int) {
    item.isOnline = index % 2 == 0 //TODO this line helps to change isOnline status and colour
}

private fun days(index: Int): String =
    when (index % 7) {
        1 -> "TuesDay"
        2 -> "Monday"
        3 -> "Sunday"
        4 -> "Saturday"
        5 -> "Friday"
        6 -> "Thursday"
        0 -> "Wednesday"
        else -> ""
    }

private fun notes(index: Int): String =
    when (index % 7) {
        1 -> "Shopping"
        2 -> "Grocery"
        3 -> "Painting"
        4 -> "Cooking"
        5 -> "Reading"
        6 -> "Hiking"
        0 -> "Binge-Watching"
        else -> ""
    }

private fun buildNotesItems(item: Note, index: Int) {
    item.title = notes(index)
    item.description = days(index)
    onlineStatus(item, index)
}

@Composable
private fun Screen(
    note: Note,
    lifecycleScope: LifecycleCoroutineScope,
    noteViewModel: NoteViewModel
) {
    Surface(modifier = Modifier.fillMaxWidth()) {
        Scaffold(
            topBar = { AppBar("Home", Icons.Default.Home) },
            floatingActionButton = {
                val fabState = remember { mutableStateOf(false) }
                FloatingActionButton(onClick = { fabState.value = true }) {
                    AddNotes(note, noteViewModel, lifecycleScope, fabState)
                    Icon(imageVector = Icons.Default.Add, contentDescription = "")
                }
            }
        ) {
            NotesRecyclerView(noteViewModel = noteViewModel)
        }
    }
}


@Composable
private fun AppBar(title: String, image: ImageVector) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            Icon(
                modifier = Modifier.padding(start = 20.dp),
                imageVector = image,
                contentDescription = ""
            )
        }
    )
}

@Composable
private fun AddNotes(
    note: Note,
    noteViewModel: NoteViewModel,
    lifecycleScope: LifecycleCoroutineScope,
    fabState: MutableState<Boolean>
) {
    if (fabState.value) {
        insert(note, noteViewModel, lifecycleScope)
        fabState.value = false
    }
}

@Composable
private fun NotesRecyclerView(noteViewModel: NoteViewModel) {
    LazyColumn {
        itemsIndexed(items = noteViewModel.notes.value) { index, item ->
            buildNotesItems(item, index)
            EachRow(note = item)
        }
    }
}

@Composable
private fun EachRow(note: Note) {
    Card(
        modifier = Modifier
            .height(200.dp)
            .padding(20.dp),
        shape = CutCornerShape(12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
        ) {
            ProfileAvatar(note = note)
            ProfileContent(note = note)
        }
    }
}


@Composable
private fun ProfileAvatar(note: Note) {
    Card(
        modifier = Modifier.size(100.dp),
        shape = CircleShape,
        border = BorderStroke(
            width = 2.dp,
            color = if (note.isOnline) MaterialTheme.colors.Green else Color.Red
        )
    ) {
        //TODO Loading the image with Coil Library. Also, images won't work in emulator. To make it work we have kept usesCleartextTraffic=true in manifest.
        Image(
            painter = rememberImagePainter(note.avatar) {
                transformations(CircleCropTransformation())
                crossfade(true)
            },
            contentDescription = ""
        )
    }
}

@Composable
private fun ProfileContent(note: Note) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,

        ) {
        Text(
            text = note.title,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.Yellow
        )
        Text(
            text = note.description,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Color.Cyan
        )
    }
}