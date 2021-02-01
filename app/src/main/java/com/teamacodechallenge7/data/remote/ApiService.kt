package com.teamacodechallenge7.data.remote

import com.teamacodechallenge7.data.model.LoginMsg
import com.teamacodechallenge7.data.model.LoginRequest
import io.reactivex.Single
import retrofit2.http.*

interface ApiService {
//    @GET("/api/posts")
//    fun getPost(): Single<PostMsg>
//
    @POST("/api/v1/auth/login")
    fun loginAction(@Body requestLogin: LoginRequest): Single<LoginMsg>

//    @POST("/api/v1/auth/register")
//    fun regis(@Body requestRegis: RequestRegis): Single<RegisMsg>
//
//    @GET("/api/v1/auth/me")
//    fun me(@Header("Authorization") authorization: String): Single<MeMsg>
//
//    @PUT("/api/v1/users")
//    fun me(@Header("Authorization") authorization: String): Single<MeMsg>
}