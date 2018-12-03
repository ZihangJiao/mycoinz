package com.example.jiaozihang.coinz

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.the_wallet.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        image_Map.setOnClickListener{
            val intent = Intent(this,ThirdActivity::class.java)
            startActivity(intent)
        }

        image_moneybag.setOnClickListener{
            val intent = Intent(this,WalletActivity::class.java)
            startActivity(intent)
        }

        image_bank.setOnClickListener{
            val intent = Intent(this,Bank::class.java)
            startActivity(intent)
        }

        image_logout.setOnClickListener{
            finish()
        }
    }

    override fun onBackPressed() {

        // super.onBackPressed(); // Comment this super call to avoid calling finish() or fragmentmanager's backstack pop operation.
    }
    }


