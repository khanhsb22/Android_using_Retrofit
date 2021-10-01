package com.example.note_retrofit_demo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_view_note.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ViewNote : AppCompatActivity() {

    var id = 0
    var title = ""
    var content = ""

    lateinit var jsonApi: JSONApi
    lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_note)

        id = intent.getIntExtra("id", -1)
        title = intent.getStringExtra("title")!!
        content = intent.getStringExtra("content")!!

        editText_title.text = Editable.Factory.getInstance().newEditable(title)
        editText_content.text = Editable.Factory.getInstance().newEditable(content)

        retrofit = Retrofit.Builder().baseUrl("http://192.168.1.10/note_restful/android/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        jsonApi = retrofit.create(JSONApi::class.java)

        handleEvents()
    }

    private fun handleEvents() {
        button_update.setOnClickListener {
            var call = jsonApi.updateNote(
                id,
                editText_title.text.toString(),
                editText_content.text.toString()
            )
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (!response.isSuccessful) {
                        Log.v("Code", response.code().toString())
                        return
                    } else {
                        Toast.makeText(this@ViewNote, "Updated note.", Toast.LENGTH_SHORT).show()
                        finish()

                        var prefs = getSharedPreferences("Updated", Context.MODE_PRIVATE)
                        var editor = prefs.edit()
                        editor.putString("update", "updated")
                        editor.apply()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.v("Update_error", t.message.toString())
                }
            })
        }
    }
}