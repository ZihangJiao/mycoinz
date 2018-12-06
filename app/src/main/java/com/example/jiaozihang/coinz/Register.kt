package com.example.jiaozihang.coinz

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDate


class Register : AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        val regBtn = findViewById<View>(R.id.regBtn) as Button

        regBtn.setOnClickListener((View.OnClickListener { register() }))
    }


    private fun register() {
        val emailTxt = findViewById<View>(R.id.emailTxt) as EditText
        val passwordTxt = findViewById<View>(R.id.passwordTxt) as EditText
        val nameTxt = findViewById<View>(R.id.usernameTxt) as EditText
        val email = emailTxt.text.toString()
        val password = passwordTxt.text.toString()
        val name = nameTxt.text.toString()
        /** read the input information */

        if (!name.isEmpty() && !password.isEmpty() && !email.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    Toast.makeText(this,
                            "Successfully signed in :", Toast.LENGTH_LONG).show()
                    /** if the register was successful, report to user */


                    val userDetails = user_cl(name, email, 0.0, LocalDate.now().toString(),
                            "", "", 25)
                    FirebaseDatabase.getInstance().getReference("users")
                            .child(user!!.uid)
                            .setValue(userDetails)
                    /** set a new user in firebase */


                    finish()
                    /** jump back to login page*/

                } else {
                    Toast.makeText(this, "Error :(, onComplete: Failed="
                            + task.exception.toString(), Toast.LENGTH_LONG).show()
                    /** if the register was failed, report the error message to user */
                }
            }
        } else {
            Toast.makeText(this, "Please enter the credentials :(",
                    Toast.LENGTH_LONG).show()
            /** if the input information was empty, remind the user */
        }

    }
}
