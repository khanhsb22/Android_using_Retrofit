package com.example.retrofit_restful_demo

import com.google.gson.annotations.SerializedName

class PostObj {
    var userId = 0
    var id = 0
    var title = ""

    @SerializedName("body") // định nghĩa tên trên json là body cho obj text
    var text = ""

    constructor(userId: Int, title: String, text: String) {
        this.userId = userId
        this.title = title
        this.text = text
    }
}