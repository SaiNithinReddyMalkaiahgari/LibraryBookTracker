package com.example.librarytracking

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.firebase.database.FirebaseDatabase
import java.io.ByteArrayOutputStream

class UpdateBooksActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            UpdateBook()
        }
    }
}

object SelectedBook {
    lateinit var bookData: BookData
    lateinit var borrowerItem : BorrowerData
}

@Composable
fun UpdateBook() {
    var title by remember { mutableStateOf(SelectedBook.bookData.title) }
    var author by remember { mutableStateOf(SelectedBook.bookData.bookId) }
    var genre by remember { mutableStateOf(SelectedBook.bookData.genre) }

    var shelfLocation by remember { mutableStateOf(SelectedBook.bookData.shelfLocation) }
    var quantity by remember { mutableStateOf(SelectedBook.bookData.qunatity) }
    var availability by remember { mutableStateOf(SelectedBook.bookData.availability) }

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.top_bar_color))
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                modifier = Modifier
                    .size(36.dp),
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "back arrow"
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                modifier = Modifier
                    .padding(12.dp),
                text = "Update Book",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

//            UploadDonorImage()

            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = { title = it },
                label = { Text("Book Title") },
            )

            Spacer(modifier = Modifier.height(4.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = author,
                onValueChange = { author = it },
                label = { Text("Author") },
            )

            Spacer(modifier = Modifier.height(4.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = genre,
                onValueChange = { genre = it },
                label = { Text("Genre") },
            )

            Spacer(modifier = Modifier.height(4.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = shelfLocation,
                onValueChange = { shelfLocation = it },
                label = { Text("Shelf Location") },
            )

            Spacer(modifier = Modifier.height(4.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text("Quantity") },
            )

            Spacer(modifier = Modifier.height(4.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Availability? ",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = availability == "Available",
                            onClick = { availability = "Available" }
                        )
                        Text("Available", modifier = Modifier.clickable { availability = "Available" })
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = availability == "Not Available",
                            onClick = { availability = "Not Available" }
                        )
                        Text(
                            "Not Available",
                            modifier = Modifier.clickable { availability = "Not Available" })
                    }
                }
            }

            Spacer(modifier = Modifier.height(42.dp))

            Button(
                onClick = {
                    if (title.isEmpty()) {
                        Toast.makeText(context, "Enter all fileds", Toast.LENGTH_SHORT).show()
                    } else {


                        val updatedBook = mapOf(
                            "title" to title,
                            "author" to author,
                            "genre" to genre,
                            "shelfLocation" to shelfLocation,
                            "qunatity" to quantity,
                            "availability" to availability,
                            "imageUrl" to SelectedBook.bookData.imageUrl,
                            "bookId" to SelectedBook.bookData.bookId
                        )

                        updateBookData(SelectedBook.bookData.bookId, updatedBook, context)

                    }
                },
                modifier = Modifier
                    .padding(16.dp, 2.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.p2),
                    contentColor = colorResource(id = R.color.p1)
                )
            ) {
                Text("Update Book")
            }

        }
    }
}


fun updateBookData(bookId: String, updatedData: Map<String, Any>, context: Context) {


    try {
        val emailKey = LibTrackingData.readMail(context)
            .replace(".", ",")
        val path = "BooksInShelf/$emailKey/$bookId"
        FirebaseDatabase.getInstance().getReference(path).updateChildren(updatedData)
            .addOnSuccessListener {
                Toast.makeText(
                    context,
                    "Details Updated Successfully",
                    Toast.LENGTH_SHORT
                ).show()

                (context as Activity).finish()
            }
            .addOnFailureListener {
                Toast.makeText(
                    context,
                    "Failed to update",
                    Toast.LENGTH_SHORT
                ).show()
            }
    } catch (e: Exception) {
        Log.e("Test", "Error Message : ${e.message}")
    }
}