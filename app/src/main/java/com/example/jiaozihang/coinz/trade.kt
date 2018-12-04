package com.example.jiaozihang.coinz

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.jiaozihang.coinz.coins.user
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.the_trade.*
import kotlin.concurrent.thread
import android.os.SystemClock




class trade : AppCompatActivity() {


    val mAuth = FirebaseAuth.getInstance()
    val user = mAuth.currentUser
    val firebaseData = FirebaseDatabase.getInstance().reference
    var found = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.the_trade)


        val comfirm_trading = findViewById<View>(R.id.trading) as Button


        comfirm_trading.setOnClickListener(){
            val  handler = Handler()
            compute_email()
            handler.postDelayed({ showToast() }, 1000)


        }
  }
    fun showToast(){
        Log.d("compare",found.toString())
        if(found == false){
            Toast.makeText(this, "Can't find that email", Toast.LENGTH_LONG).show()
        }else if(found == true){
            Toast.makeText(this, "Gift sent successfully", Toast.LENGTH_LONG).show()
            val returnIntent = Intent()
            setResult(RESULT_CANCELED, returnIntent)
            finish()
        }
    }
    fun compute_email()
    {

        val coin_today_Listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val email_txt = findViewById<View>(R.id.email_Txt) as TextView
                for (i in dataSnapshot.children) {
                    val email_temp = i.child("email").getValue(String::class.java)
                    Log.d("compare",email_txt.text.toString())
                    Log.d("compare",email_temp)
                    if(email_txt.text.toString() == email_temp){
                        val target_user = i.key
                        coins.give_coins(target_user.toString())
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