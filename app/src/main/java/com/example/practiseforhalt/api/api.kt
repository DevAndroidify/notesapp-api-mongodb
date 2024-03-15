package com.example.practiseforhalt.api

import com.example.practiseforhalt.models.userrequest
import com.example.practiseforhalt.models.userresponse
import retrofit2.http.Body
import retrofit2.http.POST

interface api {
  @POST("users/signin")
  suspend fun signin(@Body userrequest: userrequest ):userresponse
  @POST("users/signup")
  suspend fun signup(@Body userrequest: userrequest):userresponse
}