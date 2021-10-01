package com.example.retrofit_restful_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.HashMap
import java.util.Map

class MainActivity : AppCompatActivity() {

    lateinit var jsonApi: JSONApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var retrofit = Retrofit.Builder().baseUrl("http://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        jsonApi = retrofit.create(JSONApi::class.java)

        // getPosts()
        // getComments()
        // getPosts2()
        // getPosts4()
        // getPosts5()
        // getPosts6()
        // getComments2()
        // createPost()
        // createPost2()
        // createPost3()
        // updatePost()
        // updatePost2()
        deletePost()
    }

    private fun getPosts() {
        /*http://jsonplaceholder.typicode.com/posts*/
        var call = jsonApi.getPosts()
        call.enqueue(object : Callback<List<PostObj>>{
            override fun onFailure(call: Call<List<PostObj>>, t: Throwable) {
                textView_result.text = t.message
            }

            override fun onResponse(call: Call<List<PostObj>>, response: Response<List<PostObj>>) {
                if (!response.isSuccessful) {
                    textView_result.text = "Code: " + response.code()
                    return
                }

                var listPost: List<PostObj> = response.body()!! // lấy nội dung chính của đoạn JSON
                for (item in listPost) {
                    var content = ""
                    content += "userId: " + item.userId + "\n"
                    content += "id: " + item.id + "\n"
                    content += "title: " + item.title + "\n"
                    content += "body: " + item.text + "\n\n"

                    textView_result.append(content)
                }
            }
        })
    }

    private fun getComments() {
        var postId = 3
        /*http://jsonplaceholder.typicode.com/posts/3/comments*/
        var call = jsonApi.getComments(postId)
        call.enqueue(object : Callback<List<CommentObj>>{
            override fun onFailure(call: Call<List<CommentObj>>, t: Throwable) {

            }

            override fun onResponse(call: Call<List<CommentObj>>, response: Response<List<CommentObj>>) {
                if (!response.isSuccessful) {
                    textView_result.text = "Code: " + response.code()
                    return
                }

                var listComments: List<CommentObj> = response.body()!! // lấy nội dung chính của đoạn JSON
                for (item in listComments) {
                    var content = ""
                    content += "postId: " + item.postId + "\n"
                    content += "id: " + item.id + "\n"
                    content += "name: " + item.name + "\n"
                    content += "email: " + item.email + "\n"
                    content += "body: " + item.text + "\n\n"

                    textView_result.append(content)
                }
            }
        })
    }

    private fun getPosts2() {
        /*http://jsonplaceholder.typicode.com/posts?userId=4*/
        var userId = 4
        var call = jsonApi.getPosts2(userId)
        call.enqueue(object : Callback<List<PostObj>>{
            override fun onFailure(call: Call<List<PostObj>>, t: Throwable) {
                textView_result.text = t.message
            }

            override fun onResponse(call: Call<List<PostObj>>, response: Response<List<PostObj>>) {
                if (!response.isSuccessful) {
                    textView_result.text = "Code: " + response.code()
                    return
                }

                var listPost: List<PostObj> = response.body()!! // lấy nội dung chính của đoạn JSON
                for (item in listPost) {
                    var content = ""
                    content += "userId: " + item.userId + "\n"
                    content += "id: " + item.id + "\n"
                    content += "title: " + item.title + "\n"
                    content += "body: " + item.text + "\n\n"

                    textView_result.append(content)
                }
            }
        })
    }

    private fun getPosts3() {
        /*http://jsonplaceholder.typicode.com/posts?userId=4&_sort=id&_order=desc*/
        var userId = 4
        var call = jsonApi.getPosts3(userId, "id", "desc")
        // hoặc có thể để mặc định: var call = jsonApi.getPosts3(userId, null, null)
        call.enqueue(object : Callback<List<PostObj>>{
            override fun onFailure(call: Call<List<PostObj>>, t: Throwable) {
                textView_result.text = t.message
            }

            override fun onResponse(call: Call<List<PostObj>>, response: Response<List<PostObj>>) {
                if (!response.isSuccessful) {
                    textView_result.text = "Code: " + response.code()
                    return
                }

                var listPost: List<PostObj> = response.body()!! // lấy nội dung chính của đoạn JSON
                for (item in listPost) {
                    var content = ""
                    content += "userId: " + item.userId + "\n"
                    content += "id: " + item.id + "\n"
                    content += "title: " + item.title + "\n"
                    content += "body: " + item.text + "\n\n"

                    textView_result.append(content)
                }
            }
        })
    }

