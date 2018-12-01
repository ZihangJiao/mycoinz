package com.example.jiaozihang.coinz

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var loginBtn = findViewById<View>(R.id.loginBtn) as Button
        loginBtn.setOnClickListener(View.OnClickListener{
            view -> login()

        })

    }


    override fun onStart() {
        super.onStart()
        if(mAuth.currentUser == null){
            startActivity(Intent(this,PhoneAuthCredential::class.java))
        }else{
            Toast.makeText(this,"Already signed in:)",Toast.LENGTH_LONG).show()
        }
    }





    private fun login(){
        val emailTxt = findViewById<View>(R.id.emailTxt) as EditText
        val passwordTXT = findViewById<View>(R.id.passwordTxt) as EditText

        var email = emailTxt.text.toString()
        var password = passwordTXT.text.toString()

        if(!email.isEmpty() && !password.isEmpty()){
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, OnCompleteListener { task ->
                if(task.isSuccessful){
                    startActivity(Intent(this,SecondActivity::class.java))
                    Toast.makeText(this,"Successfully logged in :)", Toast.LENGTH_LONG).show()
                }
            })
        }else{
            Toast.makeText(this,"Please fill up the credentials :(",Toast.LENGTH_LONG).show()
        }
    }






}
