package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var jsonApi: JSONApi
    lateinit var gson: Gson
    lateinit var adapter: TeamAdapter
    var listAdapter: ArrayList<Team> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var retrofit = Retrofit.Builder().baseUrl("https://api.mocki.io/v1/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        jsonApi = retrofit.create(JSONApi::class.java)

        gson = Gson()

        getInfo()
    }

    private fun getInfo() {
        recyclerView_team.setHasFixedSize(true)
        var layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        recyclerView_team.layoutManager = layoutManager

        var call = jsonApi.getInfo()
        call.enqueue(object : Callback<Any>{
            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d("Error", t.message.toString())
            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (!response.isSuccessful) {
                    Log.d("Error code: ", response.code().toString())
                }
                var json = response.body().toString()
                var info = gson.fromJson(json, Info::class.java)
                textView_id.text = info.id.toString()
                textView_name.text = info.name

                var jsonObject = JSONObject(gson.toJson(response.body())) // Khởi tạo 1 đối tượng JSONObject từ response
                var listTeam: ListTeam = gson.fromJson(jsonObject.toString(), ListTeam::class.java) // convert jsonObject về đối tượng ListTeam
                listTeam.team.forEachIndexed { index, element ->
                    listAdapter.add(Team(element.site_key, element.site_nice))
                }
                adapter = TeamAdapter(listAdapter)
                recyclerView_team.adapter = adapter
            }
        })
    }
}