    private fun getPosts4() {
        /*http://jsonplaceholder.typicode.com/posts?userId=4&userId=2&_sort=id&_order=desc*/
        var userId = 4
        var userId2 = 2
        var call = jsonApi.getPosts4(userId, userId2, "id", "desc")
        call.enqueue(object : Callback<List<PostObj>>{
            override fun onFailure(call: Call<List<PostObj>>, t: Throwable) {
                textView_result.text = t.message
            }

            override fun onResponse(call: Call<List<PostObj>>, response: Response<List<PostObj>>) {
                if (!response.isSuccessful) {
                    textView_result.text = "Code: " + response.code()
                    return
                }

                var listPost: List<PostObj> = response.body()!! // lấy nội dung chính của đoạn JSON
                for (item in listPost) {
                    var content = ""
                    content += "userId: " + item.userId + "\n"
                    content += "id: " + item.id + "\n"
                    content += "title: " + item.title + "\n"
                    content += "body: " + item.text + "\n\n"

                    textView_result.append(content)
                }
            }
        })
    }

    private fun getPosts5() {
        /*http://jsonplaceholder.typicode.com/posts?userId=2&userId=3&userId=6&_sort=null&_order=null*/
        var intArray = arrayOf(2, 3, 6)
        var call = jsonApi.getPosts5(intArray, "null", "null")
        call.enqueue(object : Callback<List<PostObj>>{
            override fun onFailure(call: Call<List<PostObj>>, t: Throwable) {
                textView_result.text = t.message
            }

            override fun onResponse(call: Call<List<PostObj>>, response: Response<List<PostObj>>) {
                if (!response.isSuccessful) {
                    textView_result.text = "Code: " + response.code()
                    return
                }

                var listPost: List<PostObj> = response.body()!! // lấy nội dung chính của đoạn JSON
                for (item in listPost) {
                    var content = ""
                    content += "userId: " + item.userId + "\n"
                    content += "id: " + item.id + "\n"
                    content += "title: " + item.title + "\n"
                    content += "body: " + item.text + "\n\n"

                    textView_result.append(content)
                }
            }
        })
    }

    private fun getPosts6() {
        /*http://jsonplaceholder.typicode.com/posts?userId=1&_sort=id&_order=desc*/
        var parameters: HashMap<String, String> = HashMap()
        parameters.put("userId", "1")
        parameters.put("_sort", "id")
        parameters.put("_order", "desc")
        var call = jsonApi.getPosts6(parameters)
        call.enqueue(object : Callback<List<PostObj>>{
            override fun onFailure(call: Call<List<PostObj>>, t: Throwable) {
                textView_result.text = t.message
            }

            override fun onResponse(call: Call<List<PostObj>>, response: Response<List<PostObj>>) {
                if (!response.isSuccessful) {
                    textView_result.text = "Code: " + response.code()
                    return
                }

                var listPost: List<PostObj> = response.body()!! // lấy nội dung chính của đoạn JSON
                for (item in listPost) {
                    var content = ""
                    content += "userId: " + item.userId + "\n"
                    content += "id: " + item.id + "\n"
                    content += "title: " + item.title + "\n"
                    content += "body: " + item.text + "\n\n"

                    textView_result.append(content)
                }
            }
        })
    }

    private fun getComments2() {
        /*http://jsonplaceholder.typicode.com/posts/3/comments*/
        var call = jsonApi.getComments2("posts/3/comments")
        call.enqueue(object : Callback<List<CommentObj>>{
            override fun onFailure(call: Call<List<CommentObj>>, t: Throwable) {

            }

            override fun onResponse(call: Call<List<CommentObj>>, response: Response<List<CommentObj>>) {
                if (!response.isSuccessful) {
                    textView_result.text = "Code: " + response.code()
                    return
                }

                var listComments: List<CommentObj> = response.body()!! // lấy nội dung chính của đoạn JSON
                for (item in listComments) {
                    var content = ""
                    content += "postId: " + item.postId + "\n"
                    content += "id: " + item.id + "\n"
                    content += "name: " + item.name + "\n"
                    content += "email: " + item.email + "\n"
                    content += "body: " + item.text + "\n\n"

                    textView_result.append(content)
                }
            }
        })
    }

