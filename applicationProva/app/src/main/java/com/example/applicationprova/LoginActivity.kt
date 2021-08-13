package com.example.applicationprova

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {


    lateinit var Email: TextInputEditText
    lateinit var Password: TextInputEditText
    lateinit var goRegister: TextView
    lateinit var btnLogin: Button

    lateinit var auth : FirebaseAuth




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        Email=findViewById<TextInputEditText>(R.id.login_email)
        Password=findViewById<TextInputEditText>(R.id.login_password)
        btnLogin=findViewById<Button>(R.id.login_button)
        goRegister=findViewById<TextView>(R.id.navigate_to_register)
        // Initialize Firebase Auth
        auth = Firebase.auth

        btnLogin.setOnClickListener{
            loginUser()
        }
        goRegister.setOnClickListener{
            val intent = Intent(this, RegistrerActivity::class.java)
            startActivity(intent)
        }


    }

    private fun loginUser(){

        if(TextUtils.isEmpty(Email.text.toString())){
            Email.setError("email non puo essere vuoto")
            Email.requestFocus()
        }
        else if(TextUtils.isEmpty(Password.text.toString())){
            Password.setError("Password non puo essere vuoto")
            Password.requestFocus()
        }
        else{
            auth.signInWithEmailAndPassword(Email.text.toString(), Password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("prova", "signInWithEmail:success")
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("prova", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                    }
                }



        }

    }


    }
