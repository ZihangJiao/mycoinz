package com.example.jiaozihang.coinz

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.the_wallet.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate


class SecondActivity : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()
    val user = mAuth.currentUser
    val firebaseData = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        initData()






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


    private fun initData() {
        val bankListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                    coins.Bank = dataSnapshot.child("bank").getValue(Double::class.java)!!
                }


            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }




        val walletListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                coins.wallet.clear()
                for(i in dataSnapshot.children){
                    val coin_temp = i.getValue(Coin::class.java)
                    coins.wallet.add(coin_temp!!)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }




        val last_date_listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(LocalDate.now().toString() != dataSnapshot.child("last_collecting_date").getValue(String::class.java)!!){
                    coins.count = 25

                    FirebaseDatabase.getInstance().getReference("users")
                            .child(user!!.uid)
                            .child("last_collecting_date")
                            .removeValue()

                    FirebaseDatabase.getInstance().getReference("users")
                            .child(user!!.uid)
                            .child("last_collecting_date")
                            .setValue(LocalDate.now().toString())

                }
                else{
                    coins.count = dataSnapshot.child("count").getValue(Int::class.java)!!
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }




        val coin_today_Listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(i in dataSnapshot.children){
                    val id_temp = i.getValue(String::class.java)
                    coins.coin_collected_today.add(id_temp!!)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }


        firebaseData.child("users").child(user!!.uid).addListenerForSingleValueEvent(bankListener)

        firebaseData.child("users").child(user!!.uid).child("wallet").addListenerForSingleValueEvent(walletListener)

        firebaseData.child("users").child(user!!.uid).addListenerForSingleValueEvent(last_date_listener)

        firebaseData.child("users").child(user!!.uid).child("coin_collected_today").addListenerForSingleValueEvent(coin_today_Listener)



    }

    override fun onBackPressed() {

        // super.onBackPressed(); // Comment this super call to avoid calling finish() or fragmentmanager's backstack pop operation.
    }
    }


