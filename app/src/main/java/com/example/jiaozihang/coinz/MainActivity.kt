package com.example.jiaozihang.coinz

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    private val mAuth = FirebaseAuth.getInstance()!!

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginBtn = findViewById<View>(R.id.loginBtn) as Button
        val regTxt = findViewById<View>(R.id.regTxt) as TextView

        loginBtn.setOnClickListener((View.OnClickListener { login() }))
        regTxt.setOnClickListener((View.OnClickListener { register() }))

    }

    private fun register() {
        startActivity((Intent(this, Register::class.java)))
    }

    /** if the  register button is clicked, go to the register page */


    private fun login() {
        /** if the  login button is clicked, go to the register page */
        val emailTxt = findViewById<View>(R.id.emailTxt) as EditText
        val passwordTXT = findViewById<View>(R.id.passwordTxt) as EditText
        val email = emailTxt.text.toString()
        val password = passwordTXT.text.toString()
        /** read the email and password the user input*/

        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, SecondActivity::class.java))
                    Toast.makeText(this,
                            "Successfully logged in :)"
                            , Toast.LENGTH_LONG).show()
                    /** remind the user when the log in is successful */
                } else {
                    Toast.makeText(this,
                            "Invalid combination of email and password :("
                            , Toast.LENGTH_LONG).show()
                    /** remind the user when the input is wrong */
                }
            }

        } else {
            Toast.makeText(this,
                    "Please fill up the credentials :("
                    , Toast.LENGTH_LONG).show()
        }
        /** remind the user when the input is empty */
    }


}
