package com.example.opsc7312_poe_task2

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        val listButton = view?.findViewById<Button>(R.id.btnList)
        val mapButton = view?.findViewById<Button>(R.id.btnMap)
        val achievementButton = view?.findViewById<Button>(R.id.btnAchievements)

        listButton?.setOnClickListener {
            btnListClick()
        }

        mapButton?.setOnClickListener {
            btnMapClick()
        }

        achievementButton?.setOnClickListener {
            btnAchievementClick()
        }

        return view
    }

    private fun btnListClick() {
        startActivity(Intent(requireContext(), Nearbyfrag::class.java))
    }

    private fun btnMapClick() {
        startActivity(Intent(requireContext(), MapsActivity::class.java))
    }

    private fun btnAchievementClick() {
        startActivity(Intent(requireContext(), activityAchievements::class.java))
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}