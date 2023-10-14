package com.example.opsc7312_poe_task2

import android.app.AlertDialog
import android.content.Context

class GlobalsClass
{
    companion object
    {
        var userList = arrayListOf<UserDataClass>()
        var user = UserDataClass()

        fun userAlert(messageTitle: String, messageText: String, context: Context) {
            val alert = AlertDialog.Builder(context)
            alert.setTitle(messageTitle)
            alert.setMessage(messageText)
            alert.setPositiveButton("OK", null)

            alert.show()
        }
    }
}