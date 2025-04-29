package librarytrackingapp.sainithinreddymalkaiahgari.s3463812

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ManageBooksActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ManageBooksScreen()
        }
    }
}


@Composable
fun ManageBooksScreen() {
    var searchQuery by remember { mutableStateOf("") }

    val context = LocalContext.current as Activity
    val userEmail = LibraryTrackerPrefs.getMemberEmail(context)
    var booksList by remember { mutableStateOf(listOf<BookData>()) }
    var loadBooks by remember { mutableStateOf(true) }

    LaunchedEffect(userEmail) {
        getBooks(userEmail) { orders ->
            booksList = orders
            loadBooks = false
        }
    }

    val filteredDonors = booksList.filter {
        it.title.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.top_bar_color))
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        context.finish()
                    },
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "Arrow Back"
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Manage Books",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {



            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search Books (by Name)") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))


            if (loadBooks) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {

                if (filteredDonors.isNotEmpty()) {
                    LazyColumn {
                        items(filteredDonors.chunked(2)) { pair ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                pair.forEach { donor ->
                                    Box(modifier = Modifier.weight(1f)) {
                                        BookItem(donor)
                                    }
                                }
                                // Fill the empty space if items are odd
                                if (pair.size < 2) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                } else {
                    Text(text = "No Books Listed")
                }
            }

        }
    }
}



@Composable
fun BookItem(bookData: BookData) {

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier) {

            if (bookData.imageUrl.isNotEmpty()) {
                Image(
                    bitmap = convertToBitmap(bookData.imageUrl)!!.asImageBitmap(),
                    contentDescription = "Book Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RectangleShape)
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Crop
                )
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = "${bookData.title}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(14.dp),
                        painter = painterResource(id = R.drawable.iv_author),
                        contentDescription = "Author"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${bookData.author}",
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))


                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(14.dp),
                        painter = painterResource(id = R.drawable.iv_genre),
                        contentDescription = "Author"
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "${bookData.genre}",
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            SelectedBook.bookData = bookData
                            context.startActivity(Intent(context, UpdateBooksActivity::class.java))
                        }
                        .background(
                            color = Color.Black,
                            shape = RoundedCornerShape(
                                10.dp
                            )
                        )
                        .border(
                            width = 2.dp,
                            color = colorResource(id = R.color.black),
                            shape = RoundedCornerShape(
                                10.dp
                            )
                        )
                        .padding(vertical = 4.dp, horizontal = 12.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "Update",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = colorResource(id = R.color.white),
                    )
                )

                Spacer(modifier = Modifier.height(6.dp))


            }
        }
    }
}

fun getBooks(userEmail: String, callback: (List<BookData>) -> Unit) {

    val emailKey = userEmail.replace(".", ",")

    val databaseReference = FirebaseDatabase.getInstance().getReference("BooksInShelf/$emailKey")

    databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val bookList = mutableListOf<BookData>()

            for (bookSnapshot in snapshot.children) {
                val book = bookSnapshot.getValue(BookData::class.java)
                book?.let { bookList.add(it) }
            }

            callback(bookList)
        }

        override fun onCancelled(error: DatabaseError) {
            println("Error: ${error.message}")
            callback(emptyList())
        }
    })
}


fun convertToBitmap(base64String: String): Bitmap? {
    val decodedString = Base64.decode(base64String, Base64.DEFAULT)
    val originalBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    return originalBitmap
}