package com.example.note_retrofit_demo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_note.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddNote : AppCompatActivity() {

    lateinit var jsonApi: JSONApi
    lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        retrofit = Retrofit.Builder().baseUrl("http://192.168.1.10/note_restful/android/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        jsonApi = retrofit.create(JSONApi::class.java)

        handleEvents()
    }

    private fun handleEvents() {
        button_add.setOnClickListener {
            var title = editText_title_addNote.text.toString()
            var content = editText_content_addNote.text.toString()

            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
                Toast.makeText(this@AddNote, "Note must not empty !", Toast.LENGTH_SHORT).show()
            } else {
                var call = jsonApi.insertNote(title, content)
                call.enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (!response.isSuccessful) {
                            Log.v("Code", response.code().toString())
                            return
                        } else {
                            Toast.makeText(this@AddNote, "Inserted note.", Toast.LENGTH_SHORT).show()
                            finish()

                            var prefs = getSharedPreferences("Inserted", Context.MODE_PRIVATE)
                            var editor = prefs.edit()
                            editor.putString("insert", "inserted")
                            editor.apply()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.v("Failed_insert", t.message.toString())
                    }
                })
            }
        }
    }
}