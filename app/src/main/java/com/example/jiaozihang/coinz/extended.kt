package com.example.jiaozihang.coinz

import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser

interface DownloadCompleteListener {
    fun downloadComplete(result: String)
}

object DownloadCompleteRunner : DownloadCompleteListener {
    var result: String = ""
    var rate_peny = 0.0
    var rate_dolr = 0.0
    var rate_shil = 0.0
    var rate_quid = 0.0
    lateinit var json : JsonObject

    override fun downloadComplete(result: String) {
        this.result = result
        val parser = JsonParser()
        this.json = parser.parse(DownloadCompleteRunner.result) as JsonObject
        DownloadCompleteRunner.setup()

    }

    fun setup(){
        Log.d("Tag1",DownloadCompleteRunner.json.toString())
        val rates =DownloadCompleteRunner!!.json.get("rates").toString()
        val numberOnly = rates.replace("[^0-9\\.]+", " ")
        //val rs = numberOnly.split("\\s")
        val a = numberOnly.substringAfter(":").substringBefore(",").toDouble()
        Log.d("Tag1",a.toString())
        DownloadCompleteRunner.rate_shil =
                numberOnly
                        .substringAfter(":")
                        .substringBefore(",")
                        .toDouble()
        DownloadCompleteRunner.rate_dolr =
                numberOnly
                        .substringAfter(":")
                        .substringAfter(":")
                        .substringBefore(",")
                        .toDouble()
        DownloadCompleteRunner.rate_quid =
                numberOnly
                        .substringAfter(":")
                        .substringAfter(":")
                        .substringAfter(":")
                        .substringBefore(",")
                        .toDouble()

        DownloadCompleteRunner.rate_peny =
                numberOnly
                        .substringAfter(":")
                        .substringAfter(":")
                        .substringAfter(":")
                        .substringAfter(":")
                        .substringBefore("}")
                        .toDouble()
    }
}