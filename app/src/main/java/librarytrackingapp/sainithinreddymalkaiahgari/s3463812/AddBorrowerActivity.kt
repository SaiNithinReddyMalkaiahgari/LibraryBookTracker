package librarytrackingapp.sainithinreddymalkaiahgari.s3463812

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
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
    val userEmail = LibraryTrackerPrefs.getMemberEmail(context)

    var loadBooks by remember { mutableStateOf(true) }

    var allBookData by remember { mutableStateOf(listOf<BookData>()) }




    LaunchedEffect(Unit) {
        getBooks(userEmail) { books ->
            bookList = books
                .filter { it.availability == "Available" }
                .mapNotNull { it.title }

            allBookData = books.filter { it.availability == "Available" }
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
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues()),
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

                    if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || selectedBook.isEmpty() || borrowDate.isEmpty()) {
                        Toast.makeText(context, "Enter all fields", Toast.LENGTH_SHORT).show()
                    } else {

                        val bookToUpdate = allBookData.find { it.title == selectedBook }


                        val borrowerData = BorrowerData(
                            fullName = name,
                            email = email,
                            phoneNumber = phone,
                            book = selectedBook,
                            borrowDate = borrowDate,
                            notes = notes,
                            isReturned = false,
                            bookid = bookToUpdate!!.bookId
                        )

                        addBorrower(borrowerData, allBookData, context)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit")
            }
        }
    }
}



private fun addBorrower(
    borrowerData: BorrowerData,
    allBookData: List<BookData>,
    activityContext: Context
) {
    val userEmail = LibraryTrackerPrefs.getMemberEmail(activityContext)
    val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
    val orderId = dateFormat.format(Date())
    borrowerData.entryId = orderId

    val dbRef = FirebaseDatabase.getInstance()
        .getReference("BookBorrowers")
        .child(userEmail.replace(".", ","))
        .child(orderId)

    dbRef.setValue(borrowerData)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val bookToUpdate = allBookData.find { it.title == borrowerData.book }
                if (bookToUpdate != null && bookToUpdate.qunatity.toInt() > 0) {
                    val updatedQuantity = bookToUpdate.qunatity.toInt() - 1
                    val updateMap = mapOf<String, Any>(
                        "qunatity" to updatedQuantity.toString(),
                        "availability" to if (updatedQuantity == 0) "Not Available" else "Available"
                    )

                    updateBookQuantity(bookToUpdate.bookId, updateMap, activityContext)
                }

                Toast.makeText(activityContext, "Borrower Added Successfully", Toast.LENGTH_SHORT)
                    .show()
                (activityContext as Activity).finish()
            } else {
                Toast.makeText(
                    activityContext,
                    "Borrower Add Failed: ${task.exception?.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        .addOnFailureListener { exception ->
            Toast.makeText(
                activityContext,
                "Borrower Add Failed: ${exception.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
}


fun updateBookQuantity(bookId: String, updatedData: Map<String, Any>, context: Context) {


    try {
        val emailKey = LibraryTrackerPrefs.getMemberEmail(context)
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
    var isReturned: Boolean = false,
    var bookid: String = ""
)

