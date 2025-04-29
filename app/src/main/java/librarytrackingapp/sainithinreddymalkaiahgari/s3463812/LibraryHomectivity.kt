package librarytrackingapp.sainithinreddymalkaiahgari.s3463812

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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



@Preview(showBackground = true)
@Composable
fun LibraryHomeScreenP() {
    LibraryHomeScreen()
}

@Composable
fun LibraryHomeScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(WindowInsets.systemBars.asPaddingValues())
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
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clickable {
                        context.startActivity(Intent(context, ManageBooksActivity::class.java))
                    },
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
                    .clickable {
                        context.startActivity(Intent(context, ManageBorrowersActivity::class.java))
                    }
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
                    .clickable {
                        context.startActivity(Intent(context, LibraryProfileActivity::class.java))
                    }
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




        Spacer(modifier = Modifier.height(12.dp))


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .clickable {
                    context.startActivity(Intent(context, LibraryProfileActivity::class.java))
                }
                .border(
                    width = 2.dp,
                    color = colorResource(id = R.color.p2),
                    shape = RoundedCornerShape(6.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Contact Us",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.p2)),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))


            Text(text = "Sai Nithin Reddy Malkaiahgari")
            Text(text = "Email: sainithinreddy2002@gmail.com")
            Text(text = "Student ID: S3463812")

            Spacer(modifier = Modifier.height(8.dp))

        }
        
        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .clickable {
                    context.startActivity(Intent(context, LibraryProfileActivity::class.java))
                }
                .border(
                    width = 2.dp,
                    color = colorResource(id = R.color.p2),
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "About Us",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.p2)),
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                text = "Welcome to LibTrack – your personal library book tracker, designed to keep your reading organized and your books within reach!\n" +
                        "Developed by Sai Nithin Reddy Malkaiahgari, LibTrack is the perfect app for book lovers, students, and anyone who wants to manage their library efficiently. Whether you’re tracking books you own, borrowing from friends or a library, or managing your reading list, LibTrack makes it easy to stay organized.\n" +
                        "Thank you for choosing LibTrack to keep your library in check!\n",
                textAlign = TextAlign.Justify
            )

        }
    }
}

