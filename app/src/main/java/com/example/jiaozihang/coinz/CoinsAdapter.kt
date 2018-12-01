package com.example.jiaozihang.coinz

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class CoinsAdapter(private val getContext: Context,
                   private val CustomLayoutId : Int,
                   private val custom_item:ArrayList<customlayout>):
        ArrayAdapter<customlayout>(getContext,CustomLayoutId,custom_item) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView

        val Holder: ViewHolder
        if (row == null) {

            val inflater = (getContext as Activity).layoutInflater

            row = inflater.inflate(CustomLayoutId, parent, false)

            Holder = ViewHolder()

            Holder.img = row!!.findViewById(R.id.img) as ImageView
            Holder.txt = row.findViewById(R.id.txt) as TextView

            row.setTag(Holder)
        } else {
            Holder = row.getTag() as ViewHolder
        }

        val item = custom_item[position]

        Holder.img!!.setImageResource(item.image)
        Holder.txt!!.setText(item.wallet_coin.the_value)
        row.setOnClickListener(){
            if(row.background == null){
                row.setBackgroundResource(R.drawable.totem_plus)
            }else{
                row.setBackgroundResource(0)
            }


            if(!coins.temporary_list.isEmpty()) {
                if (item.wallet_coin.currency != coins.temporary_list[0].currency) {
                    coins.temporary_list.clear()
                    coins.temporary_list.add(item.wallet_coin)
                    Log.d("checkcrocodile",coins.temporary_list.size.toString())
                }else if(coins.temporary_list.contains(item.wallet_coin)){
                    coins.temporary_list.remove(item.wallet_coin)
                    Log.d("checkcrocodile",coins.temporary_list.size.toString())
                }else{
                    coins.temporary_list.add(item.wallet_coin)
                    Log.d("checktemp","hello")
                    Log.d("checkcrocodile",coins.temporary_list.size.toString())
                }
            }else{
                coins.temporary_list.add(item.wallet_coin)
                Log.d("checkcrocodile",coins.temporary_list.size.toString())
            }


        }




        return row


    }
    class ViewHolder {
        internal var img : ImageView? = null
        internal var txt : TextView? = null
    }
}
