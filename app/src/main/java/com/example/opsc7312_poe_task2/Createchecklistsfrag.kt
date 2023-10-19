package com.example.opsc7312_poe_task2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.opsc7312_poe_task2.databinding.FragmentCreatechecklistsfragBinding

class Createchecklistsfrag : Fragment() {
    private var binding: FragmentCreatechecklistsfragBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatechecklistsfragBinding.inflate(inflater, container, false)
        val createchecklistsfrag = binding?.root

        // Set a click listener for your button
        binding?.button4?.setOnClickListener {
            val name = binding?.editTextText2?.text.toString()
            val date = binding?.editTextText4?.text.toString()
            val location = binding?.editTextText3?.text.toString()

            // Handle your button click here, you can use the name, date, and location as needed.
        }

        return createchecklistsfrag
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
