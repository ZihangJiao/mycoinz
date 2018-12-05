package com.example.jiaozihang.coinz

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class robbery_fail  : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()
    val user = mAuth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fail_robbery)
        rob_fail()
    }







    fun rob_fail(){
        val fail_rob = findViewById<View>(R.id.fail_rob) as TextView
        fail_rob.text = "you have lost "+(0.2 * coins.Bank).toString()+" gold"
        coins.Bank = coins.Bank * 0.8
        FirebaseDatabase.getInstance().getReference("users")
                .child(this.user!!.uid)
                .child("bank").removeValue()

        FirebaseDatabase.getInstance().getReference("users")
                .child(this.user!!.uid)
                .child("bank").setValue(coins.Bank)

    }

}