package com.example.jiaozihang.coinz

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.GridView
import android.widget.Toast
import kotlinx.android.synthetic.main.player_wallet.*

class WalletActivity_crocodile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_wallet)
        val GV = this.findViewById(R.id.GV) as GridView
        val adapter = CoinsAdapter(this, R.layout.coins_layout, data)
        GV.adapter = adapter
        /** set gridView for these CoinsObject */
        addbutton()

        send_gift.setOnClickListener {
            val intent = Intent(this, trade::class.java)
            startActivity(intent)
            /** user can type this button to send gift. */
        }


    }

    fun addbutton() {
        to_bank.setOnClickListener {
            if (CoinsObject.count > 0) {
                CoinsObject.store_coins()
                /** check whether the user can still store CoinsObject */

                setContentView(R.layout.player_wallet)
                val GV = this.findViewById(R.id.GV) as GridView
                val renew_data = data
                val adapter = CoinsAdapter(this, R.layout.coins_layout, renew_data)
                GV.adapter = adapter
                addbutton()
                /** remap the remind CoinsObject */
                finish()
                overridePendingTransition(0, 0)
                startActivity(getIntent())
                overridePendingTransition(0, 0)
                /** refresh the page instantly */
            } else {
                Toast.makeText(this, "Can't store for now!", Toast.LENGTH_SHORT).show()

            }
        }

    }


    val data: ArrayList<customlayout>
        get() {

            val item_liste: ArrayList<customlayout> = ArrayList<customlayout>()

            for (i in CoinsObject.wallet) {
                if (i.currency == "\"PENY\"") {
                    item_liste.add(customlayout(R.drawable.crocodilecoin, i))
                }
            }
            return item_liste
            /** select all the PENY */
        }

    public override fun onRestart() {
        super.onRestart()
        finish()
        startActivity(intent)
    }


}
