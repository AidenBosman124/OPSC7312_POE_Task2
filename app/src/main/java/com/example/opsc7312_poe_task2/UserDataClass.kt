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


    fun ValidateUser(userName: String, userPassword: String, context: Context): Boolean
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
        return if (userID == 0)
        {
            //user doesn't exist code goes here
            GlobalsClass.userAlert(context.getString(R.string.invalidSignInTitle), context.getString(R.string.invalidSignInMessage), context)

            //return the user exists boolean as false
            false
        } else{

            //return the user exists boolean as true
            true
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun ValidateUserPassword(attemptedPassword : String, context : Context): Pair<Boolean, String>
    {

        var validationErrors = ArrayList<String>()

        if (attemptedPassword.length < 8)
        {
            validationErrors.add(context.getString(R.string.passwordShort))
        }

        if (attemptedPassword.count(Char::isDigit) == 0)
        {
            validationErrors.add(context.getString(R.string.passwordNeedsNumber))
        }

        if (attemptedPassword.any(Char::isLowerCase))
        {

        }
        else
        {
            validationErrors.add(context.getString(R.string.passwordNeedsLowerCase))
        }

        if (attemptedPassword.any(Char::isUpperCase))
        {

        }
        else
        {
            validationErrors.add(context.getString(R.string.passwordNeedsUpperCase))
        }


        if (attemptedPassword.any { it in context.getString(R.string.passwordSpecialCharacters) })
        {

        }
        else
        {
            validationErrors.add(context.getString(R.string.passwordNeedsSpecialCharacter))
        }


        if (validationErrors.isEmpty())
        {
            return Pair(true, "")//true
        }
        else
        {
            var passwordErrors = ""
            for (i in validationErrors) {
                passwordErrors+= "$i\n"
            }

            //GlobalClass.InformUser("Invalid Password", passwordErrors, context)
            return Pair(false, passwordErrors)//false
        }
    }

    fun RegisterUser(userUsername : String, userPassword: String, context: Context)
    {
        val PasswordManager = (context)
        var userExists = false


        //loop through users
        for(i in GlobalsClass.userList.indices)
        {
            //check to see if there is already a user with the given information
            if (userUsername == GlobalsClass.userList[i].username)
            {
                //If the user already exists set the user exists variable to true
                userExists = true

                //Inform user that the entered information is already registered to another user
                GlobalsClass.userAlert(context.getString(R.string.invalidSignUpTitle), context.getString(R.string.invalidSignUpMessage), context)

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

            //inform the user that their account was successfully created
            GlobalsClass.userAlert(context.getString(R.string.addedUserTitle), context.getString(R.string.addedUserMessage), context)

        }


    }
}