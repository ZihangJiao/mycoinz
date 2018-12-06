package com.example.jiaozihang.coinz

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.bank.*

class Bank : AppCompatActivity() {

    /**this is the bank page*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bank)

        val txt = findViewById<View>(R.id.Bankamount) as TextView
        txt.text = CoinsObject.Bank.toString()
        /**show the amount of gold in the bank*/

        go_to_rob.setOnClickListener {
            val intent = Intent(this, robbery::class.java)
            startActivity(intent)
            /**go to the ilp page*/
        }


    }

    public override fun onRestart() {
        super.onRestart()
        finish()
        startActivity(intent)
    }
}