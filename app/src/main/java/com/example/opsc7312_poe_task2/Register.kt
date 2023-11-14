package com.example.opsc7312_poe_task2

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class Register : AppCompatActivity() {

    private var mFullName: EditText? = null
    private var mEmail: EditText? = null
    private var mPassword: EditText? = null
    private var mPhone: EditText? = null
    private var mRegisterBtn: Button? = null
    private var mLoginBtn: TextView? = null
    private var progressBar: ProgressBar? = null
    private var fAuth: FirebaseAuth? = null
    private var fStore: FirebaseFirestore? = null
    private var userID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase
        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        // Check if the user is already logged in
        if (fAuth!!.currentUser != null) {
            startActivity(Intent(applicationContext, MainPage::class.java))
            finish()
        }

        // Initialize views
        mFullName = findViewById(R.id.fullName)
        mEmail = findViewById(R.id.Email)
        mPassword = findViewById(R.id.password)
        mPhone = findViewById(R.id.phone)
        mRegisterBtn = findViewById(R.id.registerBtn)
        mLoginBtn = findViewById(R.id.createText)
        progressBar = findViewById(R.id.progressBar)

        // Register button click listener
        mRegisterBtn?.setOnClickListener {
            val email = mEmail?.text.toString().trim()
            val password = mPassword?.text.toString().trim()
            val fullName = mFullName?.text.toString()
            val phone = mPhone?.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Email and Password are required.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressBar?.visibility = View.VISIBLE

            // Register the user in Firebase
            fAuth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Send verification link
                    val fuser: FirebaseUser? = fAuth?.currentUser
                    fuser?.sendEmailVerification()?.addOnSuccessListener {
                        Toast.makeText(
                            this@Register,
                            "Verification Email Has been Sent.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }?.addOnFailureListener { e ->
                        Log.d(
                            TAG,
                            "onFailure: Email not sent " + e.message
                        )
                    }

                    Toast.makeText(this@Register, "User Created.", Toast.LENGTH_SHORT).show()

                    userID = fAuth?.currentUser?.uid
                    val documentReference: DocumentReference? =
                        fStore!!.collection("users")?.document(userID!!)
                    val user: MutableMap<String, Any> = HashMap()
                    user["fName"] = fullName
                    user["email"] = email
                    user["phone"] = phone

                    documentReference?.set(user)?.addOnSuccessListener {
                        Log.d(
                            TAG,
                            "onSuccess: user Profile is created for $userID"
                        )
                    }?.addOnFailureListener { e ->
                        Log.d(TAG, "onFailure: $e")
                    }

                    startActivity(Intent(applicationContext, MainPage::class.java))
                } else {
                    Toast.makeText(
                        this@Register,
                        "Error ! ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    progressBar?.visibility = View.GONE
                }
            }
        }

        // Login button click listener
        mLoginBtn?.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    Login::class.java
                )
            )
        }
    }

    companion object {
        const val TAG = "TAG"
    }
}
