package com.example.opsc7312_poe_task2

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Log.d("MyApp", "onCreate called")

        // Initialization or setup tasks go here
        FirebaseApp.initializeApp(this)
    }
}
