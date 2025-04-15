package com.example.librarytracking

import android.content.Intent
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class LibraryHomectivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LibraryHomeScreen()
        }
    }
}


@Composable
fun LibraryHomeScreenOld() {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Purple Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(bottomEnd = 32.dp, bottomStart = 32.dp))
                    .background(Color.Black) // Purple color
            ) {
                // Image and Arrow Button
                Column(
                    modifier = Modifier.align(Alignment.Center)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.library), // Replace with your image
                        contentDescription = "Library Image",
                        modifier = Modifier
                            .size(150.dp) // Adjust size as needed
                    )

                    Text(
                        text = "Library Tracking App",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )

                }

                IconButton(
                    onClick = {
//                        context.startActivity(Intent(context, LoginActivity::class.java))
                    },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp)
                        .background(Color.Red, CircleShape) // Yellow background
                        .size(40.dp) // Adjust size as needed
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle, // Use a forward arrow
                        contentDescription = "Arrow",
                        tint = Color.White
                    )
                }
            }

            // Login Form Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(Color.White)
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .padding(top = 12.dp, bottom = 0.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        Column(
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp)
                                .clickable {

                                },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.library),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .height(42.dp)
                                    .width(42.dp)
                            )
                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Add Book",
                                fontSize = 10.sp,
                                textAlign = TextAlign.Center
                            )
                        }

                        Column(
                            modifier = Modifier
                                .width(42.dp)
                                .height(42.dp)
                                .clickable {

                                },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.library),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .height(42.dp)
                                    .width(42.dp)
                            )
                            Spacer(modifier = Modifier.height(6.dp))


                            Text(
                                text = "Add Readers",
                                fontSize = 10.sp,
                                textAlign = TextAlign.Center
                            )
                        }

                        Column(
                            modifier = Modifier
                                .width(42.dp)
                                .height(42.dp)
                                .clickable {

                                },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.library),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .height(42.dp)
                                    .width(42.dp)
                            )
                            Spacer(modifier = Modifier.height(6.dp))


                            Text(
                                text = "Manage Books",
                                fontSize = 10.sp,
                                textAlign = TextAlign.Center
                            )
                        }


                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .padding(top = 16.dp, bottom = 0.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        Column(
                            modifier = Modifier
                                .width(42.dp)
                                .height(42.dp)
                                .clickable {

                                },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.library),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .height(42.dp)
                                    .width(42.dp)
                            )
                            Spacer(modifier = Modifier.height(6.dp))


                            Text(
                                text = "Delete Book",
                                fontSize = 10.sp,
                                textAlign = TextAlign.Center
                            )
                        }

                        Column(
                            modifier = Modifier
                                .width(42.dp)
                                .height(42.dp)
                                .clickable {

                                },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.library),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .height(42.dp)
                                    .width(42.dp)
                            )
                            Spacer(modifier = Modifier.height(6.dp))


                            Text(
                                text = "Summary",
                                fontSize = 10.sp,
                                textAlign = TextAlign.Center
                            )
                        }

                        Column(
                            modifier = Modifier
                                .width(42.dp)
                                .height(42.dp)
                                .clickable {

                                },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.library),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .height(42.dp)
                                    .width(42.dp)
                            )
                            Spacer(modifier = Modifier.height(6.dp))


                            Text(
                                text = "My Profile",
                                fontSize = 10.sp,
                                textAlign = TextAlign.Center
                            )
                        }

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LibraryHomeScreenP() {
    LibraryHomeScreen()
}

@Composable
fun LibraryHomeScreen() {
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
                painter = painterResource(id = R.drawable.library),
                contentDescription = "Library"
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                modifier = Modifier
                    .padding(12.dp),
                text = "Library Tracking App",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )

        }

        Row(
            modifier = Modifier
                .clickable {
                    context.startActivity(Intent(context, AddBookActivity::class.java))

                }
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .background(
                    color = colorResource(id = R.color.p2),
                    shape = RoundedCornerShape(6.dp)
                )
                .border(
                    width = 1.dp,
                    color = colorResource(id = R.color.p2),
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(modifier = Modifier.width(20.dp))

            Image(
                modifier = Modifier
                    .size(64.dp),
                painter = painterResource(id = R.drawable.library),
                contentDescription = "Library"
            )

            Spacer(modifier = Modifier.width(40.dp))


            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Add Books",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                modifier = Modifier
                    .size(36.dp),
                painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_48),
                contentDescription = "arrow"
            )

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    context.startActivity(Intent(context, AddBorrowerActivity::class.java))
                }
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .background(
                    color = colorResource(id = R.color.p2),
                    shape = RoundedCornerShape(6.dp)
                )
                .border(
                    width = 1.dp,
                    color = colorResource(id = R.color.p2),
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(modifier = Modifier.width(20.dp))

            Image(
                modifier = Modifier
                    .size(64.dp),
                painter = painterResource(id = R.drawable.library),
                contentDescription = "Library"
            )

            Spacer(modifier = Modifier.width(40.dp))


            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Add Borrower",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                modifier = Modifier
                    .size(36.dp),
                painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_48),
                contentDescription = "arrow"
            )

        }

        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = "Manage Library",
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .border(
                        width = 2.dp,
                        color = colorResource(id = R.color.p2),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    modifier = Modifier
                        .size(56.dp),
                    painter = painterResource(id = R.drawable.library),
                    contentDescription = "Library"
                )

                Text(
                    modifier = Modifier,
                    text = "Manage\nBooks",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )

            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .border(
                        width = 2.dp,
                        color = colorResource(id = R.color.p2),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    modifier = Modifier
                        .size(56.dp),
                    painter = painterResource(id = R.drawable.library),
                    contentDescription = "Library"
                )

                Text(
                    modifier = Modifier,
                    text = "Borrowers\nSummary",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )

            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .border(
                        width = 2.dp,
                        color = colorResource(id = R.color.p2),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    modifier = Modifier
                        .size(56.dp),
                    painter = painterResource(id = R.drawable.library),
                    contentDescription = "Library"
                )

                Text(
                    modifier = Modifier,
                    text = "My\nProfile",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )

            }
        }
    }
}

