package com.example.opsc7312_poe_task2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.opsc7312_poe_task2.databinding.ActivityMainBinding

class Login : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    val LogInbtn = findViewById<Button>(R.id.btn_login)
    val Backbtn = findViewById<Button>(R.id.btn_back)

    LogInbtn.setOnClickListener()
    {
        //logIn()
        startActivity(Intent(this@Login, MainPage::class.java))
    }
    Backbtn.setOnClickListener()
    {
        startActivity(Intent(this@Login, StartPage::class.java))
    }

    val showHideBtn= findViewById<Button>(R.id.showHideBtn)
    val password = findViewById<EditText>(R.id.etPassword)

    showHideBtn.setOnClickListener {
        if (showHideBtn.text.toString().equals("Show")) {
            password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            showHideBtn.text = "Hide"
        } else {
            password.transformationMethod = PasswordTransformationMethod.getInstance()
            showHideBtn.text = "Show"
        }
    }
}

    private fun logIn()
    {
        val password = findViewById<EditText>(R.id.etPassword)
        val username = findViewById<EditText>(R.id.etUsername)
        if (username.text.isNotEmpty() &&  password.text.isNotEmpty())
        {
            val trySignIn  =  UserDataClass()
            val trySubmitSignIn = trySignIn.ValidateUser(password.text.toString(),password.text.toString())

            if (trySubmitSignIn)
            {
                startActivity(Intent(this@Login, MainPage::class.java))
            }
        }
        else
        {

        }
    }
}