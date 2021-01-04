package com.adityap.flashy_createflashcards

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.adityap.flashy_createflashcards.models.EXTRA_GUEST
import com.adityap.flashy_createflashcards.models.LOGGING_TAG
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val homeFragment: Fragment = HomeFragment()
    private val profileFragment: Fragment = ProfileFragment()
    private val fm: FragmentManager = supportFragmentManager
    private var active: Fragment = homeFragment
    private var isGuestLogin = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isGuestLogin = intent.extras?.getBoolean(EXTRA_GUEST, false) ?: false
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        Log.d(LOGGING_TAG, "onResume");
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null && !isGuestLogin) {
            Log.d(LOGGING_TAG, "starting LoginActivity");
            var loginIntent = Intent(this, LoginActivity::class.java)
            loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(loginIntent)
        }

        fm.beginTransaction().replace(R.id.fragment_container, active).commit()
        bottom_navigation.setOnNavigationItemSelectedListener { switchMenu(it) }
    }

    private fun switchMenu(item: MenuItem): Boolean {
        Log.d(LOGGING_TAG, "bottom_navigation nav item clicked")
        return when (item.itemId) {
            R.id.home -> {
                addFragment(homeFragment)
                true
            }
            R.id.profile -> {
                addFragment(profileFragment)
                true
            }
            else -> {
                true
            }
        }
    }

    private fun addFragment(fragment: Fragment) {
        active = homeFragment
        fm.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }
}


