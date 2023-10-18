package com.example.opsc7312_poe_task2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.opsc7312_poe_task2.databinding.ActivityMainBinding

class Register : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val Registerbtn = findViewById<Button>(R.id.btn_signup)
        val Backbtn = findViewById<Button>(R.id.btnBack)

        Registerbtn.setOnClickListener()
        {
            register()
        }
        Backbtn.setOnClickListener()
        {
            startActivity(Intent(this@Register, Login::class.java))
        }

    }
    private fun register() {
        val username = findViewById<EditText>(R.id.etNewUsername)
        val password = findViewById<EditText>(R.id.etNewPassword)

        try {
            if (username.text.isNotEmpty() && password.text.isNotEmpty()) {
                val trySignUp = UserDataClass()

                // Define your string resources here
                val passwordResources = UserDataClass.PasswordResources(
                    getString(R.string.passwordShort),
                    getString(R.string.passwordNeedsNumber),
                    getString(R.string.passwordNeedsLowerCase),
                    getString(R.string.passwordNeedsUpperCase),
                    getString(R.string.passwordNeedsSpecialCharacter),
                    getString(R.string.passwordSpecialCharacters)
                )

                val (validateUserPasswordBool, passwordErrors) = trySignUp.ValidateUserPassword(password.text.toString(), passwordResources)

                if (validateUserPasswordBool) {
                    trySignUp.RegisterUser(username.text.toString(), password.text.toString())
                    startActivity(Intent(this@Register, MainPage::class.java))
                } else {
                    // Handle password validation errors (passwordErrors)
                }
            } else {
                // Handle empty fields
            }
        } catch (e: Exception) {
            // Handle exceptions
        }
    }
}