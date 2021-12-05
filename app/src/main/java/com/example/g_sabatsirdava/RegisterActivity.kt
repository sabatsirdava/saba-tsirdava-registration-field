package com.example.g_sabatsirdava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextRepeatPassword: EditText
    private lateinit var buttonRegister: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        init()
        RegisterListeners()
    }

    private fun init() {
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextRepeatPassword = findViewById(R.id.editTextRepeatPassword)
        buttonRegister = findViewById(R.id.buttonRegister)

    }

    private fun RegisterListeners() {
        buttonRegister.setOnClickListener {

            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val repeatpassword = editTextRepeatPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill the Bar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (!email.contains(Regex("^((?=.[0-9])(?=.*[a-z])(?=.*[A-Z])(.*[@]{1}))"))) {
                Toast.makeText(this, "Please insert valid E-mail", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
                }
            else if (password!=repeatpassword) {
                Toast.makeText(this, "Passwords does not match!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if (repeatpassword.length<9 || password.length<9) {
                Toast.makeText(this, "Password is too short", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if (!password.contains(Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!&])(?=\\S+$).{9,}"))) {
                Toast.makeText(this, "Password must contain numbers and symbols too", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Registration Ended Succesfully", Toast.LENGTH_SHORT).show()
                } else{
                    Toast.makeText(this, "Something failed, try again!", Toast.LENGTH_SHORT).show()
                }
                }

        }
    }
}