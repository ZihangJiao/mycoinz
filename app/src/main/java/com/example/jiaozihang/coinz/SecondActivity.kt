package com.example.jiaozihang.coinz

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        Startbutton.setOnClickListener{
            if(!UserName.text.toString().equals("")) {
                val hellomessage: String = "Hello " + UserName.text.toString()
                Toast.makeText(this, hellomessage, Toast.LENGTH_SHORT).show()

            val intent = Intent(this,ThirdActivity::class.java)
            intent.putExtra("user_message",hellomessage)
            startActivity(intent)
        }else{
                Toast.makeText(this, "Come on, think a cool name for yourself !", Toast.LENGTH_SHORT).show()
            }

        }
    }


}