package com.iryu.wahkor.stock

import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_loading.*
import android.content.Intent
import android.os.Bundle
import android.view.View

class Loading : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        unintent= Intent(this, CreateUser::class.java)
        loadbt.setText("Create User")
        loadbt.setOnClickListener{v: View? ->
            startActivity(unintent)
        }
    }
}
