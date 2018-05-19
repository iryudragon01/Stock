package com.iryu.wahkor.stock

import android.os.AsyncTask
import java.net.HttpURLConnection
import java.net.URL

public class GoogleSheet:AsyncTask<String,String,String>(){
    override fun doInBackground(vararg urlss: String?): String {
        var url="https://script.google.com/macros/s/AKfycbwH6fLr3mO1-KcreVb5HcNoI_wATVveJs7RmOayBpYq/dev"
        val text:String
        when(googleaction) {
            "login" -> url=urlss[0]+"?action=login&user="+urlss[1]+"&password="+urlss[2];
            "getdata"   ->  url=urlss[0]+"?action=getdata&pid="+urlss[1];
            "update" -> {
                            url +="?action=testupdate&data="+urlss[0]

            }
            else -> url=urlss[0] as String;
        }

        var connection= URL(url).openConnection() as HttpURLConnection
        try {
            connection.connect()
            text=connection.inputStream.bufferedReader().readText()
        }finally {
            connection.disconnect()
        }

        return  text
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
       println(result)

    }
}