package com.example.musicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.musicapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnCreateAccount.setOnClickListener {
           val email =  binding.emailAddress.text.toString()
            val password = binding.password.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()

            if(!Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(), email)){
                binding.emailAddress.setError("Invalid Email")
                return@setOnClickListener
            }

            if (password.length <6) {
                binding.password.error = "Length should be 6 error"
                return@setOnClickListener
            }

            if (!password.equals(confirmPassword)) {
                binding.confirmPassword.error = "Password not matched"
            }

            createAccountWithFireBase(email, password)

        }

        binding.goToLogin.setOnClickListener {
            finish()
            startActivity(Intent(this, LoginActivity::class.java ))
        }

    }

    private fun createAccountWithFireBase(email: String, password: String) {
        setInProgress(true)
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                setInProgress(false)
                Toast.makeText(applicationContext, "User Created Successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener{
                setInProgress(false)
              Toast.makeText(applicationContext, "Create Account Failed", Toast.LENGTH_SHORT).show()
            }

    }

    fun setInProgress(inProgress: Boolean) {
        if (inProgress){
            binding.btnCreateAccount.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.btnCreateAccount.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE

        }
    }
}