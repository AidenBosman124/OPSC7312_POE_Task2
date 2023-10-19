package com.example.opsc7312_poe_task2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val Registerbtn = findViewById<Button>(R.id.btn_signup)
        val Backbtn = findViewById<Button>(R.id.btnBack)
        val username = findViewById<EditText>(R.id.etNewUsername)
        val password = findViewById<EditText>(R.id.etNewPassword)

        Registerbtn.setOnClickListener() {
            if (username.text.isNotEmpty() && password.text.isNotEmpty()) {
                val trySignUp = UserDataClass()
                val result = trySignUp.ValidateUserPassword(password.text.toString(), UserDataClass.PasswordResources(
                    getString(R.string.passwordShort),
                    getString(R.string.passwordNeedsNumber),
                    getString(R.string.passwordNeedsLowerCase),
                    getString(R.string.passwordNeedsUpperCase),
                    getString(R.string.passwordNeedsSpecialCharacter),
                    getString(R.string.passwordSpecialCharacters)
                ))

                if (result.first) {
                    trySignUp.RegisterUser(username.text.toString(), password.text.toString())
                    startActivity(Intent(this@Register, Login::class.java))
                } else {
                    displayPasswordError(result.second)
                }
            } else {
                // Handle empty fields
            }
        }

        Backbtn.setOnClickListener() {
            startActivity(Intent(this@Register, Login::class.java))
        }
    }

    private fun displayPasswordError(errorMessage: String) {
        Toast.makeText(this, "Sign up failed. Password requirements:\n$errorMessage", Toast.LENGTH_LONG).show()
    }
}
