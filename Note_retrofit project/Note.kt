package com.example.note_retrofit_demo

class Note {
    var id = 0
    var title = ""
    var content = ""

    constructor(id: Int, title: String, content: String) {
        this.id = id
        this.title = title
        this.content = content
    }
}