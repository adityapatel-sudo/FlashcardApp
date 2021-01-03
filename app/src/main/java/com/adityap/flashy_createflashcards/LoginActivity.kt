package com.adityap.flashy_createflashcards

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.adityap.flashy_createflashcards.models.EXTRA_GUEST
import com.adityap.flashy_createflashcards.models.LOGGING_TAG
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

        guest.setOnClickListener {
            val appIntent = Intent(this@LoginActivity,
                    MainActivity::class.java)
            appIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            appIntent.putExtra(EXTRA_GUEST, true)
            startActivity(appIntent)
        }
    }

    private fun isInputLoginReady(): Boolean {
        val emailText = email.text.toString()
        val passwordText = password.text.toString()
        return (!emailText.isNullOrEmpty() && !passwordText.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailText).matches())
    }
}
