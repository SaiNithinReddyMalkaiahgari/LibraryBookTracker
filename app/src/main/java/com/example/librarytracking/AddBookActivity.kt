package com.example.librarytracking

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.core.content.ContextCompat.startActivity

class AddBookActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                .padding(12.dp)
        ) {

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

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = availability,
                onValueChange = { availability = it },
                label = { Text("Availability") },
            )

            Spacer(modifier = Modifier.height(42.dp))

            Button(
                onClick = {

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