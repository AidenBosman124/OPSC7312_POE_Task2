package com.example.opsc7312_poe_task2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

// Activity class for the start page of the application
class StartPage : AppCompatActivity() {

    // Function called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_page)

        // Find the "LogIn" and "Register" buttons in the layout
        val LogInbtn = findViewById<Button>(R.id.LogInbtn)
        val Registerbtn = findViewById<Button>(R.id.Registerbtn)

        // Set click listeners for the "LogIn" and "Register" buttons
        LogInbtn.setOnClickListener {
            // Start the LoginActivity when the "LogIn" button is clicked
            startActivity(Intent(this@StartPage, Login::class.java))
        }
        Registerbtn.setOnClickListener {
            // Start the RegisterActivity when the "Register" button is clicked
            startActivity(Intent(this@StartPage, Register::class.java))
        }
    }
}
