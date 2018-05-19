package com.iryu.wahkor.stock

class stocklist {

    var name: String? = null
    var last: String? = null
    var sale: String? = null
    var money: String? = null

    constructor(name:String,last:String,sale:String,money:String) {
        this.name = name
        this.last=last
        this.sale=sale
        this.money=money
    }

}