package com.example.jiaozihang.coinz

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView


class CoinsAdapter(private val getContext: Context,
                   private val CustomLayoutId: Int,
                   private val custom_item: ArrayList<customlayout>) :
        ArrayAdapter<customlayout>(getContext, CustomLayoutId, custom_item) {

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
        row.setOnClickListener {
            if (row.background == null) {
                row.setBackgroundResource(R.drawable.totem_plus)
            } else {
                row.setBackgroundResource(0)
            }
            /** allow the user to set and cancel highlight */


            if (!CoinsObject.temporary_list.isEmpty()) {
                if (item.wallet_coin.currency != CoinsObject.temporary_list[0].currency) {
                    CoinsObject.temporary_list.clear()
                    CoinsObject.temporary_list.add(item.wallet_coin)
                } else if (CoinsObject.temporary_list.contains(item.wallet_coin)) {
                    CoinsObject.temporary_list.remove(item.wallet_coin)
                } else {
                    CoinsObject.temporary_list.add(item.wallet_coin)
                }
            } else {
                CoinsObject.temporary_list.add(item.wallet_coin)
            }
            /** this is used to count how manyand which coin the user has just choosed */

        }




        return row


    }

    class ViewHolder {
        internal var img: ImageView? = null
        internal var txt: TextView? = null
    }
}
