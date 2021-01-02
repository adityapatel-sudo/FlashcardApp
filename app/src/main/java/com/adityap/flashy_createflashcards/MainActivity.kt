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
import com.adityap.flashy_createflashcards.models.LOGGING_TAG
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val homeFragment: Fragment = HomeFragment()
    private val fm: FragmentManager = supportFragmentManager
    private var active: Fragment = homeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.main_toolbar) as Toolbar
        setSupportActionBar(toolbar)
    }

    override fun onResume() {
        super.onResume()
        Log.d(LOGGING_TAG, "onResume");
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            Log.d(LOGGING_TAG, "starting LoginActivity");
            startActivity(Intent(this, LoginActivity::class.java))
        }

        fm.beginTransaction().replace(R.id.fragment_container, active).commit()
        bottom_navigation.setOnNavigationItemSelectedListener { switchMenu(it) }
    }

    private fun switchMenu(item: MenuItem): Boolean {
        Log.d(LOGGING_TAG, "bottom_navigation nav item clicked")
        return when (item.itemId) {
            R.id.home -> {
                active = homeFragment
                fm.beginTransaction().replace(R.id.fragment_container, active).commit()
                true
            }
            else -> {
                true
            }
        }
    }
}


