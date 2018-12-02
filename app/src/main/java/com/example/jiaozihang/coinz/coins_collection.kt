package com.example.jiaozihang.coinz

import android.location.Location
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


object coins{

    var the_coin = ArrayList<Coin>()
    var pick_distance = 5000
    var wallet = ArrayList<Coin>()
    var Bank = 0.00
    var temporary_list = ArrayList<Coin>()
    var coins_to_remove = ArrayList<Coin>()
    var count = 50
    val mAuth = FirebaseAuth.getInstance()
    val user = mAuth.currentUser

    fun pickupcoins(loc:Location){
        Log.d("distance", the_coin.size.toString())
        for(i in the_coin){
            if ((loc.distanceTo(i.location) < pick_distance) && (wallet.size < 100)){
                wallet.add(i)
                coins_to_remove.add(i)
            }
        }

        for(k in coins_to_remove){
            the_coin.remove(k)
        }

        coins_to_remove.isEmpty()

        for(i in 0 .. (wallet.size-1)){
            FirebaseDatabase.getInstance().getReference("users")
                    .child(this.user!!.uid)
                    .child("wallet")
                    .child("CoinNo" + (i+1).toString())
                    .setValue(wallet.get(i))
        }
    }


    fun store_coins(){
        coins_to_remove.clear()
        for(i in temporary_list) {
            for(k in wallet){
                if(i.id == k.id){
                    coins_to_remove.add(k)
//                    Bank.add(k)
                    count -= 1
                    Log.d("checkcrocodile", coins_to_remove.size.toString())
                }
            }
        }
        for(i in coins_to_remove){
            Log.d("checkcrocodile",coins.wallet.size.toString())
            if(i.currency == "\"PENY\"" ){
                Bank += (i.the_value.toDouble() * 32.393996378130524)
            }else if(i.currency == "\"QUID\"" ){
                Bank += (i.the_value.toDouble() * 6.332861915771016)
            }else if(i.currency == "\"DOLR\""){
                Bank += (i.the_value.toDouble() * 29.64763865293904)
            }else if(i.currency == "\"SHIL\"" ){
                Bank += (i.the_value.toDouble() *  24.702404151790425)
            }
            wallet.remove(i)
        }
        coins_to_remove.clear()

        for(i in 0 .. (wallet.size-1)){
            FirebaseDatabase.getInstance().getReference("users")
                    .child(this.user!!.uid)
                    .child("wallet")
                    .child("CoinNo" + (i+1).toString())
                    .setValue(wallet.get(i))
        }
    }



}
