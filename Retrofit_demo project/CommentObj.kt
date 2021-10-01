package com.example.retrofit_restful_demo

import com.google.gson.annotations.SerializedName

class CommentObj {
    var postId = 0
    var id = 0
    var name = ""
    var email = ""

    @SerializedName("body")
    var text = ""
}