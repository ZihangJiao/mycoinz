package com.example.jiaozihang.coinz

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.buff.*
import kotlinx.android.synthetic.main.success_robbery.*
import java.util.*

class robbery_buff: AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()
    val user = mAuth.currentUser
    val firebaseData = FirebaseDatabase.getInstance().reference
    var num_of_user = 0
    val r = Random()
    var probability = 0
    var lucky_dog = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buff)
        val handler = Handler()
        probability = r.nextInt(10000000 )
        compute_lucky_dog()

        handler.postDelayed({ showToast() }, 1000)
    }



    fun showToast() {
        guide.text = lucky_dog + ",god said so"
    }


    fun compute_lucky_dog()
    {
        val coin_today_Listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
               num_of_user =  dataSnapshot.childrenCount.toInt()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }


        val email_choosing_Listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var num = probability.rem(num_of_user)
                for (i in dataSnapshot.children) {
                    if (num == 0){
                        lucky_dog = i.child("email").getValue(String::class.java)!!
                        break
                    }
                    num -= 1
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }


        firebaseData.child("users").addListenerForSingleValueEvent(coin_today_Listener)
        firebaseData.child("users").addListenerForSingleValueEvent(email_choosing_Listener)

    }

    }
