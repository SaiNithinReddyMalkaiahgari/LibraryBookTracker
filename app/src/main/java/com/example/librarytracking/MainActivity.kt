package com.example.librarytracking

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(::checkLoginStatusAndGo)
        }
    }

    private fun checkLoginStatusAndGo(value: Int) {

        when (value) {
            1 -> {
                startActivity(Intent(this, LibraryHomectivity::class.java))
                finish()
            }

            2 -> {
                startActivity(Intent(this, LibCheckInActivity::class.java))
                finish()
            }

            3 -> {
                startActivity(Intent(this, LibRegisterActivity::class.java))
                finish()
            }
        }
    }
}

@Composable
fun MainScreen(onLoginClick: (value: Int) -> Unit) {
    var showSplash by remember { mutableStateOf(true) }

    val context = LocalContext.current as Activity

    LaunchedEffect(Unit) {
        delay(3000) // 3 seconds delay
        showSplash = false
    }

    if (showSplash) {
        SplashScreen()
    } else {

        val currentStatus = LibTrackingData.readLS(context)

        if (currentStatus) {
            onLoginClick.invoke(1)
        } else {
            onLoginClick.invoke(2)
        }
    }
}

@Composable
fun SplashScreen() {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.p1)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.weight(1f))


            Image(
                painter = painterResource(id = R.drawable.library),
                contentDescription = "Library Tracking App",
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Library Tracking App",
                color = colorResource(id = R.color.p2),
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 6.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = "Sai Nithin Reddy",
                color = colorResource(id = R.color.p2),
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 18.dp)
                    .align(Alignment.CenterHorizontally)
            )


        }
    }

}

