package com.example.jiaozihang.coinz

import java.time.LocalDate

class user_cl(val name: String = "",
              val email: String = "",
              val bank: Double = 0.0,
              val last_collecting_date: String = LocalDate.now().toString(),
              val wallet: String = "",
              val coin_collected_today: String = "",
              val count: Int = 0) {

}