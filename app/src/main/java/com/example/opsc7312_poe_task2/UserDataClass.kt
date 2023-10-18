package com.example.opsc7312_poe_task2

import android.annotation.SuppressLint
import android.content.Context


class UserDataClass
    (
    var userID: Int = 0,
    var username: String = "",
    var password: String = ""
    )
{


    fun ValidateUser(userName: String, userPassword: String): Boolean
    {
        //val PasswordManager = PasswordClass(context)

        //loop through users
        for (i in GlobalsClass.userList.indices)
        {

            //if the entered username matches an existing username
            if (userName == GlobalsClass.userList[i].username)
            {
                //if user exists
                if (userPassword == GlobalsClass.userList[i].password)
                {
                    //if the user password is correct
                    userID = GlobalsClass.userList[i].userID
                    username = userName
                    password = GlobalsClass.userList[i].password
                    //Send the data to globals
                    GlobalsClass.user.userID = userID
                    GlobalsClass.user.username = username
                    GlobalsClass.user.password = password
                    //exit loop
                    break
                }
            }
        }
        return userID != 0
    }

    @SuppressLint("SuspiciousIndentation")
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

    data class PasswordResources(
        val passwordShort: String,
        val passwordNeedsNumber: String,
        val passwordNeedsLowerCase: String,
        val passwordNeedsUpperCase: String,
        val passwordNeedsSpecialCharacter: String,
        val passwordSpecialCharacters: String
    )


    fun RegisterUser(userUsername : String, userPassword: String)
    {
        var userExists = false


        //loop through users
        for(i in GlobalsClass.userList.indices)
        {
            //check to see if there is already a user with the given information
            if (userUsername == GlobalsClass.userList[i].username)
            {
                //If the user already exists set the user exists variable to true
                userExists = true
                //exit loop
                break
            }
        }

        //check if the user matching the given data exists or conflicts
        if (!userExists) {
            //if the user doesn't conflict with existing data then register the user


            var userIDs = ArrayList<Int>()
            for (j in GlobalsClass.userList.indices)
            {
                userIDs.add(GlobalsClass.userList[j].userID)
            }

            var lastID = userIDs.max()
            //val currentLastUserIDIndex = GlobalClass.allUsers.last().userID//GlobalClass.listUserUserID.last()

            var newUserUserIDIndex = lastID + 1


            var newUser = UserDataClass(newUserUserIDIndex, userUsername, userPassword)

            GlobalsClass.userList.add(newUser)
        }


    }
}