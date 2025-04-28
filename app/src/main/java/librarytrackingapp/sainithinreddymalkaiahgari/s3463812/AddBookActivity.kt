package librarytrackingapp.sainithinreddymalkaiahgari.s3463812

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.database.FirebaseDatabase
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddBookActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AddBookScreen()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddBookScreenP() {
    AddBookScreen()
}

@Composable
fun AddBookScreen() {
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }

    var shelfLocation by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var availability by remember { mutableStateOf("") }



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
                    .clickable {
                        (context as Activity).finish()
                    }
                    .size(36.dp),
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "back arrow"
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                modifier = Modifier
                    .padding(12.dp),
                text = "Add Book",
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

            UploadBookImage()

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
                    if (title.isEmpty()||author.isEmpty()||genre.isEmpty()||shelfLocation.isEmpty()||quantity.isEmpty()||availability.isEmpty()) {
                        Toast.makeText(context, "Enter all fileds", Toast.LENGTH_SHORT).show()
                    } else {

                        if(BookPhoto.isImageSelected) {
                            val inputStream =
                                context.contentResolver.openInputStream(BookPhoto.selImageUri)
                            val bitmap = BitmapFactory.decodeStream(inputStream)
                            val outputStream = ByteArrayOutputStream()
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                            val base64Image =
                                Base64.encodeToString(
                                    outputStream.toByteArray(),
                                    Base64.DEFAULT
                                )


                            val bookData = BookData(
                                title = title,
                                author = author,
                                genre = genre,
                                shelfLocation = shelfLocation,
                                qunatity = quantity,
                                availability = availability,
                                imageUrl = base64Image
                            )

                            uploadBook(bookData, context)
                        }else{
                            Toast.makeText(context, "Upload book image", Toast.LENGTH_SHORT).show()

                        }
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
                Text("Add Book")
            }

        }
    }
}

private fun uploadBook(addBookData: BookData, activityContext: Context) {

    val userEmail = LibraryTrackerPrefs.getMemberEmail(activityContext)
    val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
    val orderId = dateFormat.format(Date())
    addBookData.bookId = orderId

    FirebaseDatabase.getInstance().getReference("BooksInShelf").child(userEmail.replace(".", ","))
        .child(orderId).setValue(addBookData)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(activityContext, "Book Added Successfully", Toast.LENGTH_SHORT)
                    .show()
                (activityContext as Activity).finish()
            } else {
                Toast.makeText(
                    activityContext,
                    "Book Added Failed: ${task.exception?.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        .addOnFailureListener { exception ->
            Toast.makeText(
                activityContext,
                "Book Added Failed: ${exception.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
}

data class BookData(
    var title: String = "",
    var bookId: String = "",
    var author: String = "",
    var genre: String = "",
    var shelfLocation: String = "",
    var qunatity: String = "",
    var availability: String = "",
    var imageUrl: String = ""
)

@Composable
fun UploadBookImage() {
    val activityContext = LocalContext.current

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val captureImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                imageUri = getImageUri(activityContext)
                BookPhoto.selImageUri = imageUri as Uri
                BookPhoto.isImageSelected = true
            } else {
                BookPhoto.isImageSelected = false
                Toast.makeText(activityContext, "Capture Failed", Toast.LENGTH_SHORT).show()
            }
        }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                Toast.makeText(activityContext, "Permission Granted", Toast.LENGTH_SHORT).show()
                captureImageLauncher.launch(getImageUri(activityContext)) // Launch the camera
            } else {
                Toast.makeText(activityContext, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    )

    Column(
        modifier = Modifier.size(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = if (imageUri != null) {
                rememberAsyncImagePainter(model = imageUri)
            } else {
                painterResource(id = R.drawable.ic_add_image)
            },
            contentDescription = "Captured Image",
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .clickable {
                    if (ContextCompat.checkSelfPermission(
                            activityContext,
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        captureImageLauncher.launch(getImageUri(activityContext))
                    } else {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (imageUri == null) {
            Text(text = "Tap the image to capture")
        }
    }
}

fun getImageUri(activityContext: Context): Uri {
    val file = File(activityContext.filesDir, "captured_image.jpg")
    return FileProvider.getUriForFile(
        activityContext,
        "${activityContext.packageName}.fileprovider",
        file
    )
}


object BookPhoto {
    lateinit var selImageUri: Uri
    var isImageSelected = false
}