package com.example.practiseforhalt.api

import android.telecom.Call
import androidx.room.Delete
import com.example.practiseforhalt.models.newnoteresponseItem
import com.example.practiseforhalt.models.noterequest
import com.example.practiseforhalt.models.noteresponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface noteapi {
    @GET("notes/")
    suspend fun getNotes(): List<newnoteresponseItem>

    @POST("notes/")
    suspend fun createNote(
        @Header("Authorization") token: String,
        @Body noteRequest: noterequest
    ): noteresponse

    @PUT("notes/{noteid}")
    suspend fun updateNote(
        @Header("Authorization") token: String,
        @Path("noteid") noteId: String,
        @Body noteRequest: noterequest
    ): noteresponse

    @DELETE("notes/{noteid}")
    suspend fun deleteNote(
        @Header("Authorization") token: String,
        @Path("noteid") noteId: String
    ): noteresponse
}
