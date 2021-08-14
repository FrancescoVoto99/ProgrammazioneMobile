package com.example.applicationprova

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ListOfGroups : AppCompatActivity() {

    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_groups)

        var auth = Firebase.auth
        val currentUser = auth.currentUser


        database = FirebaseDatabase.getInstance("https://prova-14ff5-default-rtdb.europe-west1.firebasedatabase.app/")
        myRef = database.getReference("utentiGruppi")


        val rv: RecyclerView = findViewById(R.id.listaGruppi)
        rv.layoutManager = LinearLayoutManager(this)

        val child=currentUser?.email.toString().replace(".","")
        myRef.child(child).get().addOnSuccessListener {

                Log.i("firebase", "Got value ${it.value}")
                 
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }

        rv.adapter = ListofGroupsAdapter(IntRange(0, 100).toList())
    }
}