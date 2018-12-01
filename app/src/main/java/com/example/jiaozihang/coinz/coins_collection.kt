package com.example.jiaozihang.coinz

import android.location.Location
import android.util.Log


object coins{

    var the_coin = ArrayList<Coin>()
    var pick_distance = 5000
    var wallet = ArrayList<Coin>()
    var Bank = ArrayList<Coin>()
    var temporary_list = ArrayList<Coin>()
    var coins_to_remove = ArrayList<Coin>()
    var count = 50


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
    }


    fun store_coins(){
        coins_to_remove.clear()
        for(i in temporary_list) {
            for(k in wallet){
                if(i.id == k.id){
                    coins_to_remove.add(k)
                    Bank.add(k)
                    count -= 1
                    Log.d("checkcrocodile", coins_to_remove.size.toString())
                }
            }
        }
        for(i in coins_to_remove){
            Log.d("checkcrocodile",coins.wallet.size.toString())
            wallet.remove(i)
        }
        coins_to_remove.clear()
    }



}
