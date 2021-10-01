package com.example.note_retrofit_demo

import retrofit2.Call
import retrofit2.http.*

interface JSONApi {
    /* http://localhost/note_restful/android/readlAll.php */
    @GET("readAll.php")
    fun getAllNotes(): Call<Any>

    /* http://localhost/note_restful/android/delete.php?id=5 */
    @DELETE("delete.php")
    fun deleteNote(@Query("id") id: Int): Call<Void>

    /*http://localhost/note_restful/android/update.php?id=33&title=A1&content=A2*/
    @PUT("update.php")
    fun updateNote(
        @Query("id") id: Int,
        @Query("title") title: String,
        @Query("content") content: String
    ): Call<Void>

    /*http://localhost/note_restful/android/insert.php*/
    @FormUrlEncoded
    @POST("insert.php")
    fun insertNote(@Field("title") title: String,
    @Field("content") content: String): Call<Void>

    /*http://localhost/note_restful/android/deleteAll.php*/
    @DELETE("deleteAll.php")
    fun deleteAllNote(): Call<Void>
}