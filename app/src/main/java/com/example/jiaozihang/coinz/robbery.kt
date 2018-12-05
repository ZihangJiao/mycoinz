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
import kotlinx.android.synthetic.main.the_robbery.*
import java.util.*

class robbery : AppCompatActivity() {
    val mAuth = FirebaseAuth.getInstance()
    val user = mAuth.currentUser
    val firebaseData = FirebaseDatabase.getInstance().reference
    var found = false
    val  handler = Handler()
    val r = Random()
    var probability = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.the_robbery)
        robbing.setOnClickListener{
            probability = r.nextInt(100 )
            compute_email()
            handler.postDelayed({ show_wheather_valid() }, 500)
        }
    }

    fun show_wheather_valid(){
        if(found == false){
            Toast.makeText(this, "Can't find that email", Toast.LENGTH_LONG).show()
        }else {
            if(0<= probability && probability < 40){
                Toast.makeText(this, "The price of greed", Toast.LENGTH_LONG).show()
                val intent = Intent(this,robbery_fail::class.java)
                startActivity(intent)

            }else if(40 <= probability && probability < 70){
                Toast.makeText(this, "Theophany", Toast.LENGTH_LONG).show()
                val intent = Intent(this,robbery_buff::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Soul consumer", Toast.LENGTH_LONG).show()
                val intent = Intent(this,robbery_success::class.java)
                startActivity(intent)

            }
        }
    }



    fun compute_email()
    {

        val coin_today_Listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val email_txt = findViewById<View>(R.id.email_Txt) as TextView
                coins.victim_email = email_txt.text.toString()
                for (i in dataSnapshot.children) {
                    val email_temp = i.child("email").getValue(String::class.java)
                    if(email_txt.text.toString() == email_temp){
                        val target_user = i.key
                        found = true
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

