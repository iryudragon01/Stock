package com.iryu.wahkor.stock


import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatTextView
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_show_stock.*
import kotlinx.android.synthetic.main.row_layout.*
import org.json.JSONArray
public var userdata:String=""
class ShowStockActivity : AppCompatActivity() {

    private var listNotes = ArrayList<stocklist>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_stock)
        var x=0

        val jsonArray= JSONArray(userdata)
        while (x<jsonArray.length()){
            val jsonObject=jsonArray.getJSONObject(x)
            GoogleData_name[x] =jsonObject.getString("name")
            GoogleData_last[x] =jsonObject.getString("last")
            GoogleData_sale[x] =jsonObject.getString("sale")
            GoogleData_money[x] =jsonObject.getString("money")
            GoogleData_price[x]= jsonObject.getString("price")
            x++

             }
        loop=jsonArray.length()
        updatelist(jsonArray.length())

    }

    inner class ListAdapte(val context:Context,val list:ArrayList<stocklist>):BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val view:View= LayoutInflater.from(context).inflate(R.layout.row_layout,parent,false)
            val presidentName=view.findViewById(R.id.stock_name) as AppCompatTextView
            val presidentLast=view.findViewById(R.id.stock_last) as AppCompatTextView
            val presidentSale=view.findViewById(R.id.stock_sale) as AppCompatTextView
            val presidentMoney=view.findViewById(R.id.stock_money) as AppCompatTextView
            presidentName.text=list[position].name.toString()
            presidentLast.text=list[position].last.toString()
            presidentSale.text=list[position].sale.toString()
            presidentMoney.text=list[position].money.toString()
            return view
        }

        override fun getItem(position: Int): Any {
            return list[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return list.size
        }
        fun setvaluetolast(){


        }

    }
    fun updatelist(loop:Int){
        var x=0
        while (x<loop){
            listNotes.add(stocklist(
                    GoogleData_name[x] as String,
                    GoogleData_last[x] as String,
                    "ยอดขาย "+ GoogleData_sale[x] as String,
                    "ยอดเงิน "+ GoogleData_money[x] as String
            ))
            x++
        }
        listNotes.add(stocklist(GoogleUser,"update","",""))
        var notesAdapter = ListAdapte(this, listNotes)
        stock_list.adapter = notesAdapter
        stock_list.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            myAlert(position)
            // Toast.makeText(this, "Click on " + listNotes[position].name, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onBackPressed() {
        unintent= Intent(this,Loading::class.java)
        startActivity(unintent)
        finish()
    }

    fun myAlert(position:Int) {
        if(position == loop){
            googleaction="update"
            var senddata=""
            for(i in 0..1000){
                if(GoogleData_name[i] ==null){
                    break
                }
                senddata += GoogleData_last[i]+","
            }
            GoogleSheet().execute(senddata)
            println("click on update button")
            return
        }
    var alert=AlertDialog.Builder(this)
    var edittextlast:EditText?=null
with(alert){
    setTitle(GoogleData_name[position])
    setMessage("ยอดล่าสุด")

    edittextlast=EditText(context)
    edittextlast!!.hint= GoogleData_last[position]
    edittextlast!!.inputType = InputType.TYPE_CLASS_NUMBER
    setPositiveButton("OK") {
        dialog, whichButton ->
        //showMessage("display the game score or anything!")
        dialog.dismiss()
        var last=edittextlast!!.text.toString()
        var deff=last.toInt()- (GoogleData_last[position]as String).toInt()
        GoogleData_last[position]=last
        GoogleData_sale[position]=((GoogleData_sale[position]as String).toInt()+deff).toString()
        GoogleData_money[position]=((GoogleData_sale[position]as String).toInt()* (GoogleData_price[position]as String).toInt()).toString()
        listNotes[position].last=last
        listNotes[position].sale= GoogleData_sale[position]
        listNotes[position].money= GoogleData_money[position]

        //updatelist(loop)
       //tock_list.getChildAt(position).
    }

    setNegativeButton("NO") {
        dialog, whichButton ->
        //showMessage("Close the game or anything!")
        dialog.dismiss()
    }
}

        // Dialog
        val dialog = alert.create()
        dialog.setView(edittextlast)
        dialog.show()
}



    }
