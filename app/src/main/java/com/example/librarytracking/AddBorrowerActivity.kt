package com.example.librarytracking

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

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
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var borrowDate by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    var selectedBook by remember { mutableStateOf("") }
    var bookList by remember { mutableStateOf(listOf<String>()) }

    var showDatePicker by remember { mutableStateOf(false) }
    val userEmail = LibTrackingData.readMail(context)

    var loadBooks by remember { mutableStateOf(true) }


    // Fetch book titles from Firebase
//    LaunchedEffect(Unit) {
//        getBooks(userEmail) { books ->
//            bookList = books.mapNotNull { it.title }
//        }
//    }

    LaunchedEffect(Unit) {
        getBooks(userEmail) { books ->
            bookList = books
                .filter { it.availability == "Available" }
                .mapNotNull { it.title }
            loadBooks = false
        }
    }


    // Show DatePicker
    if (showDatePicker) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            context,
            { _, year, month, day ->
                borrowDate = String.format("%04d-%02d-%02d", year, month + 1, day)
                showDatePicker = false
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.top_bar_color))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "Back",
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        (context as Activity).finish()
                    }
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Add Book Borrower",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {

            // Form Fields
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth()
            )

            // 🔽 Using your custom dropdown component
            DropdownMenuBookTitles(
                types = bookList,
                selectedType = selectedBook,
                onTypeSelected = { selectedBook = it }
            )
            if (loadBooks) {
                Text(text = "Loading books...")
            }

            OutlinedTextField(
                value = borrowDate,
                onValueChange = {},
                label = { Text("Borrow Date") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker = true },
                readOnly = true,
                enabled = false
            )

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes (optional)") },
                maxLines = 3,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )

            Button(
                onClick = {
                    val borrowerData = BorrowerData(
                        fullName = name,
                        email = email,
                        phoneNumber = phone,
                        book = selectedBook,
                        borrowDate = borrowDate,
                        notes = notes,
                        isReturned = false
                    )

                    addBorrower(borrowerData, context)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit")
            }
        }
    }
}


private fun addBorrower(borrowerData: BorrowerData, activityContext: Context) {

    val userEmail = LibTrackingData.readMail(activityContext)
    val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
    val orderId = dateFormat.format(Date())
    borrowerData.entryId = orderId

    FirebaseDatabase.getInstance().getReference("BookBorrowers").child(userEmail.replace(".", ","))
        .child(orderId).setValue(borrowerData)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(activityContext, "Borrower Added Successfully", Toast.LENGTH_SHORT)
                    .show()
                (activityContext as Activity).finish()
            } else {
                Toast.makeText(
                    activityContext,
                    "Borrower Added Failed: ${task.exception?.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        .addOnFailureListener { exception ->
            Toast.makeText(
                activityContext,
                "Borrower Added Failed: ${exception.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuBookTitles(
    types: List<String>,
    selectedType: String,
    onTypeSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedType,
            onValueChange = {},
            readOnly = true,
            label = { Text("Select Book") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor() // Important for anchoring the dropdown
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            types.forEach { type ->

                DropdownMenuItem(
                    text = { Text(type) },
                    onClick = {
                        onTypeSelected(type)
                        expanded = false
                    }
                )
            }
        }
    }
}


data class BorrowerData(
    var fullName: String = "",
    var email: String = "",
    var phoneNumber: String = "",
    var book: String = "",
    var borrowDate: String = "",
    var notes: String = "",
    var entryId: String = "",
    var isReturned: Boolean = false
)

