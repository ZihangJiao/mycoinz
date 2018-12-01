package com.example.jiaozihang.coinz

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.the_wallet.*

class WalletActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.the_wallet)

        imageButtonFrog.setOnClickListener{
            val intent = Intent(this,WalletActivity_frog::class.java)
            startActivity(intent)
        }
//
        imageButtonTurtle.setOnClickListener{
            val intent = Intent(this,WalletActivity_turtle::class.java)
            startActivity(intent)
        }

        imageButtonLizard.setOnClickListener{
            val intent = Intent(this,WalletActivity_lizard::class.java)
            startActivity(intent)
        }

        imageButtonCrocodile.setOnClickListener{
            val intent = Intent(this,WalletActivity_crocodile::class.java)
            startActivity(intent)
        }
    }
}

