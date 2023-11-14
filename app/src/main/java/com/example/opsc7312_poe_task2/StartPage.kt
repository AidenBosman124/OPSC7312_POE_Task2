package com.example.opsc7312_poe_task2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.FirebaseApp;


class StartPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_page)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        val LogInbtn = findViewById<Button>(R.id.LogInbtn)
        val Registerbtn = findViewById<Button>(R.id.Registerbtn)

        LogInbtn.setOnClickListener {
            startActivity(Intent(this@StartPage, Login::class.java))
        }
        Registerbtn.setOnClickListener {
            startActivity(Intent(this@StartPage, Register::class.java))
        }
    }
}