package com.example.opsc7312_poe_task2

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


class FragmentHandler
{
    //This class handles fragments
    public fun replaceFragment(fragment : Fragment, fragmentContainerID : Int, fragmentManager : FragmentManager) {
        //begin transition to desired fragment
        val fragmentTransaction = fragmentManager.beginTransaction()

        //set the desired fragment to be swapped
        fragmentTransaction.replace(fragmentContainerID, fragment)

        //commit fragment change
        fragmentTransaction.commit()
    }
}