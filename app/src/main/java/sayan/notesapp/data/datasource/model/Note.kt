package sayan.notesapp.data.datasource.model

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NoteEntity")
data class Note(
    var title: String,
    var description: String,
    var isOnline: Boolean = false,
    @DrawableRes var avatar: Int
) {
    @PrimaryKey(autoGenerate = true)
    //var id: Int = 0
    var id: Int? = null
}
