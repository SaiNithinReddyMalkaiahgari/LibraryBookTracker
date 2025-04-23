package com.example.librarytracking

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
            DonorSearchScreen()
        }
    }
}


@Composable
fun DonorSearchScreen() {
    var searchQuery by remember { mutableStateOf("") }

    val context = LocalContext.current as Activity
    val userEmail = LibTrackingData.readMail(context)
    var donorsList by remember { mutableStateOf(listOf<BookData>()) }
    var loadDonors by remember { mutableStateOf(true) }

    LaunchedEffect(userEmail) {
        getBooks() { orders ->
            donorsList = orders
            loadDonors = false
        }
    }

    val filteredDonors = donorsList.filter {
        it.title.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.Red
                )
                .padding(12.dp),
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


            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search Books (by Name)") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

//            // Donor List
//            LazyColumn {
//                items(filteredDonors.size) { donor ->
//                    BookItem(filteredDonors[donor])
//                }
//            }

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

        }
    }
}


// Donor Item UI
@Composable
fun BookItem(donor: BookData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier) {

            if (donor.imageUrl.isNotEmpty()) {
                Image(
                    bitmap = decodeBase64ToBitmap(donor.imageUrl)!!.asImageBitmap(),
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
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                Text(
                    text = "${donor.title}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){

                    Image(
                        modifier = Modifier.size(14.dp),
                        painter = painterResource(id = R.drawable.iv_author),
                        contentDescription = "Author"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${donor.author}",
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        modifier = Modifier.size(14.dp),
                        painter = painterResource(id = R.drawable.iv_genre),
                        contentDescription = "Author"
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "${donor.genre}",
                        fontSize = 14.sp
                    )

                }

                Spacer(modifier = Modifier.height(6.dp))

//                Text(
//                    text = "Quantity : ${donor.qunatity}",
//                    fontSize = 16.sp
//                )
            }
        }
    }
}

fun getBooks(callback: (List<BookData>) -> Unit) {

    val databaseReference = FirebaseDatabase.getInstance().getReference("BooksInShelf")

    databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val booksList = mutableListOf<BookData>()

            for (donorSnapshot in snapshot.children) {
                for (donationSnapshot in donorSnapshot.children) {
                    val donation = donationSnapshot.getValue(BookData::class.java)
                    donation?.let { booksList.add(it) }
                }
            }

            callback(booksList)
        }

        override fun onCancelled(error: DatabaseError) {
            println("Error: ${error.message}")
            callback(emptyList())
        }
    })
}


fun decodeBase64ToBitmap(base64String: String): Bitmap? {
    val decodedString = Base64.decode(base64String, Base64.DEFAULT)
    val originalBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    return originalBitmap
}