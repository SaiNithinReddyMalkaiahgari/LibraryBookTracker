package com.example.librarytracking

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

class LibraryProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LibraryProfileScreen()
        }
    }
}

@Composable
fun LibraryProfileScreen() {

    val context = LocalContext.current as Activity

    Column(
        modifier = Modifier.fillMaxSize()
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
                text = "Profile",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                modifier = Modifier
                    .size(120.dp),
                painter = painterResource(id = R.drawable.library),
                contentDescription = "Profile"
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Librarian Name ", modifier = Modifier.weight(1f))
                Text(text = ": ")
                Text(
                    text = "${LibTrackingData.readUserName(context)}",
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Librarian EmailId ", modifier = Modifier.weight(1f))
                Text(text = ": ")
                Text(
                    text = "${LibTrackingData.readMail(context)}",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        LibTrackingData.writeLS(context, false)

                        val intent = Intent(context, LibCheckInActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        context.startActivity(intent)
                        (context as Activity).finish()

                    }
                    .background(color = Color.Black, shape = RoundedCornerShape(10.dp))
                    .border(
                        width = 2.dp,
                        color = colorResource(id = R.color.black),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(vertical = 4.dp, horizontal = 12.dp),
                text = "Logout",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
            )
        }
    }
}