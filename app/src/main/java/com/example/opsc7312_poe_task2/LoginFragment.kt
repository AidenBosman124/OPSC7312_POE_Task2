package com.example.opsc7312_poe_task2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.opsc7312_poe_task2.databinding.FragmentLoginBinding

class LoginFragment : Fragment()
{
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnLogin.setOnClickListener {

            if (binding.etUsername.text.isNotEmpty() &&  binding.etPassword.text.isNotEmpty())
            {
                val trySignIn  =  UserDataClass()
                val trySubmitSignIn = trySignIn.ValidateUser(binding.etPassword.text.toString(),binding.etPassword.text.toString(), requireContext())

                if (trySubmitSignIn)
                {
                    GlobalsClass.userAlert(GlobalsClass.user.userID.toString(), "", requireContext())
                    //Sends the user to the home page on successful login
                    /*
                    var intent = Intent(activity, Home_Activity::class.java)
                    startActivity(intent)
                     */
                }
            }
            else
            {
                GlobalsClass.userAlert(getString(R.string.inputErrorTitle),getString(R.string.incompleteFields), requireContext())
            }

        }

        return view
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}