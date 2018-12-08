package com.example.jiaozihang.coinz

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_second.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.LocalDateTime


class SecondActivity : AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()!!
    val user = mAuth.currentUser
    private val firebaseData = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        var Month = LocalDateTime.now().monthValue.toString()
        if (Month.length == 1) {
            Month = "0" + Month
        }
        var Date = LocalDate.now().dayOfMonth.toString()
        if (Date.length == 1) {
            Date = "0" + Date
        }
        DownloadFileTask(DownloadCompleteRunner)
                .execute("http://homepages.inf.ed.ac.uk/stg/coinz/"
                        + LocalDate.now().year.toString()
                        + "/" + Month + "/" + Date + "/coinzmap.geojson")


        initData()
        DownloadCompleteRunner


        image_Map.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }
        /** go to the map page */

        image_moneybag.setOnClickListener {
            val intent = Intent(this, WalletActivity::class.java)
            startActivity(intent)
        }
        /** go to the wallet page */

        image_bank.setOnClickListener {
            val intent = Intent(this, Bank::class.java)
            startActivity(intent)
        }
        /** go to the bank page */

        image_logout.setOnClickListener {
            finish()
        }
        /** go back to the login page */
    }


    private fun initData() {
        val bankListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                CoinsObject.Bank = dataSnapshot.child("bank").getValue(Double::class.java)!!
            }


            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        /** get the value of the bank from firebase */


        val walletListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                CoinsObject.wallet.clear()
                for (i in dataSnapshot.children) {
                    val coin_temp = i.getValue(Coin::class.java)
                    CoinsObject.wallet.add(coin_temp!!)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        /** get the value of the wallet from firebase */


        val last_date_listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (LocalDate.now().toString() != dataSnapshot
                                .child("last_collecting_date")
                                .getValue(String::class.java)!!) {
                    CoinsObject.count = 25

                    FirebaseDatabase.getInstance().getReference("users")
                            .child(user!!.uid)
                            .child("last_collecting_date")
                            .removeValue()

                    FirebaseDatabase.getInstance().getReference("users")
                            .child(user.uid)
                            .child("last_collecting_date")
                            .setValue(LocalDate.now().toString())
                    /** if the date is not the date stored in firebase, renew the date and
                     * set the maximum number the user can pick for now to 25. */


                } else {
                    CoinsObject.count = dataSnapshot.child("count").getValue(Int::class.java)!!
                    /** if the date is the date stored in firebase, read the count stored,
                     * which is the maximum number the user can pick for now */
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        /** set the date and count */


        val coin_today_Listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (i in dataSnapshot.children) {
                    val id_temp = i.getValue(String::class.java)
                    CoinsObject.coin_collected_today.add(id_temp!!)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        /** get the CoinsObject that has been collected by the user */


        firebaseData.child("users")
                .child(user!!.uid)
                .addListenerForSingleValueEvent(bankListener)

        firebaseData.child("users")
                .child(user.uid)
                .child("wallet").addListenerForSingleValueEvent(walletListener)

        firebaseData.child("users")
                .child(user.uid)
                .addListenerForSingleValueEvent(last_date_listener)

        firebaseData.child("users")
                .child(user.uid)
                .child("coin_collected_today").addListenerForSingleValueEvent(coin_today_Listener)

        /** go to the firebase to get data from database */

    }

    override fun onBackPressed() {

        /** avoid user using this back button to go back to login page */
    }
}


