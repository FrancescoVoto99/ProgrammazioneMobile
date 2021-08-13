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
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class RegistrerActivity : AppCompatActivity() {

    lateinit var Email: TextInputEditText
    lateinit var NomeUtente: TextInputEditText
    lateinit var Password: TextInputEditText
    lateinit var goLogin: TextView
    lateinit var btnRegister: Button

    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrer)

        btnRegister=findViewById<Button>(R.id.register_button)
        NomeUtente=findViewById<TextInputEditText>(R.id.register_nomeutente)
        Email=findViewById<TextInputEditText>(R.id.register_email)
        Password=findViewById<TextInputEditText>(R.id.register_password)
        goLogin=findViewById<TextView>(R.id.navigate_to_login)

        // Initialize Firebase Auth
        auth = Firebase.auth

        btnRegister.setOnClickListener{
            creteUser()
        }

        goLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


    }
    private fun creteUser(){
        if(TextUtils.isEmpty(Email.text.toString())){
            Email.setError("email non puo essere vuoto")
            Email.requestFocus()
        }
        else if(TextUtils.isEmpty(Password.text.toString())){
            Password.setError("Password non puo essere vuoto")
            Password.requestFocus()
        }
        else{
            auth.createUserWithEmailAndPassword(Email.text.toString(), Password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("prova", "createUserWithEmail:success")
                        val user = auth.currentUser

                        val profileUpdates = userProfileChangeRequest {
                            displayName = NomeUtente.text.toString()
                        }

                        user!!.updateProfile(profileUpdates)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d("set name", "User profile updated.")
                                }
                            }

                        val intent = Intent(this, InserisciProdotto::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("prova", "createUserWithEmail:failure", task.exception)

                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                    }
                }
        }


    }
}