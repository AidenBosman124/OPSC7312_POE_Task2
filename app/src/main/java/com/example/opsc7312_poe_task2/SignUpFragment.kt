package com.example.opsc_poe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.opsc7312_poe_task2.GlobalsClass
import com.example.opsc7312_poe_task2.R
import com.example.opsc7312_poe_task2.UserDataClass
import com.example.opsc7312_poe_task2.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment(R.layout.fragment_sign_up){


    private var _binding: FragmentSignUpBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root

        //-------------------------------------------------
        //code here

        //sign in button
        binding.btnSignup.setOnClickListener {
            try
            {
                if (binding.etNewUsername.text.isNotEmpty() && binding.etNewPassword.text.isNotEmpty())
                {

                    val trySignUp  =  UserDataClass()
                    var (validateUserPasswordBool, validateUserPasswordFeedback) = trySignUp.ValidateUserPassword(binding.etNewPassword.text.toString(), requireContext())

                        if (validateUserPasswordBool)
                        {
                            trySignUp.RegisterUser(
                                binding.etNewUsername.text.toString(),
                                binding.etNewPassword.text.toString(),
                                requireContext()
                            )
                        }
                        else
                        {
                            GlobalsClass.userAlert(getString(R.string.invalidPassword), validateUserPasswordFeedback, requireContext())
                        }
                }
                else
                {
                    GlobalsClass.userAlert(getString(R.string.inputErrorTitle),getString(R.string.incompleteFields), requireContext())
                }
            }
            catch (e: Error)
            {
                GlobalsClass.userAlert(getString(R.string.Error), "${e.toString()}", requireContext())
            }
            //-------------------------------------------------
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}