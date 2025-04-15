package com.example.librarytracking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class AddBorrowerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AddBookBorrowerScreen()
        }
    }
}


@Composable
fun AddBookBorrowerScreen() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var bookTitle by remember { mutableStateOf("") }
    var bookId by remember { mutableStateOf("") }
    var borrowDate by remember { mutableStateOf("") }
    var returnDate by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
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
                text = "Add Book Borrower",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") })
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") })
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone Number") })
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = bookTitle,
                onValueChange = { bookTitle = it },
                label = { Text("Book Title") })
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = bookId,
                onValueChange = { bookId = it },
                label = { Text("Book ID") })

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = borrowDate,
                onValueChange = { borrowDate = it },
                label = { Text("Borrow Date (YYYY-MM-DD)") },
                placeholder = { Text("2025-04-15") }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = returnDate,
                onValueChange = { returnDate = it },
                label = { Text("Return Date (YYYY-MM-DD)") },
                placeholder = { Text("2025-04-30") }
            )
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes (optional)") },
                maxLines = 3,
                modifier = Modifier.height(100.dp).fillMaxWidth()
            )

            Button(
                onClick = {
                    // handle submission
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit")
            }
        }
    }
}

