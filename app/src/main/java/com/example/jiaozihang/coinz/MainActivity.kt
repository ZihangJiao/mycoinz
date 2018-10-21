package com.example.jiaozihang.coinz

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ready.setOnClickListener {
            Log.i("MathActivity", "Botton was clicked !")
            Toast.makeText(this, "Welcome to Coinz !", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,SecondActivity::class.java)
            startActivity(intent)


        }
    }
}
