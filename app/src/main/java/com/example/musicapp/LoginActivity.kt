package com.example.musicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.musicapp.databinding.ActivityLoginBinding
import com.example.musicapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            binding.btnLogin.setOnClickListener {
                val email = binding.emailAddress.text.toString()
                val password = binding.password.text.toString()

                if (!Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(), email)) {
                    binding.emailAddress.setError("Invalid Email")
                    return@setOnClickListener
                }

                if (password.length < 6) {
                    binding.password.error = "Length should be 6 error"
                    return@setOnClickListener
                }

                loginWithFireBase(email, password)
            }

            binding.goToSignUp.setOnClickListener{
                startActivity(Intent(this, SignUpActivity::class.java))
            }

        }
    }

    fun setInProgress(inProgress: Boolean) {
        if (inProgress){
            binding.btnLogin.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.btnLogin.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE

        }
    }

    override fun onResume() {
        super.onResume()
        FirebaseAuth.getInstance().currentUser?.apply {
            startActivity(Intent(this@LoginActivity,MainActivity::class.java))
            finish()
        }
    }

     fun loginWithFireBase(email: String, password: String) {
        setInProgress(true)
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                setInProgress(false)
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
            .addOnFailureListener{
                setInProgress(false)
                Toast.makeText(applicationContext, "login Account Failed", Toast.LENGTH_SHORT).show()
            }

    }


}