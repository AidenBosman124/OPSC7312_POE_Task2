package com.example.opsc7312_poe_task2

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class Register : AppCompatActivity()
{
    var mFullName: EditText? = null
    var mEmail: EditText? = null
    var mPassword: EditText? = null
    var mPhone: EditText? = null
    var mRegisterBtn: Button? = null
    var mLoginBtn: TextView? = null
    var fAuth: FirebaseAuth? = null
    var progressBar: ProgressBar? = null
    var fStore: FirebaseFirestore? = null
    var userID: String? = null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val mFullName = findViewById<EditText>(R.id.fullName)
        val mEmail = findViewById<EditText>(R.id.Email)
        val mPassword = findViewById<EditText>(R.id.password)
        val mPhone = findViewById<EditText>(R.id.phone)
        val mRegisterBtn = findViewById<Button>(R.id.registerBtn)
        val mLoginBtn = findViewById<TextView>(R.id.createText)
        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        if (fAuth!!.currentUser != null)
        {
            startActivity(Intent(applicationContext, MainPage::class.java))
            finish()
        }
        mRegisterBtn!!.setOnClickListener(View.OnClickListener {
            val email = mEmail!!.text.toString().trim { it <= ' ' }
            val password = mPassword!!.text.toString().trim { it <= ' ' }
            val fullName = mFullName!!.text.toString()
            val phone = mPhone!!.text.toString()
            if (TextUtils.isEmpty(email))
            {
                mEmail.error = "Email is Required."
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password))
            {
                mPassword.error = "Password is Required."
                return@OnClickListener
            }
            if (password.length < 6)
            {
                mPassword.error = "Password Must be >= 6 Characters"
                return@OnClickListener
            }
            progressBar!!.visibility = View.VISIBLE

            // register the user in firebase
            fAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful)
                    {

                        // send verification link
                        val fuser: FirebaseUser? = fAuth!!.currentUser
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
                        Toast.makeText(this@Register, "User Created.", Toast.LENGTH_SHORT)
                            .show()
                        userID = fAuth!!.currentUser?.uid
                        val documentReference: DocumentReference =
                            fStore!!.collection("users").document(userID!!)
                        val user: MutableMap<String, Any> = HashMap()
                        user["fName"] = fullName
                        user["email"] = email
                        user["phone"] = phone
                        documentReference.set(user)
                            .addOnSuccessListener {
                                Log.d(
                                    TAG,
                                    "onSuccess: user Profile is created for $userID"
                                )
                            }.addOnFailureListener { e -> Log.d(TAG, "onFailure: $e") }
                        startActivity(Intent(applicationContext, MainPage::class.java))
                    } else
                    {
                        Toast.makeText(this@Register, "Error ! ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        progressBar.visibility = View.GONE
                    }
                }
        })
        mLoginBtn!!.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    Login::class.java
                )
            )
        }
    }

    companion object
    {
        const val TAG = "TAG"
    }
}
