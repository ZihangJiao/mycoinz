package com.example.jiaozihang.coinz

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.success_robbery.*

class robbery_success  : AppCompatActivity() {
    val mAuth = FirebaseAuth.getInstance()
    val user = mAuth.currentUser
    val firebaseData = FirebaseDatabase.getInstance().reference
    var victim_bank = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.success_robbery)
        val handler = Handler()
        compute_email()
        handler.postDelayed({ showToast() }, 1000)
    }

    fun showToast() {
        success_rob.text = "you have robbed "+(0.2 * victim_bank).toString()+" gold"
        }



    fun compute_email()
    {
        Log.d("Check2", coins.victim_email)
        val coin_today_Listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (i in dataSnapshot.children) {
                    val email_temp = i.child("email").getValue(String::class.java)
                    if(coins.victim_email == email_temp){
                        Log.d("Check2","here")
                        val target_user = i.key
                           victim_bank = i.child("bank").getValue(Double::class.java)!!

                        coins.Bank = coins.Bank + 0.2 * victim_bank!!

                        FirebaseDatabase.getInstance().getReference("users")
                                .child(target_user!!)
                                .child("bank").removeValue()

                        FirebaseDatabase.getInstance().getReference("users")
                                .child(target_user!!)
                                .child("bank").setValue(0.8 * victim_bank)

                        FirebaseDatabase.getInstance().getReference("users")
                                .child(user!!.uid)
                                .child("bank").removeValue()

                        FirebaseDatabase.getInstance().getReference("users")
                                .child(user!!.uid)
                                .child("bank").setValue(coins.Bank)


                        break
                    }
                }



            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }

        firebaseData.child("users").addListenerForSingleValueEvent(coin_today_Listener)

    }
    }