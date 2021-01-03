package com.adityap.flashy_createflashcards

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : Fragment() {
    private var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            LayoutInflater.from(context).inflate(R.layout.profile_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpView(user)
        login_btn.setOnClickListener {
            if (user != null) {
                FirebaseAuth.getInstance().signOut()
                user = FirebaseAuth.getInstance().currentUser
                setUpView(user)
            } else {
                startActivity(Intent(context, LoginActivity::class.java))
            }
        }
    }

    private fun setUpView(user: FirebaseUser?) {
        if (user != null) {
            name.text = user.displayName
            email.text = user.email
            login_btn.text = resources.getText(R.string.log_out)
        } else {
            name.text = resources.getText(R.string.guest_message)
            email.text = ""
            login_btn.text = resources.getText(R.string.log_in)
        }
    }
}
