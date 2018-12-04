package com.example.jiaozihang.coinz

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.internal.FirebaseAppHelper.getUid
import java.time.LocalDate


class Register : AppCompatActivity(){

    val mAuth = FirebaseAuth.getInstance()
    lateinit var mDatabase : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        val regBtn = findViewById<View>(R.id.regBtn) as Button



        regBtn.setOnClickListener((View.OnClickListener {view -> register() }))

    }


    private fun register() {
        val emailTxt = findViewById<View>(R.id.emailTxt) as EditText
        val passwordTxt = findViewById<View>(R.id.passwordTxt) as EditText
        val nameTxt = findViewById<View>(R.id.usernameTxt) as EditText

        var email = emailTxt.text.toString()
        var password = passwordTxt.text.toString()
        var name = nameTxt.text.toString()

        if(!name.isEmpty() && !password.isEmpty() && !email.isEmpty()){
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                Log.d("checkthis",mAuth.createUserWithEmailAndPassword(email, password).toString())
                if(task.isSuccessful){
                    val user = mAuth.currentUser
                    val uid = user!!.uid
                    Toast.makeText(this,"Successfully signed in :",Toast.LENGTH_LONG).show()



                    val userDetails = user_cl(name, email,0.0, LocalDate.now().toString(),"","",25)
                    FirebaseDatabase.getInstance().getReference("users")
                            .child(user.getUid())
                            .setValue(userDetails)



                    finish()

                }else{
                    Toast.makeText(this,"Error :(, onComplete: Failed="+ task.getException().toString(),Toast.LENGTH_LONG).show()
                }
            }
        }else{
            Toast.makeText(this,"Please enter the credentials :(", Toast.LENGTH_LONG).show()
        }

    }
}
