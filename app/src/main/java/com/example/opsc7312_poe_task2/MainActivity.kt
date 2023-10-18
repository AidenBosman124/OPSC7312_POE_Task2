package com.example.opsc7312_poe_task2
/*
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.appcompat.app.AppCompatActivity
import com.example.opsc7312_poe_task2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Hide the action bar
        supportActionBar?.hide()

        try
        {
            //Set view binding
            val binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            //create local fragment controller
            val fragmentControl = FragmentHandler()

            //set the sign in fragment to be the initial view
            fragmentControl.replaceFragment(SignUpFragment(), R.id.fcFragmentContainer, supportFragmentManager)

            //get the intent value if it exists
            var Login = intent.getBooleanExtra(getString(R.string.LoadSignUp), false)

            if (Login)
            {
                fragmentControl.replaceFragment(LoginFragment(), R.id.fcFragmentContainer, supportFragmentManager)
            }

            //Sign in view activation
            binding.tvSignIn.setOnClickListener{
                fragmentControl.replaceFragment(LoginFragment(),R.id.fcFragmentContainer, supportFragmentManager)
            }

            //Sign up view activation
            binding.tvSignUp.setOnClickListener{
                fragmentControl.replaceFragment(SignUpFragment(), R.id.fcFragmentContainer, supportFragmentManager)
            }
        }
        catch (e: Error)
        {
            GlobalsClass.userAlert(getString(R.string.Error), "${e.toString()}", this)
        }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
*/