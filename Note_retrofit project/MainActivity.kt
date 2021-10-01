package com.example.note_retrofit_demo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.StringReader

class MainActivity : AppCompatActivity() {

    var listOfAdapter: ArrayList<Note> = ArrayList()
    var adapter: NoteAdapter? = null
    lateinit var jsonApi: JSONApi
    lateinit var retrofit: Retrofit
    lateinit var gson: Gson
    var id = 0
    var index = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrofit = Retrofit.Builder().baseUrl("http://192.168.1.10/note_restful/android/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        jsonApi = retrofit.create(JSONApi::class.java)

        gson = Gson()

        getAllNotes()
        clearPref()
        handleEvents()
    }

    private fun handleEvents() {
        fab_add_new.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddNote::class.java))
        }
        imageView_delete_All.setOnClickListener {
            var alertDialog = AlertDialog.Builder(this@MainActivity)
            alertDialog.setTitle("Delete all")
            alertDialog.setMessage("You want delete all notes ?")
            alertDialog.setCancelable(false) // Không cho click ở ngoài làm mất dialog
            alertDialog.setPositiveButton("Ok") { dialog, which ->
                alertDialog.setCancelable(true)
                var call = jsonApi.deleteAllNote()
                call.enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (!response.isSuccessful) {
                            Log.v("Code", response.code().toString())
                            return
                        } else {
                            Toast.makeText(this@MainActivity, "Deleted All Note.", Toast.LENGTH_SHORT).show()
                            listOfAdapter.clear()
                            getAllNotes()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.v("Delete_all_fail", t.message.toString())
                    }
                })
            }
            alertDialog.setNegativeButton("Cancel") {dialog, which ->
                alertDialog.setCancelable(true)
                dialog.dismiss()
            }
            alertDialog.show()
        }
    }

    private fun clearPref() {
        var prefs = getSharedPreferences("Updated", Context.MODE_PRIVATE)
        prefs.edit().clear().commit()
        var prefs_2 = getSharedPreferences("Inserted", Context.MODE_PRIVATE)
        prefs_2.edit().clear().commit()
    }

    override fun onStart() {
        super.onStart()
        var prefs = getSharedPreferences("Updated", Context.MODE_PRIVATE)
        if (prefs != null) {
            var value = prefs.getString("update", "null")
            if (value == "updated") {
                listOfAdapter.clear()
                getAllNotes()
            }
        }
        var prefs_2 = getSharedPreferences("Inserted", Context.MODE_PRIVATE)
        if (prefs_2 != null) {
            var value = prefs_2.getString("insert", "null")
            if (value == "inserted") {
                listOfAdapter.clear()
                getAllNotes()
            }
        }
    }

    private fun getAllNotes() {
        recyclerView_notes.setHasFixedSize(true)
        var layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        recyclerView_notes.layoutManager = layoutManager

        var call = jsonApi.getAllNotes()
        call.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (!response.isSuccessful) {
                    Log.v("Code", response.code().toString())
                    return
                }
                var jsonObject = JSONObject(gson.toJson(response.body()))
                Log.v("jsonObject", jsonObject.getJSONArray("body").toString())
                var listNote: ListNote = gson.fromJson(jsonObject.toString(), ListNote::class.java)
                listNote.body.forEachIndexed { index, element ->
                    listOfAdapter.add(Note(element.id, element.title, element.content))
                }
                adapter = NoteAdapter(this@MainActivity, listOfAdapter)
                recyclerView_notes.adapter = adapter
                // add a divider
                val itemDecorator =
                        DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
                itemDecorator.setDrawable(
                        ContextCompat.getDrawable(
                                this@MainActivity,
                                R.drawable.custom_divider
                        )!!
                )
                recyclerView_notes.addItemDecoration(itemDecorator)
                adapter?.notifyDataSetChanged()

                // Set events of adapter
                adapter?.setOnItemClickListener(object : NoteAdapter.ClickListener {
                    override fun onItemClick(position: Int, v: View) {
                        var intent = Intent(this@MainActivity, ViewNote::class.java)
                        intent.putExtra("id", listOfAdapter[position].id)
                        intent.putExtra("title", listOfAdapter[position].title)
                        intent.putExtra("content", listOfAdapter[position].content)
                        startActivity(intent)
                    }

                    override fun onItemLongClick(position: Int, v: View) {
                        id = position
                        index = listOfAdapter[position].id
                        registerForContextMenu(v)
                    }
                })
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.v("Failed_to_getAllNote", t.message.toString())
            }
        })

    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu?.add(0, v!!.id, 0, "Xóa")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.title == "Xóa") {
            listOfAdapter.removeAt(id)
            var call = jsonApi.deleteNote(index)

            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Toast.makeText(this@MainActivity, "Deleted.", Toast.LENGTH_SHORT).show()
                    adapter?.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.v("failed_to_delete", t.message.toString())
                }
            })
        }
        return true
    }
}