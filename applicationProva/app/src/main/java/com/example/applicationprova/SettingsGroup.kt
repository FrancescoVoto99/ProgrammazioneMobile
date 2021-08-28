package com.example.applicationprova

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.applicationprova.databinding.ActivitySettingsGroupBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList

class SettingsGroup : AppCompatActivity() {

    var database: FirebaseDatabase= FirebaseDatabase.getInstance("https://prova-14ff5-default-rtdb.europe-west1.firebasedatabase.app/")
    var searchUser: DatabaseReference = database.getReference("gruppi")
    var myRefutenti: DatabaseReference = database.getReference("utentiGruppi")
    val list = ArrayList<String>()
    val listkey = ArrayList<String>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val binding: ActivitySettingsGroupBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_settings_group
        )


        searchUser.child("-MhxU2gVL0ZZrvfrYOsN").child("nomeGruppo").get().addOnSuccessListener {
            binding.Nomegruppo.setText(it.value.toString())
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }

        searchUser.child("-MhxU2gVL0ZZrvfrYOsN").child("gruppo").get().addOnSuccessListener {
            for (postSnapshot in it.children) {

                list.add(postSnapshot.getValue().toString())
                listkey.add(postSnapshot.key.toString())

            }


            binding.listaUtenti.isClickable = true
            binding.listaUtenti.adapter = SettingGroupAdapter(this, list)

            binding.listaUtenti.setOnItemClickListener { parent, view, position, id ->

                val email = list[position]


                val alertDialog = AlertDialog.Builder(this)
                alertDialog.setTitle("Eliminazione")
                alertDialog.setMessage("Sei sicuro di voler eliminare l'utente: "+ email)

                alertDialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->


                    searchUser.child("-MhxU2gVL0ZZrvfrYOsN").child("gruppo").child(listkey[position]).removeValue().addOnSuccessListener {
                        myRefutenti.child(email.replace(".","")).child("-MhxU2gVL0ZZrvfrYOsN").removeValue().addOnSuccessListener {
                            val intent = Intent(this, SettingsGroup::class.java)
                            startActivity(intent)
                        }


                    }

                })

                alertDialog.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

                alertDialog.show()
//
            }

            }

        .addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }







        val fab: View = binding.fabNewUser
        fab.setOnClickListener {
            fabOnClick("-MhxU2gVL0ZZrvfrYOsN",binding.Nomegruppo.text.toString())
        }
    }

    private fun fabOnClick(keygroup:String,namegroup:String) {




        val builder: AlertDialog.Builder =AlertDialog.Builder(this)
        builder.setTitle("Inserisci un Nuovo Componente")

        val input = EditText(this)

        input.setHint("Email")
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)


        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->

            var email = input.text.toString()

            searchUser.child("-MhxU2gVL0ZZrvfrYOsN").child("gruppo").push().setValue(email).addOnSuccessListener {
                myRefutenti.child(email.replace(".","")).child("-MhxU2gVL0ZZrvfrYOsN").setValue(namegroup).addOnSuccessListener {
                    val intent = Intent(this, SettingsGroup::class.java)
                    startActivity(intent)
                }


            }

        })

        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
//
    }



}