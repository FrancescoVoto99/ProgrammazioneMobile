package com.example.applicationprova

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.applicationprova.databinding.ActivityListOfGroupsBinding
import com.example.applicationprova.databinding.ActivityNewgroupBinding
import com.example.progetto.Prodotto
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase


class Newgroup : AppCompatActivity() {

    lateinit var nome : EditText
    lateinit var nuovoComponente: EditText
    lateinit var lista: TextView
    lateinit var agg: Button
    lateinit var crea: Button

    var groupid: Long=0

    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityNewgroupBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_newgroup)

        val list = mutableListOf<String>()


        database = FirebaseDatabase.getInstance("https://prova-14ff5-default-rtdb.europe-west1.firebasedatabase.app/")
        myRef = database.getReference("gruppi")
        var myRefutenti = database.getReference("utentiGruppi")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                if (dataSnapshot.exists())
                    groupid= dataSnapshot.childrenCount.toLong()
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("prova", "Failed to read value.", error.toException())
            }
        })

        binding.agg.setOnClickListener{
            list.add(binding.aggiungimembro.text.toString())
            binding.componenti.append(binding.aggiungimembro.text.toString()+ System.getProperty ("line.separator"))
            binding.aggiungimembro.setText("")
        }

        binding.creaGruppo.setOnClickListener{

            val auth = Firebase.auth
            val currentUser = auth.currentUser

            val group = Gruppo(binding.Nomegruppo.text.toString(),list)




            myRef.child((groupid+1).toString()).setValue(group).addOnSuccessListener {

                myRefutenti.child(currentUser?.email.toString().replace(".","")).child("idgruppo" +(groupid).toString()).setValue((groupid).toString())
                list.forEach{
                    myRefutenti.child(it.replace(".","")).child("idgruppo" +(groupid).toString()).setValue((groupid).toString())
                }
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }

        }

    }



}