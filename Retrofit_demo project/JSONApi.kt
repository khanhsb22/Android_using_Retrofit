package com.example.retrofit_restful_demo

import retrofit2.Call
import retrofit2.http.*
import java.util.Map

interface JSONApi {
    /*http://jsonplaceholder.typicode.com/posts*/
    @GET("posts")
    fun getPosts(): Call<List<PostObj>>

    /*http://jsonplaceholder.typicode.com/posts/3/comments*/
    @GET("posts/{id}/comments") // giả sử id là một value của editText user nhập vào
    fun getComments(@Path("id") postId: Int): Call<List<CommentObj>> // @Path: tên thay thế trong đường dẫn

    /*http://jsonplaceholder.typicode.com/posts?userId=4*/
    // @Query (value) phải giống với tham số trên url
    @GET("posts")
    fun getPosts2(@Query("userId") userId: Int): Call<List<PostObj>>

    /*http://jsonplaceholder.typicode.com/posts?userId=4&_sort=id&_order=desc*/
    @GET("posts")
    fun getPosts3(
            @Query("userId") userId: Int,
            @Query("_sort") sort: String,
            @Query("_order") order: String
    ): Call<List<PostObj>>

    /*http://jsonplaceholder.typicode.com/posts?userId=4&userId=2&_sort=id&_order=desc*/
    @GET("posts")
    fun getPosts4(
            @Query("userId") userId: Int,
            @Query("userId") userId2: Int,
            @Query("_sort") sort: String,
            @Query("_order") order: String
    ): Call<List<PostObj>>

    /*http://jsonplaceholder.typicode.com/posts?userId=2&userId=3&userId=6&_sort=null&_order=null*/
    @GET("posts")
    fun getPosts5(
            @Query("userId") userIds: Array<Int>,
            @Query("_sort") sort: String,
            @Query("_order") order: String
    ): Call<List<PostObj>>

    /*http://jsonplaceholder.typicode.com/posts?userId=1&_sort=id&_order=desc*/
    @GET("posts")
    fun getPosts6(@QueryMap parameters: HashMap<String, String>): Call<List<PostObj>>

    /*http://jsonplaceholder.typicode.com/posts/3/comments*/
    @GET
    fun getComments2(@Url url: String): Call<List<CommentObj>>

    /*http://jsonplaceholder.typicode.com/posts*/
    @POST("posts")
    fun createPost(@Body post: PostObj): Call<PostObj>

    /*http://jsonplaceholder.typicode.com/posts*/
    @FormUrlEncoded
    @POST("posts")
    fun createPost2(
            @Field("userId") userId: Int,
            @Field("title") title: String,
            @Field("body") text: String
    ): Call<PostObj>

    /*http://jsonplaceholder.typicode.com/posts*/
    @FormUrlEncoded
    @POST("posts")
    fun createPost3 (@FieldMap fields: HashMap<String, String>): Call<PostObj>

    /*http://jsonplaceholder.typicode.com/posts?id=5*/ //update post id = 5
    @PUT("posts/{id}")
    fun putPost(@Path("id") id: Int, @Body post: PostObj): Call<PostObj>

    /*http://jsonplaceholder.typicode.com/posts?id=5*/
    @FormUrlEncoded
    @PATCH("posts/{id}")
    fun patchPost(@Path("id") id: Int,
                  @Field("userId") userId: Int,
                  @Field("title") title: String?,
                  @Field("body") text: String
    ): Call<PostObj>

    /*http://jsonplaceholder.typicode.com/posts?id=5*/
    @DELETE("posts/{id}")
    fun deletePost(@Path("id") id: Int): Call<Void>
}