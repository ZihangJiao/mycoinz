package com.example.jiaozihang.coinz

import android.location.Location
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDateTime


object CoinsObject {

    var the_coin = ArrayList<Coin>()
    var pick_distance = 25
    var wallet = ArrayList<Coin>()
    var Bank = 0.00
    var temporary_list = ArrayList<Coin>()
    var coins_to_remove = ArrayList<Coin>()
    var count = 25
    val mAuth = FirebaseAuth.getInstance()
    val user = mAuth.currentUser
    val coin_collected_today = ArrayList<String>()
    val date = LocalDateTime.now()
    var victim_email = ""

    fun pickupcoins(loc: Location) {
        for (i in the_coin) {
            val Location = Location("")
            Location.longitude = i.longitude
            Location.latitude = i.latitude
            if ((loc.distanceTo(Location) < pick_distance) && (wallet.size < 100)) {
                wallet.add(i)
                coins_to_remove.add(i)

                FirebaseDatabase.getInstance().getReference("users")
                        .child(this.user!!.uid)
                        .child("coin_collected_today")
                        .child("CoinNo" + i.id)
                        .setValue(i.id)
            }
        }

        for (k in coins_to_remove) {
            the_coin.remove(k)
        }

        coins_to_remove.isEmpty()

        for (i in 0..(wallet.size - 1)) {
            FirebaseDatabase.getInstance().getReference("users")
                    .child(this.user!!.uid)
                    .child("wallet")
                    .child("CoinNo" + (i + 1).toString())
                    .setValue(wallet.get(i))
        }
        /** if the user's wallet is not full and the user is in the range, add those CoinsObject
         * in to the user's wallet and the coin collecting list, and upload to firebase.
         * */


    }


    fun store_coins() {


        coins_to_remove.clear()
        for (i in temporary_list) {
            for (k in wallet) {
                if (i.id == k.id && count > 0) {
                    coins_to_remove.add(k)
                    count -= 1
                    /** decrease the number of CoinsObject that the usr can store */
                }
            }
        }
        /** temporary_list stores the selected CoinsObject of the user */
        for (i in coins_to_remove) {
            if (i.currency == "\"PENY\"") {
                Bank += (i.the_value.drop(1)
                        .dropLast(1).toDouble() * DownloadCompleteRunner.rate_peny)
            } else if (i.currency == "\"QUID\"") {
                Bank += (i.the_value.drop(1)
                        .dropLast(1).toDouble() * DownloadCompleteRunner.rate_quid)
            } else if (i.currency == "\"DOLR\"") {
                Bank += (i.the_value.drop(1)
                        .dropLast(1).toDouble() * DownloadCompleteRunner.rate_dolr)
            } else if (i.currency == "\"SHIL\"") {
                Bank += (i.the_value.drop(1)
                        .dropLast(1).toDouble() * DownloadCompleteRunner.rate_shil)
            }
            /** add the total amount of gold to the Bank */
            wallet.remove(i)
            /** remove that coin from wallet */
        }
        temporary_list.clear()
        coins_to_remove.clear()


        FirebaseDatabase.getInstance().getReference("users")
                .child(this.user!!.uid)
                .child("wallet").removeValue()

        for (i in 0..(wallet.size - 1)) {

            FirebaseDatabase.getInstance().getReference("users")
                    .child(this.user.uid)
                    .child("wallet")
                    .child("CoinNo" + (i + 1).toString())
                    .setValue(wallet.get(i))
        }
        FirebaseDatabase.getInstance().getReference("users")
                .child(this.user.uid)
                .child("bank").removeValue()

        FirebaseDatabase.getInstance().getReference("users")
                .child(this.user.uid)
                .child("bank").setValue(Bank)

        FirebaseDatabase.getInstance().getReference("users")
                .child(this.user.uid)
                .child("count").removeValue()

        FirebaseDatabase.getInstance().getReference("users")
                .child(this.user.uid)
                .child("count").setValue(count)
    }

    /** upload those data to the firebase */


    fun give_coins(userid: String) {
        coins_to_remove.clear()
        for (i in temporary_list) {
            for (k in wallet) {
                if (i.id == k.id) {
                    coins_to_remove.add(k)
                }
            }
        }
        for (i in coins_to_remove) {
            wallet.remove(i)
            FirebaseDatabase.getInstance().getReference("users")
                    .child(userid)
                    .child("wallet")
                    .child("gift" + i.id).setValue(i)
            /** add those CoinsObject to the firebase of the selected user */
        }

        FirebaseDatabase.getInstance().getReference("users")
                .child(this.user!!.uid)
                .child("wallet").removeValue()

        for (i in 0..(wallet.size - 1)) {

            FirebaseDatabase.getInstance().getReference("users")
                    .child(this.user!!.uid)
                    .child("wallet")
                    .child("CoinNo" + (i + 1).toString())
                    .setValue(wallet.get(i))
            /** reset the wallet on the firebase */

        }
        temporary_list.clear()

    }
}
