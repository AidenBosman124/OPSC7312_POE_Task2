package com.example.opsc7312_poe_task2

import android.annotation.SuppressLint
import android.content.Context

// Class representing user data and related operations
class UserDataClass(
    var userID: Int = 0,
    var username: String = "",
    var password: String = ""
) {

    // Validate user credentials (username and password)
    fun ValidateUser(userName: String, userPassword: String): Boolean {
        for (user in GlobalsClass.userList) {
            if (userName == user.username && userPassword == user.password) {
                userID = user.userID
                username = userName
                password = user.password
                GlobalsClass.user.userID = userID
                GlobalsClass.user.username = username
                GlobalsClass.user.password = password
                return true
            }
        }
        return false
    }

    @SuppressLint("SuspiciousIndentation")
    // Validate user-provided password against specified criteria
    fun ValidateUserPassword(attemptedPassword: String, passwordResources: PasswordResources): Pair<Boolean, String> {
        val validationErrors = ArrayList<String>()

        if (attemptedPassword.length < 8) {
            validationErrors.add(passwordResources.passwordShort)
        }

        if (attemptedPassword.count(Char::isDigit) == 0) {
            validationErrors.add(passwordResources.passwordNeedsNumber)
        }

        if (!attemptedPassword.any(Char::isLowerCase)) {
            validationErrors.add(passwordResources.passwordNeedsLowerCase)
        }

        if (!attemptedPassword.any(Char::isUpperCase)) {
            validationErrors.add(passwordResources.passwordNeedsUpperCase)
        }

        if (!attemptedPassword.any { it in passwordResources.passwordSpecialCharacters }) {
            validationErrors.add(passwordResources.passwordNeedsSpecialCharacter)
        }

        if (validationErrors.isEmpty()) {
            return Pair(true, "")
        } else {
            val passwordErrors = validationErrors.joinToString("\n")
            return Pair(false, passwordErrors)
        }
    }

    // Data class to hold resources for password validation messages
    data class PasswordResources(
        val passwordShort: String,
        val passwordNeedsNumber: String,
        val passwordNeedsLowerCase: String,
        val passwordNeedsUpperCase: String,
        val passwordNeedsSpecialCharacter: String,
        val passwordSpecialCharacters: String
    )

    // Register a new user with the provided username and password
    fun RegisterUser(userUsername: String, userPassword: String) {
        if (GlobalsClass.userList.any { it.username == userUsername }) {
            // User already exists
            // Handle this case as needed (show an error message or take appropriate action)
        } else {
            val lastID = GlobalsClass.userList.maxByOrNull { it.userID }?.userID ?: 0
            val newUserUserIDIndex = lastID + 1
            val newUser = UserDataClass(newUserUserIDIndex, userUsername, userPassword)
            GlobalsClass.userList.add(newUser)
        }
    }
}
