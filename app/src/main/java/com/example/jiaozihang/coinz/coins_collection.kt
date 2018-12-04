package com.example.jiaozihang.coinz

import android.location.Location
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


object coins{

    var the_coin = ArrayList<Coin>()
    var pick_distance = 900
    var wallet = ArrayList<Coin>()
    var Bank = 0.00
    var temporary_list = ArrayList<Coin>()
    var coins_to_remove = ArrayList<Coin>()
    var count = 100
    val mAuth = FirebaseAuth.getInstance()
    val user = mAuth.currentUser
    val coin_collected_today = ArrayList<String>()


    fun pickupcoins(loc:Location){
        Log.d("distance", the_coin.size.toString())
        for(i in the_coin){
            var Location = Location("")
            Location.longitude = i.longitude
            Location.latitude = i.latitude
            if ((loc.distanceTo(Location) < pick_distance) && (wallet.size < 100)){
                wallet.add(i)
                coins_to_remove.add(i)

                FirebaseDatabase.getInstance().getReference("users")
                        .child(this.user!!.uid)
                        .child("coin_collected_today")
                        .child("CoinNo" + i.id)
                        .setValue(i.id)
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
                    count -= 1
                }
            }
        }
        for(i in coins_to_remove){
            if(i.currency == "\"PENY\"" ){
                Bank += (i.the_value.drop(1).dropLast(1).toDouble() * 32.393996378130524)
            }else if(i.currency == "\"QUID\"" ){
                Bank += (i.the_value.drop(1).dropLast(1).toDouble() * 6.332861915771016)
            }else if(i.currency == "\"DOLR\""){
                Bank += (i.the_value.drop(1).dropLast(1).toDouble() * 29.64763865293904)
            }else if(i.currency == "\"SHIL\"" ){
                Bank += (i.the_value.drop(1).dropLast(1).toDouble() *  24.702404151790425)
            }
            wallet.remove(i)
        }
        coins_to_remove.clear()


        FirebaseDatabase.getInstance().getReference("users")
                .child(this.user!!.uid)
                .child("wallet").removeValue()

        for(i in 0 .. (wallet.size-1)){

            FirebaseDatabase.getInstance().getReference("users")
                    .child(this.user!!.uid)
                    .child("wallet")
                    .child("CoinNo" + (i+1).toString())
                    .setValue(wallet.get(i))
        }
        FirebaseDatabase.getInstance().getReference("users")
                .child(this.user!!.uid)
                .child("bank").removeValue()

        FirebaseDatabase.getInstance().getReference("users")
                .child(this.user!!.uid)
                .child("bank").setValue(Bank)
    }

    fun give_coins(userid :String){
        coins_to_remove.clear()
        for(i in temporary_list) {
            for(k in wallet){
                if(i.id == k.id){
                    coins_to_remove.add(k)
            }
        }
    }
        Log.d("compare",userid)
        for(i in coins_to_remove){
            wallet.remove(i)
            FirebaseDatabase.getInstance().getReference("users")
                    .child(userid)
                    .child("wallet")
                    .child("gift"+i.id).setValue(i)
        }

        FirebaseDatabase.getInstance().getReference("users")
                .child(this.user!!.uid)
                .child("wallet").removeValue()

        for(i in 0 .. (wallet.size-1)){

            FirebaseDatabase.getInstance().getReference("users")
                    .child(this.user!!.uid)
                    .child("wallet")
                    .child("CoinNo" + (i+1).toString())
                    .setValue(wallet.get(i))
        }

}
}
