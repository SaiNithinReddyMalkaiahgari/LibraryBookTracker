package com.example.librarytracking

import android.app.Activity
import android.os.Bundle
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
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
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

class ManageBorrowersActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BorrowersScreen()
        }
    }
}


@Composable
fun BorrowersScreen() {
    var searchQuery by remember { mutableStateOf("") }

    val context = LocalContext.current as Activity
    val userEmail = LibTrackingData.readMail(context)
    var donorsList by remember { mutableStateOf(listOf<BorrowerData>()) }
    var loadDonors by remember { mutableStateOf(true) }

    LaunchedEffect(userEmail) {
        getBorrowers() { orders ->
            donorsList = orders
            loadDonors = false
        }
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
                text = "Manage Borrowers",
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


            Spacer(modifier = Modifier.height(16.dp))

//            // Donor List
            LazyColumn {
                items(donorsList.size) { donor ->
                    BorrowerItem(donorsList[donor])
                }
            }


        }
    }
}


// Donor Item UI
@Composable
fun BorrowerItem(donor: BorrowerData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier) {


            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = "Borrower Name : ${donor.fullName}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Book Name : ${donor.book}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Borrow Date : ${donor.borrowDate}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

//                Text(
//                    text = "Quantity : ${donor.qunatity}",
//                    fontSize = 16.sp
//                )
            }
        }
    }
}

fun getBorrowers(callback: (List<BorrowerData>) -> Unit) {

    val databaseReference = FirebaseDatabase.getInstance().getReference("BookBorrowers")

    databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val booksList = mutableListOf<BorrowerData>()

            for (donorSnapshot in snapshot.children) {
                for (donationSnapshot in donorSnapshot.children) {
                    val donation = donationSnapshot.getValue(BorrowerData::class.java)
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

