package com.example.jiaozihang.coinz

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class trade : AppCompatActivity() {


    private val mAuth = FirebaseAuth.getInstance()!!
    val user = mAuth.currentUser
    private val firebaseData = FirebaseDatabase.getInstance().reference
    var found = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.the_trade)


        val comfirm_trading = findViewById<View>(R.id.trading) as Button


        comfirm_trading.setOnClickListener() {
            val handler = Handler()
            compute_email()
            handler.postDelayed({ showToast() }, 1000)
            /** give the firebase time to find the email */


        }
    }

    fun showToast() {
        if (found == false) {
            Toast.makeText(this, "Can't find that email", Toast.LENGTH_LONG).show()
        } else if (found == true) {
            Toast.makeText(this, "Gift sent successfully", Toast.LENGTH_LONG).show()
            val returnIntent = Intent()
            setResult(RESULT_CANCELED, returnIntent)
            finish()
        }
    }

    fun compute_email() {

        val coin_today_Listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val email_txt = findViewById<View>(R.id.email_Txt) as TextView
                for (i in dataSnapshot.children) {
                    val email_temp = i.child("email").getValue(String::class.java)
                    if (email_txt.text.toString() == email_temp) {
                        val target_user = i.key
                        CoinsObject.give_coins(target_user.toString())
                        found = true
                        break
                        /** try to find the user that match the email given */
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