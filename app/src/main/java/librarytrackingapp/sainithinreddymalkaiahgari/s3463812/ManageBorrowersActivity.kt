package librarytrackingapp.sainithinreddymalkaiahgari.s3463812

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
    val userEmail = LibraryTrackerPrefs.getMemberEmail(context)
    var booksList by remember { mutableStateOf(listOf<BorrowerData>()) }
    var loadBorrowers by remember { mutableStateOf(true) }

    var selectedBorrower by remember { mutableStateOf<BorrowerData?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    var allBookData by remember { mutableStateOf(listOf<BookData>()) }

    var loadBooks by remember { mutableStateOf(true) }

    LaunchedEffect(userEmail) {
        getBorrowers(userEmail) { orders ->
            booksList = orders
            loadBorrowers = false
        }

        getBooks(userEmail) { books ->
            allBookData = books
            loadBooks = true
        }
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

            if (loadBorrowers && loadBooks) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {

                if (booksList.isNotEmpty()) {

                    LazyColumn {
                        items(booksList.size) { donor ->
                            BorrowerItem(booksList[donor]) {
                                selectedBorrower = it
                                showDialog = true
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                    }

                    if (showDialog && selectedBorrower != null) {
                        ConfirmReturnDialog(
                            borrowerData = selectedBorrower!!,
                            onDismiss = { showDialog = false },
                            onConfirm = { borrower ->
                                markAsReturned(userEmail, context, borrower, allBookData)
                                showDialog = false
                            }
                        )
                    }

                } else {
                    Text(text = "No Borrowers Found")
                }
            }

        }
    }
}

fun markAsReturned(
    accountMail: String,
    context: Context,
    borrower: BorrowerData,
    allBookData: List<BookData>
) {
    val emailKey = accountMail.replace(".", ",")
    val borrowerId = borrower.entryId ?: return

    val databaseReference = FirebaseDatabase.getInstance()
        .getReference("BookBorrowers/$emailKey/$borrowerId")

    val updates = mapOf<String, Any>(
        "returned" to true
    )

    databaseReference.updateChildren(updates)
        .addOnSuccessListener {


            val book = allBookData.find { it.title == borrower.book }
            if (book != null) {
                val newQuantity = book.qunatity.toInt() + 1
                val updateMap = mapOf<String, Any>(
                    "qunatity" to newQuantity.toString(),
                    "availability" to if (newQuantity > 0) "Available" else "Not Available"
                )

                updateBookQuantity(book.bookId, updateMap, context)
            }
        }
        .addOnFailureListener {
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show()
        }
}




@Composable
fun ConfirmReturnDialog(
    borrowerData: BorrowerData,
    onDismiss: () -> Unit,
    onConfirm: (BorrowerData) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Mark as Returned?") },
        text = { Text("Are you sure you want to mark '${borrowerData.book}' as returned?") },
        confirmButton = {
            TextButton(onClick = { onConfirm(borrowerData) }) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("No")
            }
        }
    )
}


@Composable
fun BorrowerItem(borrowerItem: BorrowerData, onUpdateClicked: (BorrowerData) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp)
            ) {
                Text(
                    text = "Borrower Name : ${borrowerItem.fullName}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Book Name : ${borrowerItem.book}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Date of Borrow: ${borrowerItem.borrowDate}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                val retStatus = if (borrowerItem.isReturned) "Returned" else "With Borrower"
                Text(text = "Status : $retStatus", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }

            if (!borrowerItem.isReturned)
                Text(
                    modifier = Modifier
                        .clickable { onUpdateClicked(borrowerItem) }
                        .background(color = Color.Black, shape = RoundedCornerShape(10.dp))
                        .border(
                            width = 2.dp,
                            color = colorResource(id = R.color.black),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(vertical = 4.dp, horizontal = 12.dp)
                        .align(Alignment.CenterVertically),
                    text = "Update",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
                )

            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}


fun getBorrowers(userEmail: String, callback: (List<BorrowerData>) -> Unit) {

    val emailKey = userEmail.replace(".", ",")
    val databaseReference = FirebaseDatabase.getInstance().getReference("BookBorrowers/$emailKey")

    databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val borrowersList = mutableListOf<BorrowerData>()

            for (borrowerSnap in snapshot.children) {
                val book = borrowerSnap.getValue(BorrowerData::class.java)
                book?.let { borrowersList.add(it) }
            }

            callback(borrowersList)
        }

        override fun onCancelled(error: DatabaseError) {
            println("Error: ${error.message}")
            callback(emptyList())
        }
    })
}

