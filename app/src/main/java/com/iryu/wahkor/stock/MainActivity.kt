package com.iryu.wahkor.stock

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import kotlinx.android.synthetic.main.createuser.*
import kotlinx.android.synthetic.main.login_main.*
import java.net.HttpURLConnection
import java.net.URL

public lateinit var unintent:Intent
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (userid == "") {
            setContentView(R.layout.login_main)
            loginBTmain.setOnClickListener { v: View? -> loginBTmainListener() }
             userBoxmain.setOnClickListener{v: View? -> userBoxmain.setText("") }
            passBoxmain.setOnClickListener{v: View? -> passBoxmain.setText("") }
            passBoxmain.setOnKeyListener(View.OnKeyListener{v, keyCode, event ->
                if (keyCode== KeyEvent.KEYCODE_ENTER){
                            loginBTmainListener()
                             true
                        }else{
                    false} })

        } else {
            setContentView(R.layout.activity_main)
        }
        val iryuintent: Intent
        iryuintent = Intent(this, ShowStockActivity::class.java)
        unintent = iryuintent

    }


    private fun createBTmainListener() {

        setContentView(R.layout.createuser)
        signupBTmain.setOnClickListener(View.OnClickListener { singupBtmainListener() })
    }

    private  fun loginBTmainListener(){
        var url="https://script.google.com/macros/s/AKfycbwH6fLr3mO1-KcreVb5HcNoI_wATVveJs7RmOayBpYq/dev"
       // var url="https://script.google.com/macros/s/AKfycbzuwCkRJmvxcK51PDTyQPrJyolaaD2j92acMA-yL88NbxksXi2N/exec"
        googleaction="login"
        GoogleAppScript().execute(url,userBoxmain.text.toString(),passBoxmain.text.toString())
    }

    private fun singupBtmainListener() {
        println(namecreateboxmain.text.toString()+passcreateboxmain.text.toString())
    }

    inner class GoogleAppScript: AsyncTask<String, String, String>(){
        override fun doInBackground(vararg urlss: String?): String {
            val url:String?
            val text:String
            when(googleaction) {
                "login" -> url=urlss[0]+"?action=login&user="+urlss[1]+"&password="+urlss[2];
                "getdata"   ->  url=urlss[0]+"?action=getdata&pid="+urlss[1];
                else -> url=urlss[0];
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
                    val tempstring=(result as String).split("--><--")
                    val tempname=tempstring[0].split(",")
                    GoogleId=tempname[2]
                    GoogleLevel=tempname[1]
                    GoogleUser=tempname[0]
                    userdata=tempstring[1]
                    if(GoogleUser !="failed"){
                    startActivity(unintent)}

            }
        }    }

