package com.adityap.flashy_createflashcards

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_login)
        email.addTextChangedListener {
            loginButton.isEnabled = isInputLoginReady()
        }

        password.addTextChangedListener {
            loginButton.isEnabled = isInputLoginReady()
        }

        loginButton.setOnClickListener {
            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this@LoginActivity,
                                    MainActivity::class.java))
                        } else {
                            Toast.makeText(this, "Login Failed, Please try again "
                                    + task.exception.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
        }
    }

    private fun isInputLoginReady(): Boolean {
        val emailText = email.text.toString()
        val passwordText = password.text.toString()
        return (!emailText.isNullOrEmpty() && !passwordText.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailText).matches())
    }
}
