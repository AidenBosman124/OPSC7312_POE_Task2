package com.example.opsc7312_poe_task2

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class Login : AppCompatActivity()
{
    var mEmail: EditText? = null
    var mPassword: EditText? = null
    var mLoginBtn: Button? = null
    var mCreateBtn: TextView? = null
    var forgotTextLink: TextView? = null
    var progressBar: ProgressBar? = null
    var fAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val mEmail = findViewById<EditText>(R.id.Email)
        val mPassword = findViewById<EditText>(R.id.password)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val fAuth = FirebaseAuth.getInstance()
        val mLoginBtn = findViewById<Button>(R.id.loginBtn)
        val mCreateBtn = findViewById<TextView>(R.id.createText)
        val forgotTextLink = findViewById<TextView>(R.id.forgotPassword)

        mLoginBtn.setOnClickListener(View.OnClickListener {
            val email = mEmail.getText().toString().trim { it <= ' ' }
            val password = mPassword.getText().toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(email))
            {
                mEmail.setError("Email is Required.")
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password))
            {
                mPassword.setError("Password is Required.")
                return@OnClickListener
            }
            if (password.length < 6)
            {
                mPassword.setError("Password Must be >= 6 Characters")
                return@OnClickListener
            }
            progressBar.setVisibility(View.VISIBLE)

            // authenticate the user
            fAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful)
                {
                    Toast.makeText(this@Login, "Logged in Successfully", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(applicationContext, MainPage::class.java))
                } else
                {
                    Toast.makeText(
                        this@Login,
                        "Error ! " + task.exception!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    progressBar.setVisibility(View.GONE)
                }
            }
        })
        mCreateBtn.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    Register::class.java
                )
            )
        })
        forgotTextLink.setOnClickListener(View.OnClickListener { v ->
            val resetMail = EditText(v.context)
            val passwordResetDialog = AlertDialog.Builder(v.context)
            passwordResetDialog.setTitle("Reset Password ?")
            passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.")
            passwordResetDialog.setView(resetMail)
            passwordResetDialog.setPositiveButton(
                "Yes"
            ) { dialog, which -> // extract the email and send reset link
                val mail = resetMail.text.toString()
                fAuth!!.sendPasswordResetEmail(mail).addOnSuccessListener {
                    Toast.makeText(
                        this@Login,
                        "Reset Link Sent To Your Email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }.addOnFailureListener { e ->
                    Toast.makeText(
                        this@Login,
                        "Error ! Reset Link is Not Sent" + e.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            passwordResetDialog.setNegativeButton(
                "No"
            ) { dialog, which ->
                // close the dialog
            }
            passwordResetDialog.create().show()
        })
    }
}