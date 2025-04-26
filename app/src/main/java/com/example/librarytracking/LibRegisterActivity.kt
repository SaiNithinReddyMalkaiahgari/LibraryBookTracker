package com.example.librarytracking

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.firebase.database.FirebaseDatabase

class LibRegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegisterScreen()
        }
    }
}

@Composable
fun RegisterScreen() {
    var name by remember { mutableStateOf("") }
    var useremail by remember { mutableStateOf("") }
    var userpassword by remember { mutableStateOf("") }
    var confirmuserpassword by remember { mutableStateOf("") }

    val context = LocalContext.current as Activity
//    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.p1)),

        ) {

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Welcome to our App!",
                color = colorResource(id = R.color.p2),
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = "Please fill your details",
                color = colorResource(id = R.color.p2),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = useremail,
                onValueChange = { useremail = it },
                label = { Text("Enter Mail") },

                )

            Spacer(modifier = Modifier.height(6.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = { name = it },
                label = { Text("Enter Name") }
            )

            Spacer(modifier = Modifier.height(6.dp))


            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = userpassword,
                onValueChange = { userpassword = it },
                label = { Text("Enter Password") }
            )


            Spacer(modifier = Modifier.height(6.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = confirmuserpassword,
                onValueChange = { confirmuserpassword = it },
                label = { Text("Confirm Password") }
            )

            Spacer(modifier = Modifier.height(36.dp))

            Button(
                onClick = {

                    if (name.isEmpty()) {
                        Toast.makeText(context, "Enter name", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (useremail.isEmpty()) {
                        Toast.makeText(context, "Enter Mail", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (userpassword.isEmpty()) {
                        Toast.makeText(context, "Enter Password", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (userpassword != confirmuserpassword) {
                        Toast.makeText(context, "Passwords doesn't match", Toast.LENGTH_SHORT)
                            .show()
                        return@Button
                    }

                    val libReaderData = LibReader(
                        name,
                        useremail,
                        "",
                        userpassword
                    )
                    libReaderRegister(libReaderData, context)


                },
                modifier = Modifier
                    .padding(16.dp, 2.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.p2),
                    contentColor = colorResource(id = R.color.p1)
                )
            ) {
                Text("SignUp")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Spacer(modifier = Modifier.weight(1f))


            Row(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Already have an account? ",
                    style = MaterialTheme.typography.bodyLarge,
                    color = colorResource(id = R.color.p2),
                )

                Text(
                    text = "SignIn",
                    color = colorResource(id = R.color.p2),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Black),
                    modifier = Modifier.clickable {
                        context.startActivity(Intent(context, LibCheckInActivity::class.java))
                        context.finish()
                    }
                )
            }

        }

    }
}

fun libReaderRegister(userData: LibReader, context: Context) {

    val firebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference = firebaseDatabase.getReference("LibraryReader")

    databaseReference.child(userData.emailid.replace(".", ","))
        .setValue(userData)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "You Registered Successfully", Toast.LENGTH_SHORT)
                    .show()

                context.startActivity(Intent(context, LibCheckInActivity::class.java))
                (context as Activity).finish()

            } else {
                Toast.makeText(
                    context,
                    "Registration Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        .addOnFailureListener { _ ->
            Toast.makeText(
                context,
                "Something went wrong",
                Toast.LENGTH_SHORT
            ).show()
        }
}

data class LibReader(
    var name: String = "",
    var emailid: String = "",
    var area: String = "",
    var password: String = ""
)