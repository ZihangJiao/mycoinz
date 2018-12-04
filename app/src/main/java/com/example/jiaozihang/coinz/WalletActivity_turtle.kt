package com.example.jiaozihang.coinz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.GridView
import android.widget.Toast
import kotlinx.android.synthetic.main.player_wallet.*

class WalletActivity_turtle:AppCompatActivity (){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_wallet)



        val GV = this.findViewById(R.id.GV) as?  GridView
        val adapter = CoinsAdapter(this,R.layout.coins_layout,data)
        GV?.adapter = adapter

        addbutton()

        send_gift.setOnClickListener{
            val intent = Intent(this,trade::class.java)
            startActivity(intent)
        }
    }

    fun addbutton() {
        to_bank.setOnClickListener {
            if (coins.count > 0) {
                Log.d("checkcrocodile", coins.wallet.size.toString())
                coins.store_coins()

                Log.d("checkcrocodile", coins.wallet.size.toString())



                setContentView(R.layout.player_wallet)

                Log.d("checkthis", coins.wallet.size.toString())
                val GV = this.findViewById(R.id.GV) as GridView
                var renew_data = data

                val adapter = CoinsAdapter(this, R.layout.coins_layout, renew_data)
                GV.adapter = adapter

                addbutton()
                Log.d("checkthat", "no")
                finish()

                overridePendingTransition(0, 0)
                startActivity(getIntent())
                overridePendingTransition(0, 0)
            } else {
                Toast.makeText(this, "Can't store for now!", Toast.LENGTH_SHORT).show()

            }
        }

    }


    val data : ArrayList<customlayout>
        get()
        {

            val item_liste:ArrayList<customlayout> =  ArrayList<customlayout>()
            for(i in coins.wallet){
                if(i.currency == "\"QUID\""){
                    item_liste.add(customlayout(R.drawable.turtlecoin,i))
                }
            }

            return item_liste
        }

    public override fun onRestart() {
        super.onRestart()
        finish()
        startActivity(intent)
    }
}