    private fun createPost() {
        var post = PostObj(23, "New title", "New text")
        /*http://jsonplaceholder.typicode.com/posts*/
        var call = jsonApi.createPost(post)
        call.enqueue(object : Callback<PostObj> {
            override fun onFailure(call: Call<PostObj>, t: Throwable) {

            }

            override fun onResponse(call: Call<PostObj>, response: Response<PostObj>) {
                if (!response.isSuccessful) {
                    textView_result.text = "Code: " + response.code()
                    return
                }

                var postResponse = response.body()
                var content = ""
                content += "Code: " + response.code() + "\n" // CODE: 201
                content += "userId: " + postResponse!!.userId + "\n"
                content += "title: " + postResponse!!.title + "\n"
                content += "body: " + postResponse!!.text + "\n\n"

                textView_result.text = content
            }
        })
    }

    private fun createPost2() {
        /*http://jsonplaceholder.typicode.com/posts*/
        var call = jsonApi.createPost2(24, "New title", "New text")
        call.enqueue(object : Callback<PostObj> {
            override fun onFailure(call: Call<PostObj>, t: Throwable) {

            }

            override fun onResponse(call: Call<PostObj>, response: Response<PostObj>) {
                if (!response.isSuccessful) {
                    textView_result.text = "Code: " + response.code()
                    return
                }

                var postResponse = response.body()
                var content = ""
                content += "Code: " + response.code() + "\n"
                content += "userId: " + postResponse!!.userId + "\n"
                content += "title: " + postResponse!!.title + "\n"
                content += "body: " + postResponse!!.text + "\n\n"

                textView_result.text = content
            }
        })
    }

    private fun createPost3() {
        /*http://jsonplaceholder.typicode.com/posts*/
        var map: HashMap<String, String> = HashMap()
        map.put("userId", "25")
        map.put("title", "New Title")
        var call = jsonApi.createPost3(map)
        call.enqueue(object : Callback<PostObj> {
            override fun onFailure(call: Call<PostObj>, t: Throwable) {

            }

            override fun onResponse(call: Call<PostObj>, response: Response<PostObj>) {
                if (!response.isSuccessful) {
                    textView_result.text = "Code: " + response.code()
                    return
                }

                var postResponse = response.body()
                var content = ""
                content += "Code: " + response.code() + "\n"
                content += "userId: " + postResponse!!.userId + "\n"
                content += "title: " + postResponse!!.title + "\n"
                content += "body: " + postResponse!!.text + "\n\n"

                textView_result.text = content

            }
        })
    }

    private fun updatePost() {
        var post = PostObj(12, "New title 12", "New text 12")
        /*http://jsonplaceholder.typicode.com/posts/12*/
        var call = jsonApi.putPost(5, post)
        call.enqueue(object : Callback<PostObj> {
            override fun onFailure(call: Call<PostObj>, t: Throwable) {
                textView_result.text = t.message
            }

            override fun onResponse(call: Call<PostObj>, response: Response<PostObj>) {
                if (!response.isSuccessful) {
                    textView_result.text = "Code: " + response.code() // Code: 200
                    return
                }

                var postResponse = response.body()
                var content = ""
                content += "Code: " + response.code() + "\n" // CODE: 201
                content += "id: " + postResponse!!.id + "\n"
                content += "userId: " + postResponse!!.userId + "\n"
                content += "title: " + postResponse!!.title + "\n"
                content += "body: " + postResponse!!.text + "\n\n"

                textView_result.text = content

            }
        })
    }

    private fun updatePost2() {
        /*http://jsonplaceholder.typicode.com/posts/5*/
        // update post những phần khác nhưng giữ lại title
        var call = jsonApi.patchPost(5, 12, null, "New Text 12")
        call.enqueue(object : Callback<PostObj> {
            override fun onFailure(call: Call<PostObj>, t: Throwable) {
                textView_result.text = t.message
            }

            override fun onResponse(call: Call<PostObj>, response: Response<PostObj>) {
                if (!response.isSuccessful) {
                    textView_result.text = "Code: " + response.code() // 200
                    return
                }

                var postResponse = response.body()
                var content = ""
                content += "Code: " + response.code() + "\n" // CODE: 201
                content += "id: " + postResponse!!.id + "\n"
                content += "userId: " + postResponse!!.userId + "\n"
                content += "title: " + postResponse!!.title + "\n"
                content += "body: " + postResponse!!.text + "\n\n"

                textView_result.text = content

            }
        })
    }

    private fun deletePost() {
        var call = jsonApi.deletePost(5)
        /*http://jsonplaceholder.typicode.com/posts?id=5*/
        call.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                textView_result.text = "Code: " + response.code() // Code: 200
            }
        })
    }

}