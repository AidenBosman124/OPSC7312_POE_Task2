/*
package com.example.opsc7312_poe_task2

import android.content.Context
import java.security.SecureRandom

class PasswordClass (val upperContext: Context)
{
    fun generateRandomSalt(): String
    {
        val random = SecureRandom()
        val salt = ByteArray(16)
        random.nextBytes(salt)
        return salt.toHexString()
    }

    fun ByteArray.toHexString() : String
    {
        return this.joinToString("")
        {
            java.lang.String.format(upperContext.getString(R.string.byteArrayFormatter), it)
        }
    }
}
*/
